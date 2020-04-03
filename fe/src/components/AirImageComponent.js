class AirImageComponrnt {
  constructor() {

  }

  render(forecastData) {
    const forecastList = forecastData.reduce((list, forecastInfo) => {
      list += `
        <img class="_image" src="${forecastInfo.forecastImage}">
      `
      return list
    }, "")
    return `
      <div class="forecast-title">미세먼지 예보</div>
      <div class="img-set">
        ${forecastList}
      </div>
    `
  }

  imageRender(index) {
    const airImageList = document.querySelectorAll('._image')
    if(index >= airImageList.length - 1) {
      airImageList[0].style.display = "block"
      airImageList[airImageList.length-1].style.display = "none"
      return
    }
    airImageList[index].style.display = 'none'
    airImageList[index+1].style.display = 'block'

  } 
}

export default AirImageComponrnt