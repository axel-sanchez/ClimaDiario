package com.example.climadiario.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.climadiario.data.models.Day
import com.example.climadiario.databinding.FragmentWeatherBinding

const val ARG_POS = "pos"

class WeatherFragment : Fragment() {
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POS)
        }
    }

    private var fragmentMainBinding: FragmentWeatherBinding? = null
    private val binding get() = fragmentMainBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentMainBinding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentMainBinding = null
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viento.text = "${staticDays[position].wind} km/h"
        binding.description.text = staticDays[position].description
        binding.txtWeather.text = staticDays[position].temperature

        when (position) {
            0 -> binding.day.text = "Hoy"
            1 -> binding.day.text = "Mañana"
            else -> binding.day.text = staticDays[position].name
        }

        var mesString = when (staticDays[position].month) {
            1 -> "Enero"
            2 -> "Febrero"
            3 -> "Marzo"
            4 -> "Abril"
            5 -> "Mayo"
            6 -> "Junio"
            7 -> "Julio"
            8 -> "Agosto"
            9 -> "Septiembre"
            10 -> "Octubre"
            11 -> "Noviembre"
            12 -> "Diciembre"
            else -> "No existe el mes"
        }

        binding.date.text = "${staticDays[position].number} de $mesString"
    }

    companion object {
        var staticDays: List<Day> = mutableListOf()

        @JvmStatic
        fun newInstance(position: Int) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POS, position)
                }
            }
    }
}