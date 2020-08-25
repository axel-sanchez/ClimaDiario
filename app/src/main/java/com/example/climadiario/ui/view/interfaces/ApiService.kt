package com.example.climadiario.ui.view.interfaces

import com.example.climadiario.data.models.Base
import com.example.climadiario.data.models.Clima
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("weather")
    fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String, @Query("appid") id: String, @Query("units") units: String): Call<Clima>

    @GET("onecall")
    fun getCurrent(@Query("lat") lat: String, @Query("lon") lon: String, @Query("units") units: String, @Query("appid") id: String): Call<Base>
}