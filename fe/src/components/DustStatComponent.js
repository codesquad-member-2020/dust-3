import {elementSelector} from '../utils/common.js'

class DustStatComponent {
  constructor() {
  }
  render({ emoji, airQualityIndex, pm10, currentTime, location, backgroundColor }) {
    return `
    <section class="dust_info" style="background: ${backgroundColor}">
      <div class="dust_title_wrap">
        <h1 class="dust_title">${ '미세먼지 앱' }</h1>
      </div>
      <div class="dust_stat_image">${ emoji }</div>
      <div>
        <span class="dust_stat">${ airQualityIndex }</span>
      </div>
      <div class="dust_dt">
      <div class="dust_time">${ currentTime }</div>
        <span class="dust_density">${ pm10 }</span>
        <span>𝜇g/m³</span>
      </div>
      <div>
        <strong class="dust_place">${ location }</strong>
        <span> 측정소 기준</span>
      </div>
    </section> 
    `
  }

  initialize(airData) {
    return this.render(airData[0])
  }

  renderAirInfo(airData)  {
    if(airData == undefined) return
    elementSelector('.dust_info').style.background = `${airData.backgroundColor}`
    elementSelector('.dust_stat_image').innerText = `${airData.emoji}`
    elementSelector('.dust_stat').innerText = `${airData.airQualityIndex}`
    elementSelector('.dust_density').innerText = `${airData.pm10}`
    elementSelector('.dust_time').innerText = `${airData.currentTime}`
  }
}

export default DustStatComponent