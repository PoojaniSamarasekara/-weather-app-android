# Weather API Research — Member 2

## API Chosen
OpenWeatherMap (Current Weather Data API)

## Endpoint
https://api.openweathermap.org/data/2.5/weather

## HTTP Method
GET

## Required Parameters
- q — city name (e.g. Colombo)
- appid — API key (required, sign up free at openweathermap.org/api)
- units — metric (for °C, optional but recommended)

## API Key Requirement
Free tier account required. Sign up at openweathermap.org, generate a key
from the "API keys" tab. Note: new keys can take up to 2 hours to activate.

## Example Request
https://api.openweathermap.org/data/2.5/weather?q=Colombo&appid=YOUR_KEY&units=metric

## JSON Response — Success (200 OK)
Key fields used by the app:
- name -> City name
- main.temp -> Temperature (°C)
- weather[0].description -> Weather condition
- main.humidity -> Humidity (%)
- wind.speed -> Wind speed (m/s)

## JSON Response — Error (404, invalid city)
{"cod":"404","message":"city not found"}