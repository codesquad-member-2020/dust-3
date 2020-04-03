class GeneralForecastComponent {
  constructor() {

  }

  render() {
    return `
      <div class="forecast-text">
        <div class="forecast-general-text">
        [미세먼지] 전 권역이 '좋음'∼'보통'으로 예상됨. 다만, 서울·경기도·충청권은 오전에 일시적으로 '나쁨' 수준일 것으로 예상됨.
        </div>
        <div class="forecast-region-text">
        서울 : 보통, 제주 : 좋음, 전남 : 보통, 전북 : 보통, 광주 : 보통, 경남 : 좋음, 경북 : 좋음, 울산 : 좋음, 대구 : 좋음, 부산 : 좋음, 충남 : 보통, 충북 : 보통, 세종 : 보통, 대전 : 보통, 영동 : 좋음, 영서 : 보통, 경기남부 : 보통, 경기북부 : 보통, 인천 : 보통
        </div>
      </div>
    `
  }
}

export default GeneralForecastComponent