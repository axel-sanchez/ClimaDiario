package com.example.climadiario.data.service

import com.example.climadiario.data.models.Base
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * Interface para generar las peticiones a la api
 * @author Axel Sanchez
 */
interface ApiService {
    /**
     * api get que busca el clima actual y el de los cinco días próximos
     * @param [lat] latitud de la ubicación
     * @param [lon] longitud de la ubicación
     * @param [units] unidad de medida de la temperatura
     * @param [id] id de la appi de open weather map
     * @return retorna un objeto [Base]
     */
    @GET("onecall")
    suspend fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String, @Query("units") units: String, @Query("appid") id: String): Response<Base>

    @GET("onecall")
    fun getWeatherTest(@Query("lat") lat: String, @Query("lon") lon: String, @Query("units") units: String, @Query("appid") id: String): Call<Base>
}