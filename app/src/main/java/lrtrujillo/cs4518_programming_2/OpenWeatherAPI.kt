package lrtrujillo.cs4518_programming_2

import retrofit2.Call
import retrofit2.http.GET

private const val API_KEY = "0f39264c7474b48acf6e115559be6f0e"

interface OpenWeatherAPI {

    @GET("/data/2.5/weather?q=Worcester&appid=${API_KEY}&units=imperial")
    fun getContents(): Call<WeatherResponse>
}