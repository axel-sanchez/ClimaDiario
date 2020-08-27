package com.example.climadiario.ui.view

import com.example.climadiario.ui.API_ID
import com.example.climadiario.ui.END_POINT
import com.example.climadiario.ui.interfaces.ApiService
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainFragmentTest{

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(END_POINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: ApiService = retrofit.create(ApiService::class.java)

    @Test fun getLondon(){
        Assert.assertEquals(true, service.getWeather("51.5072", "-0.1275", "metric", API_ID).execute().isSuccessful)
    }

    @Test fun getSingapur(){
        Assert.assertEquals(true, service.getWeather("1.28967", "103.85007", "metric", API_ID).execute().isSuccessful)
    }

    @Test fun getMadrid(){
        Assert.assertEquals(true, service.getWeather("40.4167", "-3.70325", "metric", API_ID).execute().isSuccessful)
    }

    @Test fun getNewYork(){
        Assert.assertEquals(true, service.getWeather("40.6643", "-73.9385", "metric", API_ID).execute().isSuccessful)
    }

    @Test fun getParis(){
        Assert.assertEquals(true, service.getWeather("48.8032", "2.3511", "metric", API_ID).execute().isSuccessful)
    }

    @Test fun forzarErrorConLetras(){
        Assert.assertEquals(false, service.getWeather("lat", "lon", "metric", API_ID).execute().isSuccessful)
    }

    @Test fun forzarErrorConNumeros(){
        Assert.assertEquals(false, service.getWeather("-2", "-300000000000000000000000000000000000000000000", "metric", API_ID).execute().isSuccessful)
    }
}