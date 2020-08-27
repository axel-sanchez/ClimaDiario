package com.example.climadiario.data.models

import androidx.lifecycle.MutableLiveData
import com.example.climadiario.ui.API_ID
import com.example.climadiario.ui.END_POINT
import com.example.climadiario.ui.interfaces.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConnectToApi {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(END_POINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: ApiService = retrofit.create(ApiService::class.java)


    suspend fun getWeather(lat: String, lon: String): MutableLiveData<Base> {
        var mutableLiveData = MutableLiveData<Base>()
        mutableLiveData.value = service.getWeather(lat, lon, "metric", API_ID)//.enqueue(object: Callback<Base>{
        return mutableLiveData
    }

    companion object {
        private var instance: ConnectToApi? = null

        fun getInstance(): ConnectToApi {
            if (instance == null) {
                instance = ConnectToApi()
            }
            return instance!!
        }
    }
}