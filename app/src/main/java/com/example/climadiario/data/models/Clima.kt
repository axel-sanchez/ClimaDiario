package com.example.climadiario.data.models

data class Base(val lat: Number?, val lon: Number?, val timezone: String?, val timezone_offset: Number?, val current: Current?, val minutely: List<Minutely>?, val hourly: List<Hourly>?, val daily: List<Daily>?)

data class Current(val dt: Number?, val sunrise: Number?, val sunset: Number?, val temp: Number?, val feels_like: Number?, val pressure: Number?, val humidity: Number?, val dew_point: Number?, val uvi: Number?, val clouds: Number?, val visibility: Number?, val wind_speed: Number?, val wind_deg: Number?, val weather: List<Weather>?, val rain: Rain?)

data class Daily(val dt: Number?, val sunrise: Number?, val sunset: Number?, val temp: Temp?, val feels_like: Feels_like?, val pressure: Number?, val humidity: Number?, val dew_point: Number?, val wind_speed: Number?, val wind_deg: Number?, val weather: List<Weather>?, val clouds: Number?, val pop: Number?, val rain: Number?, val uvi: Number?)

data class Feels_like(val day: Number?, val night: Number?, val eve: Number?, val morn: Number?)

data class Hourly(val dt: Number?, val temp: Number?, val feels_like: Number?, val pressure: Number?, val humidity: Number?, val dew_point: Number?, val clouds: Number?, val visibility: Number?, val wind_speed: Number?, val wind_deg: Number?, val weather: List<Weather>?, val pop: Number?, val rain: Rain?)

data class Minutely(val dt: Number?, val precipitation: Number?)

data class Rain(val _1h: Number?)

data class Temp(val day: Number?, val min: Number?, val max: Number?, val night: Number?, val eve: Number?, val morn: Number?)

data class Weather(val id: Number?, val main: String?, val description: String?, val icon: String?)

