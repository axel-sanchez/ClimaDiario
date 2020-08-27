package com.example.climadiario.ui.interfaces

import com.example.climadiario.data.models.Base
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("onecall")
    suspend fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String, @Query("units") units: String, @Query("appid") id: String): Base
    //fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String, @Query("units") units: String, @Query("appid") id: String): Call<Base>
}