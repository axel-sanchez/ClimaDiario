package com.example.climadiario.helpers

object DateHelper{
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