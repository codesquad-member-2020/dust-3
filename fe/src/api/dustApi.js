export function dustAPI() {
  const URL = 'http://52.78.203.80:8080/air-quality-info?x=127.0653333&y=37.49371200'
  const res = await fetch(URL)
  const fetchData = await res.json()
  return fetchData
}

export function forecastAPI() {
  const URL = 'http://52.78.203.80:8080/forecast-info'
  const res = await fetch(URL)
  const fetchData = await res.json()
  return fetchData
}