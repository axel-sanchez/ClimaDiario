package com.example.climadiario.domain

import com.example.climadiario.data.models.ConnectToApi
import com.example.climadiario.data.models.Daily
import com.example.climadiario.data.models.Day
import com.example.climadiario.helpers.DateHelper
import java.lang.Exception
import java.util.*
import kotlin.math.roundToInt

class DaysUseCase {

    private val dias = arrayOf("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")

    private val api = ConnectToApi.getInstance()

    suspend fun getDaysList(lat: String, lon: String): List<Day> {
        var response = api.getWeather(lat, lon).value
        println("body token: ${response!!}")

        var listDays = response.daily!!.subList(0, 6)

        var current= response.current!!.temp!!.toFloat().roundToInt().toString()

        return dailytoDays(listDays, current)
    }

    private fun dailytoDays(listDays: List<Daily>, current: String): MutableList<Day>{

        var listadoDays = mutableListOf<Day>()

        val calendar = Calendar.getInstance()
        var day = calendar.time.day
        var numberDay = calendar.time.date
        var mes = calendar.time.month + 1

        for (i in 0 until (6)) {
            if(i == 0){
                listadoDays.add(
                    Day(
                        dias[day - 1],
                        numberDay.toString(),
                        mes,
                        current,
                        listDays[i].wind_speed!!.toFloat().roundToInt().toString(),
                        listDays[i].weather!!.first().main.toString()
                    )
                )
            }else{
                listadoDays.add(
                    Day(
                        dias[day - 1],
                        numberDay.toString(),
                        mes,
                        listDays[i].temp!!.day!!.toFloat().roundToInt().toString(),
                        listDays[i].wind_speed!!.toFloat().roundToInt().toString(),
                        listDays[i].weather!!.first().main.toString()
                    )
                )
            }

            var diaYMes = DateHelper.nextDay(numberDay, mes)
            numberDay = diaYMes.first()
            mes = diaYMes.last()
            if (day == 7) day = 1
            else day++
        }

        //WeatherFragment.dias = listadoDays
        //WeatherFragment.current = current

        return listadoDays
    }
}