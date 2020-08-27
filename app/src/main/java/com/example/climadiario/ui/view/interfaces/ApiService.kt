package com.example.climadiario.ui.view.interfaces

import com.example.climadiario.data.models.Base
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("onecall")
    fun getCurrent(@Query("lat") lat: String, @Query("lon") lon: String, @Query("units") units: String, @Query("appid") id: String): Call<Base>
}