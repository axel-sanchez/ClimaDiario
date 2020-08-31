package com.example.climadiario.domain

import com.example.climadiario.ui.API_ID
import com.example.climadiario.ui.END_POINT
import com.example.climadiario.data.service.ApiService
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DaysUseCaseTest {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(END_POINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var service: ApiService = retrofit.create(ApiService::class.java)

    @Test
    fun getLondon() {
        assertTrue(service.getWeatherTest("51.5072", "-0.1275", "metric", API_ID).execute().isSuccessful)
    }

    @Test
    fun getSingapur() {
        assertTrue(service.getWeatherTest("1.28967", "103.85007", "metric", API_ID).execute().isSuccessful)
    }

    @Test
    fun getMadrid() {
        assertTrue(service.getWeatherTest("40.4167", "-3.70325", "metric", API_ID).execute().isSuccessful)
    }

    @Test
    fun getNewYork() {
        assertTrue(service.getWeatherTest("40.6643", "-73.9385", "metric", API_ID).execute().isSuccessful)
    }

    @Test
    fun getParis() {
        assertTrue(service.getWeatherTest("48.8032", "2.3511", "metric", API_ID).execute().isSuccessful)
    }

    @Test
    fun forzarErrorConLetras() {
        assertFalse(service.getWeatherTest("lat", "lon", "metric", API_ID).execute().isSuccessful)
    }

    @Test
    fun forzarErrorConNumeros() {
        assertFalse(service.getWeatherTest("-2", "-300000000000000000000000000000000000000000000", "metric", API_ID).execute().isSuccessful)
    }
}