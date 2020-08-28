package com.example.climadiario.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.climadiario.R
import com.example.climadiario.data.models.Day
import com.example.climadiario.ui.customs.BaseFragment
import kotlinx.android.synthetic.main.fragment_weather.*

const val ARG_POS = "pos"

class WeatherFragment : BaseFragment() {

    private var position = 0

    override fun onBackPressFragment() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viento.text = "${dias[position].wind} km/h"
        description.text = dias[position].description
        txtWeather.text = dias[position].temperature

        when (position) {
            0 -> day.text = "Hoy"
            1 -> day.text = "Mañana"
            else -> day.text = dias[position].name
        }

        var mesString = when (dias[position].month) {
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

        date.text = "${dias[position].number} de $mesString"
    }

    companion object {
        var dias: List<Day> = mutableListOf()

        @JvmStatic
        fun newInstance(position: Int) =//, dias: MutableList<Day>) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POS, position)
                }
            }
    }
}