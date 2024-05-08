/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

/**
 *
 * @author hp
 */
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherAPI {
    //using my APIID (got by subscribing to openweather API)
    private static final String API_KEY = "7cd5be8637d1ee500c40dbd47cf44ef2";
    private static final String URL_TEMPLATE = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";
//change the city by just typing the city to the CITY string 
    public static void main(String[] args) {
        String city = "Charlotte"; 
        fetchWeather(city);
    }
//fetching data from the API "open weather" 
    private static void fetchWeather(String city) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = String.format(URL_TEMPLATE, city, API_KEY);
            HttpGet request = new HttpGet(url);
            String jsonResponse = EntityUtils.toString(httpClient.execute(request).getEntity());
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            double temp = rootNode.path("main").path("temp").asDouble();
            String weatherDescription = rootNode.path("weather").get(0).path("description").asText();
             int humidity = rootNode.path("main").path("humidity").asInt();
        double windSpeed = rootNode.path("wind").path("speed").asDouble();
        double rain = rootNode.path("rain").path("1h").asDouble(0); // Defaults to 0 if '1h' is not available
//printing all the fetched information 
        System.out.printf("Current weather in %s: %s°C, %s, Humidity: %d%%, Wind Speed: %.2f m/s, Rain (last 1 hr): %.2f mm%n", 
                          city, temp, weatherDescription, humidity, windSpeed, rain);
    } catch (Exception e) {
            e.printStackTrace();
        }
    }
}