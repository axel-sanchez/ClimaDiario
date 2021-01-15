package com.example.climadiario.application

import android.app.Application
import android.widget.Toast
import com.example.climadiario.data.service.ConnectToApi
import com.example.climadiario.di.moduleApp
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, listOf(moduleApp))
    }
}