import Controller from './controller/index.js'
import DustModel from './models/dustModel.js'
import ForecastModel from './models/forecastModel.js'
import DustView from './views/DustView.js'
import ForecastView from './views/ForecastView.js'
import DustTabComponent from './components/DustTabComponent.js'
import DustStatComponent from './components/DustStatComponent.js'
import DustLogGraphComponent from './components/DustLogGraphComponent.js'
import AirImageComponent from './components/AirImageComponent.js'
import GeneralForecastComponent from './components/GeneralForecastComponent.js'
import ForecastPlayComponent from './components/ForecastPlayComponent.js'
import {elementSelector} from './utils/common.js'

const dustTabComponent = new DustTabComponent()
const dustStatComponent = new DustStatComponent()
const dustLogGraphComponent = new DustLogGraphComponent()
const generalForecastComponent = new GeneralForecastComponent()
const forecastPlayComponent = new ForecastPlayComponent()

const airImageComponent = new AirImageComponent()
const dustView = new DustView({
  dustTabComponent,
  dustStatComponent,
  dustLogGraphComponent,
  airInfoArea: elementSelector('.dust_wrap')
})
const forecastView = new ForecastView({
  airImageComponent,
  generalForecastComponent,
  forecastPlayComponent,
  airStatusArea: elementSelector('.air-status')
})

const dustModel = new DustModel()
const forecastModel = new ForecastModel()

const controller = new Controller({
  model: { dustModel, forecastModel },
  view: { dustView, forecastView },
  component: { dustStatComponent, dustLogGraphComponent, airImageComponent }
})

window.addEventListener("DOMContentLoaded", () => {
  controller
})

