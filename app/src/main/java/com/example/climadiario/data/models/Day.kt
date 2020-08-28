package com.example.climadiario.data.models

/**
 * @author Axel Sanchez
 * Esta clase se utiliza para almacenar los datos del clima de cada día
 * @param [name] nombre del día
 * @param [number] número del día
 * @param [month] mes de la fecha
 * @param [temperature] temperatura en grados centígrados
 * @param [wind] velocidad con la que corre el viento
 * @param [description] descripción corta de cómo esta el día
 */
class Day(var name: String, var number: String, var month: Int, var temperature: String, var wind: String, var description: String)