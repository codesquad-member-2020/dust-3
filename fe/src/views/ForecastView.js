class ForecastView {
  constructor({
    airImageComponent, 
    generalForecastComponent, 
    forecastPlayComponent,
    airStatusArea}) {
    this.airImageComponent = airImageComponent
    this.generalForecastComponent = generalForecastComponent
    this.forecastPlayComponent = forecastPlayComponent
    this.airStatusArea = airStatusArea
  }

  initRender(forecastData) {
    this.airStatusArea.innerHTML = this.initTemplate(forecastData)
  }

  initTemplate(forecastData) {
    return `
      ${this.airImageComponent.render(forecastData)}
      ${this.generalForecastComponent.render(forecastData)}
      ${this.forecastPlayComponent.render()}
    `
  }
}

export default ForecastView