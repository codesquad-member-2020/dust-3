class DustLogGraphComponent {
  constructor() {
    this.airQualityInfo = []
  }

  render(airData) {
    this.airQualityInfo = airData
    const dustList = airData
      .reduce((list, airInfo) => {
        list += 
          `<li><h2>${airInfo.currentTime}</h2><Strong>${airInfo.pm10}</Strong></li>`
          return list
      }, "")

    return `
      <section class="dust_graph">
        <ul class="graph">
          ${dustList}
        </ul>
      </section>`
  }

  initialize(airData) {
    return this.render(airData)
  }

  renderdustData() {
    const data = this.airQualityInfo
    
    $('.graph li').each(function(index) {
      let color = data[index].color;
      $(this).children('h2').css('color', color).text(data[index].currentTime);
    
      let bar = $(this).children('strong');
      bar.css('background-color', color);
      bar.delay(300*index).animate({ 'width': data[index].pm10 + '%' }, {
        duration: 3000,
        step: function(now) {
          $(this).text(Math.round(now));
        }
      })
    })
  }
}



export default DustLogGraphComponent