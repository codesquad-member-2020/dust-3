class DustView {
  constructor({ dustTabComponent, dustStatComponent, dustLogGraphComponent, airInfoArea }) {
    this.dustTabComponent = dustTabComponent
    this.dustStatComponent = dustStatComponent
    this.dustLogGraphComponent = dustLogGraphComponent
    this.airInfoArea = airInfoArea
  }

  initRender(airData) {
    this.airInfoArea.innerHTML = this.initTemplate(airData)
  }

  initTemplate(airData) {
    return `
      ${this.dustTabComponent.render()}
      ${this.dustStatComponent.initialize(airData)}
      ${this.dustLogGraphComponent.initialize(airData)}
    `
  }
}

export default DustView