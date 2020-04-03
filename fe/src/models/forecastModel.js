class forecastModel {
  constructor() {
    this.forecastData = []
  }
  async initialize() {
    const URL = 'http://52.78.203.80:8080/forecast-info'
    const res = await fetch(URL)
    const fetchData = await res.json()
    this.forecastData = fetchData.forecastInfos
    return this.forecastData
  }
}

export default forecastModel