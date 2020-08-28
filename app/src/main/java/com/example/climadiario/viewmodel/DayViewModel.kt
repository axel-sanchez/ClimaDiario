package com.example.climadiario.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.climadiario.data.models.Day
import com.example.climadiario.domain.DaysUseCase

/**
 * View model de [WeatherFragment]
 * @author Axel Sanchez
 */
class DayViewModel(private val daysUseCase: DaysUseCase) : ViewModel() {

    private val listData = MutableLiveData<List<Day>>()

    private fun setListData(listaDays: List<Day>) {
        listData.value = listaDays
    }

    suspend fun getListDays(lat: String, lon: String) {
        setListData(daysUseCase.getDaysList(lat, lon))
    }

    fun getListDaysLiveData(): LiveData<List<Day>> {
        return listData
    }
}