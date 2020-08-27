package com.example.climadiario.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.climadiario.domain.DaysUseCase

class MyViewModelFactory(private val daysUseCase: DaysUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DaysUseCase::class.java).newInstance(daysUseCase)
    }
}