package com.example.climadiario.di

import com.example.climadiario.data.service.ApiService
import com.example.climadiario.data.service.ConnectToApi
import com.example.climadiario.domain.DaysUseCase
import com.example.climadiario.ui.END_POINT
import com.example.climadiario.viewmodel.DayViewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val moduleApp = module {
    single { ConnectToApi(get()) }
    single { DaysUseCase(get()) }
    single { Retrofit.Builder()
        .baseUrl(END_POINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()}
    single { (get() as Retrofit).create(ApiService::class.java) }
}