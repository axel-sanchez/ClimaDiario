package com.example.climadiario.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.climadiario.domain.DaysUseCase

/**
 * Factory de nuestro [DayViewModel]
 * @author Axel Sanchez
 */
class MyViewModelFactory(private val daysUseCase: DaysUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DaysUseCase::class.java).newInstance(daysUseCase)
    }
}