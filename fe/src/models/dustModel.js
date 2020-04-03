class dustModel {
  constructor() {
    this.fetchData = [];
    this.airInfoData = [];
    this.airQualityInfo = {
      emoji: '',
      status: '',
      density: '',
      time: '',
      location: '',
      background: ''
    }
  }
  async initialize() {
    const URL = 'http://52.78.203.80:8080/air-quality-info?x=127.0653333&y=37.49371200'
    const res = await fetch(URL)
    const fetchData = await res.json()
    this.airInfoData =  this.computedAirData(fetchData.airQualityInfos)
    return this.airInfoData
  }

  computedAirData(airInfoData) {
    airInfoData
      .filter(data => data.airQualityIndex == '좋음')
      .forEach(data => {
        data.color = '#00b6f5'
        data.backgroundColor = 'linear-gradient(180deg, #0092e0, transparent)'
        data.emoji = '😀'
      })
    airInfoData
      .filter(data => data.airQualityIndex == '보통')
      .forEach(data => {
        data.color = '#2fd248'
        data.backgroundColor = 'linear-gradient(180deg, #2fd248, transparent)'
        data.emoji = '🙂'
      })
    airInfoData
      .filter(data => data.airQualityIndex == '나쁨')
      .forEach(data => {
        data.color = '#dea138'
        data.backgroundColor = 'linear-gradient(180deg, #dea138, transparent)'
        data.emoji = '😷'
      })
    airInfoData
      .filter(data => data.airQualityIndex == '매우나쁨')
      .forEach(data => {
        data.color = '#bf2b2b'
        data.backgroundColor = 'linear-gradient(180deg, #bf2b2b, transparent)'
        data.emoji = '😱'
      })

    return airInfoData
  }
}

export default dustModel