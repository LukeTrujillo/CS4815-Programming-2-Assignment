package lrtrujillo.cs4518_programming_2

import com.google.gson.annotations.SerializedName


data class Weather (
    var temp:Float
)


data class WeatherResponse (
    @SerializedName("main")
    var weather: Weather
);