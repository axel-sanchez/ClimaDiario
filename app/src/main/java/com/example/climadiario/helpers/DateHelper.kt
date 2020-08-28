package com.example.climadiario.helpers

/**
 * Este es un objeto que contiene funciones que ayudan a realizar acciones con la fecha
 * @author Axel Sanchez
 */
object DateHelper{
    /**
     * Esta función sirve para obtener el día siguiente y su mes correspondiente
     * @param [numberDay] es el número del día de la fecha
     * @param [mes] es el mes de la fecha
     * @return devuelve una lista de enteros, el primer elemento es el siguiente día y el segundo elemento es su mes correspondiente
     */
    fun nextDay(numberDay: Int, mes: Int): List<Int>{
        when(mes){
            1,3,5,7,8,10 -> {
                return if(numberDay == 31) listOf(1, mes+1)
                else listOf(numberDay+1, mes)
            }
            4,6,9,11 -> {
                return if(numberDay == 30) listOf(1, mes+1)
                else listOf(numberDay+1, mes)
            }
            2 -> {
                return if(numberDay == 28) listOf(1, mes+1)
                else listOf(numberDay+1, mes)
            }
            12 -> {
                return if(numberDay == 31) listOf(1, 1)
                else listOf(numberDay+1, mes)
            }
            else -> {
                return listOf(numberDay, mes)
            }
        }
    }
}