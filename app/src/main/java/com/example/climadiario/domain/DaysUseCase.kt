package com.example.climadiario.domain

import com.example.climadiario.data.service.ConnectToApi
import com.example.climadiario.data.models.Daily
import com.example.climadiario.data.models.Day
import com.example.climadiario.helpers.DateHelper
import java.lang.Exception
import java.util.*
import kotlin.math.roundToInt

/**
 * Caso de uso para los días
 * @author Axel Sanchez
 */
class DaysUseCase {

    private val dias = arrayOf("Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado")

    private val api = ConnectToApi.getInstance()

    /**
     * Recibe la [Base] y arma los días para enviarlos al viewModel
     * @param [lat] latitud de la ubicación
     * @param [lon] longitud de la ubicación
     * @return devuelve un listado de dias
     */
    suspend fun getDaysList(lat: String, lon: String): List<Day> {
        var response = api.getWeather(lat, lon).value
        println("body token: ${response!!}")

        var listDays = response.daily!!.subList(0, 6)
        var current = ""
        try {
            //current = response.current!!.temp!!.toFloat().roundToInt().toString()
            current = response.current!!.temp!!.roundToInt().toString()
            println(current)
        } catch (e: Exception){
            e.printStackTrace()
        }

        return dailytoDays(listDays, current)
    }

    /**
     * Al primer día del listado le reemplazo el clima actual para que sea más preciso
     * @param [listDays] listado del objeto Daily que recibo de la api
     * @param [current] un string con la temperatura actual
     * @return listado de días actualizado
     */
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
                        dias[day],
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
                        dias[day],
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

        return listadoDays
    }
}