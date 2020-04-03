import { elementSelector } from '../utils/common.js'
import option from './option.js'

class Controller {
  constructor({
    model: { dustModel, forecastModel },
    view: { dustView, forecastView },
    component: { dustStatComponent, dustLogGraphComponent, airImageComponent }
  }) {
    this.dustModel = dustModel;
    this.forecastModel = forecastModel
    this.dustView = dustView;
    this.forecastView = forecastView;
    this.dustStatComponent = dustStatComponent;
    this.dustLogGraphComponent = dustLogGraphComponent;
    this.airImageComponent = airImageComponent
    this.airQualityInfo = [];
    this.forcastInfo = [];
    this.currentIndex = 0
    this.index = 0
    this.isMoving = false
    this.initialize();
  }

  async initialize() {
    const airData = await this.dustModel.initialize();
    this.airQualityInfo = airData
    this.dustView.initRender(airData);
    this.dustLogGraphComponent.renderdustData()
    const forecastData = await this.forecastModel.initialize();
    this.forcastInfo = [...forecastData]
    this.forecastView.initRender(forecastData)
    this.eventHandler()
  }

  eventHandler() {
    elementSelector('.tab_Menu').addEventListener("touchstart", this.eventTabMenu, {passive: true})
    elementSelector('.play-btn').addEventListener("touchend", e => {
      requestAnimationFrame(this.playForecastBtn.bind(this)), {passive: true}})
    elementSelector('.play-status').addEventListener("touchstart", this.initClientX.bind(this), {passive: true})
    // elementSelector('.play-status').addEventListener("touchmove", this.moveTargetBtn.bind(this), {passive: true})
    elementSelector('.graph').addEventListener("touchstart", this.initGraph.bind(this), {passive: true})
    elementSelector('.graph').addEventListener("touchmove", this.moveGraph.bind(this), {passive: true})
  }

  eventTabMenu({target: { innerText }}) {
    if(innerText === "미세먼지") {
      elementSelector('.air-status').style.zIndex = -1
      elementSelector('.air-status').style.opacity = 0
      elementSelector('.dust_info').style.display = 'block';
      elementSelector('.dust_graph').style.display = 'block'
      elementSelector('.menu1').classList.add('select')
      elementSelector('.menu2').classList.remove('select')
    } else if(innerText === "예보") {
      elementSelector('.air-status').style.zIndex = 1
      elementSelector('.air-status').style.opacity = 1
      elementSelector('.dust_info').style.display = 'none';
      elementSelector('.dust_graph').style.display = 'none'
      elementSelector('.menu2').classList.add('select')
      elementSelector('.menu1').classList.remove('select')
    }
  }


  touchdownEvent() {
    this.graphList[option.counter - 1].style.border = "";
    this.graphList[option.counter].style.border = "5px solid red";
    option.counter++;
  }

  touchupEvent() {
    const currentCounter = option.counter;
    const beforeCounter = option.counter - 1;
    this.graphList[currentCounter].style.border = "";
    this.graphList[beforeCounter].style.border = "5px solid red";
    option.counter--;
  }

  touchStartPointEvent() {
    const currentCounter = option.counter;
    this.graphList[currentCounter].style.border = "5px solid red";
    option.counter++;
  }

  touchEndPointEvent() {
    let beforeCounter = option.counter - 1;
    let currentCounter = option.counter - 2;
    this.graphList[beforeCounter].style.border = "";
    this.graphList[currentCounter].style.border = "5px solid red";
    option.counter--;
  }

  playForecastBtn() {
    if(this.isMoving) return
    this.isMoving = true
    this.barWidth = document.querySelector('.bar').offsetWidth;
    this.contentWidth = this.barWidth / this.forcastInfo.length;
    this.runGageAnimation();
  }

  runGageAnimation() {
    const gripBtn = document.querySelector('.play-status')
    const preLeftPosition = parseInt(gripBtn.style.left);
    if(preLeftPosition >= this.barWidth - 10) {
      gripBtn.style.left = 0 + "px";
      this.isMoving = false
      this.currentIndex = 0
      return;
    }

    if(this.currentClientX > this.airQualityInfo.length - 1 ) {
      this.currentClientX = 0
      this.isMoving = false
      return 
    }

    this.airImageComponent.imageRender(this.currentIndex)
    this.currentIndex += 1
    gripBtn.style.left = (preLeftPosition) + this.contentWidth + "px";
    setTimeout(this.runGageAnimation.bind(this), 300);
  }

  moveTargetBtn(e) {
    this.currentClientX =  e.changedTouches[0].clientX;
    const averageBtn = e.target.clientWidth / 2;
    if(this.currentClientX < 96 || this.currentClientX > 360) return 
    console.log(this.startClientX)
    this.btnIndex =  (this.currentClientX - averageBtn - this.startClientX) / this.contentWidth + 2
    this.gripBtn.style.left = this.contentWidth *  this.btnIndex +'px' 
    // this.airImageComponent.imageRender(parseInt(this.btnIndex))
  }

  initClientX(e) {
    this.startClientX = e.changedTouches[0].clientX
    this.barWidth = document.querySelector('.bar').offsetWidth;
    this.contentWidth = this.barWidth / this.forcastInfo.length;
    this.gripBtn = document.querySelector('.play-status')
  }

  initGraph(e) {
    this.graphList = document.querySelectorAll('.graph li')
    option.initialY = e.touches[0].clientY;
  }

  fixTouchY(e) {
    option.initialY = e.touches[0].clientY - 0.1;
  }

  changeGraphControll(e) {
    let currentY = e.touches[0].clientY;
    let diffY = option.initialY - currentY;
    0 < diffY ? (option.touchSwipe = "top") : (option.touchSwipe = "bottom");
  }

  moveGraph(e) {
    this.changeGraphControll(e)
    this.fixTouchY(e)
    const counter = option.counter;
    const touchSwipe = option.touchSwipe;
    
    this.index++
    if(this.index < 15) {
      return
    } else {
      this.index = 0
    }

    if (counter === 0 && touchSwipe === "bottom") {
      return;
    } else if (counter === this.airQualityInfo.length && touchSwipe === "top") {
      return;
    } else if (counter === 0 && touchSwipe === "top") {
      this.dustStatComponent.renderAirInfo(this.airQualityInfo[counter])
      this.touchStartPointEvent();
      return;
    } else if (counter === this.airQualityInfo.length && touchSwipe === "bottom") {
      this.touchEndPointEvent();
      this.dustStatComponent.renderAirInfo(this.airQualityInfo[counter])
      return;
    } else if (counter === this.airQualityInfo.length) {
      return;
    } else if (touchSwipe === "top") {
      this.dustStatComponent.renderAirInfo(this.airQualityInfo[counter])
      this.touchdownEvent();
    } else if (touchSwipe === "bottom") {
      this.touchupEvent();
      this.dustStatComponent.renderAirInfo(this.airQualityInfo[counter])
    }
  }

}

export default Controller;








