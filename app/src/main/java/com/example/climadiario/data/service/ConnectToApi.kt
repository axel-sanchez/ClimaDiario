package com.example.climadiario.data.service

import androidx.lifecycle.MutableLiveData
import com.example.climadiario.data.models.Base
import com.example.climadiario.ui.API_ID
import com.example.climadiario.ui.END_POINT
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Esta clase es la encargada de conectarse a las api's
 * @author Axel Sanchez
 */
class ConnectToApi {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(END_POINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: ApiService = retrofit.create(ApiService::class.java)

    /**
     * Esta función es la encargada de retornar el clima de hoy y los 5 días siguientes
     * @param [lat] recibe la latitud de la ubicación
     * @param [lon] recibe la longitud de la ubicación
     * @return devuelve un mutableLiveData de la Base que es el objeto que mapea la respuesta
     * @sample getWeather("1.28967", "103.85007")
     */
    suspend fun getWeather(lat: String, lon: String): MutableLiveData<Base> {
        var mutableLiveData = MutableLiveData<Base>()
        mutableLiveData.value = service.getWeather(lat, lon, "metric", API_ID)
        return mutableLiveData
    }

    companion object {
        private var instance: ConnectToApi? = null

        /**
         * Utilizo Singleton para crear solo una instancia de esta clase
         */
        fun getInstance(): ConnectToApi {
            if (instance == null) {
                instance = ConnectToApi()
            }
            return instance!!
        }
    }
}