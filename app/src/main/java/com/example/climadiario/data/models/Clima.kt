package com.example.climadiario.data.models

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.*

val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

data class Clima (
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val coord: Coord,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val weather2: List<Weather2>,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val base: String,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val main: Main,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val visibility: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val wind: Wind,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val clouds: Clouds,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val dt: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val sys: Sys,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val timezone: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val id: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val name: String,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val cod: Long
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<Clima>(json)
    }
}

data class Clouds (
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val all: Long
)

data class Coord (
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val lon: Double,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val lat: Double
)

data class Main (
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val temp: Double,

    @get:JsonProperty("feels_like", required=true)@field:JsonProperty("feels_like", required=true)
    val feelsLike: Double,

    @get:JsonProperty("temp_min", required=true)@field:JsonProperty("temp_min", required=true)
    val tempMin: Double,

    @get:JsonProperty("temp_max", required=true)@field:JsonProperty("temp_max", required=true)
    val tempMax: Double,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val pressure: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val humidity: Long
)

data class Sys (
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val type: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val id: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val message: Double,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val country: String,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val sunrise: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val sunset: Long
)

data class Weather2 (
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val id: Long,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val main: String,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val description: String,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val icon: String
)

data class Wind (
    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val speed: Double,

    @get:JsonProperty(required=true)@field:JsonProperty(required=true)
    val deg: Long
)

