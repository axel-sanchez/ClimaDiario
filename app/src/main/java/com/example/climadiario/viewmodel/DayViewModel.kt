package com.example.climadiario.viewmodel

import androidx.lifecycle.*
import com.example.climadiario.data.models.Day
import com.example.climadiario.domain.DaysUseCase
import kotlinx.coroutines.launch

/**
 * View model de [WeatherFragment]
 * @author Axel Sanchez
 */
class DayViewModel(private val daysUseCase: DaysUseCase) : ViewModel() {

    private val listData = MutableLiveData<List<Day>>()

    private fun setListData(daysList: List<Day>) {
        listData.value = daysList
    }

    fun getListDays(lat: String, lon: String) {
        viewModelScope.launch {
            setListData(daysUseCase.getDaysWeatherList(lat, lon))
        }
    }

    fun getListDaysLiveData(): LiveData<List<Day>> {
        return listData
    }

    class MyViewModelFactory(private val daysUseCase: DaysUseCase): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(DaysUseCase::class.java).newInstance(daysUseCase)
        }
    }
}