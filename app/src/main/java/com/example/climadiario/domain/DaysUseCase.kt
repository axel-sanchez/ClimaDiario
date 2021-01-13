package com.example.climadiario.domain

import com.example.climadiario.data.service.ConnectToApi
import com.example.climadiario.data.models.Daily
import com.example.climadiario.data.models.Day
import com.example.climadiario.helpers.DateHelper
import java.util.*
import kotlin.math.roundToInt

/**
 * Caso de uso para los días
 * @author Axel Sanchez
 */
class DaysUseCase(private var api:ConnectToApi) {

    private val days = arrayOf("Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado")

    suspend fun getDaysWeatherList(lat: String, lon: String): List<Day> {
        var response = api.getWeather(lat, lon).value
        var listDays = response?.let{ it.daily }?.let { it.subList(0, 6) }?: kotlin.run { return dailyListToDaysList(null, "--") }
        var current = ""
        current = response?.let { it.current }?.let { it.temp }?.let { it.roundToInt().toString() }?: kotlin.run { "" }
        return dailyListToDaysList(listDays, current)
    }

    private fun dailyListToDaysList(dailyList: List<Daily>?, current: String): MutableList<Day> {

        var daysList = mutableListOf<Day>()

        val calendar = Calendar.getInstance()
        var day = calendar.time.day
        var numberDay = calendar.time.date
        var mes = calendar.time.month + 1

        for (i in 0 until (6)) {
            if (i == 0) {
                daysList.add(
                    Day(
                        days[day],
                        numberDay.toString(),
                        mes,
                        current,
                        dailyList?.let { it[i] }?.let { it.wind_speed }?.let{ it.toFloat() }?.let{ it.roundToInt().toString() }?: kotlin.run { "--" },
                        dailyList?.let { it[i] }?.let { it.weather}?.let { it.first() }?.let{ it.main.toString() }?: kotlin.run { "--" }
                    )
                )
            } else {
                daysList.add(
                    Day(
                        days[day],
                        numberDay.toString(),
                        mes,
                        dailyList?.let { it[i] }?.let { it.temp}?.let{ it.day }?.let{ it.toFloat().roundToInt().toString() }?: kotlin.run { "--" },
                        dailyList?.let { it[i] }?.let { it.wind_speed}?.let{ it.toFloat().roundToInt().toString() }?: kotlin.run { "--" },
                        dailyList?.let { it[i] }?.let { it.weather}?.let{ it.first() }?.let{ it.main }?: kotlin.run { "--" }
                    )
                )
            }

            var diaYMes = DateHelper.nextDay(numberDay, mes)
            numberDay = diaYMes.first()
            mes = diaYMes.last()
            if (day == 6) day = 0
            else day++
        }

        return daysList
    }
}