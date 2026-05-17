package net.edigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import net.edigest.journalApp.api.response.WeatherResponse;
import net.edigest.journalApp.cache.AppCache;

@Service
public class WeatherService {

	@Value("${weather.api.key}")
	private String API_KEY;

//	Now we get this from the DB
//	private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=CITY&units=metric&appid=API_KEY";

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AppCache appCache;
	@Autowired
	private RedisService redisService;

	public WeatherResponse getWeather(String city) throws JsonMappingException, JsonProcessingException {
		WeatherResponse weatherResponse = redisService.get("Weather_of: " + city, WeatherResponse.class);

		if (weatherResponse != null) {
			System.out.println("Data fetched from Redis");
			return weatherResponse;
		} else {

			String finalUrl = appCache.storingKeyValue.get("weather_api").replace("<city>", city).replace("<apiKey>",
					API_KEY);
			ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(finalUrl, HttpMethod.GET, null,
					WeatherResponse.class);
			WeatherResponse bodyResponse = responseEntity.getBody();
			System.out.println("Data fetched from Weather API");
			if (bodyResponse != null) {
				redisService.set("Weather_of: " + city, bodyResponse, 5l);
			}

			return bodyResponse;
		}
	}
}