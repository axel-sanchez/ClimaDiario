package com.example.climadiario.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.agrawalsuneet.dotsloader.loaders.ZeeLoader
import com.example.climadiario.R
import com.example.climadiario.data.models.Base
import com.example.climadiario.data.models.Daily
import com.example.climadiario.data.models.Day
import com.example.climadiario.helpers.DateHelper
import com.example.climadiario.ui.view.adapter.ItemViewPager
import com.example.climadiario.ui.view.adapter.ViewPageAdapter
import com.example.climadiario.ui.view.customs.BaseFragment
import com.example.climadiario.ui.view.interfaces.ApiService
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.math.roundToInt

private const val MY_PERMISSIONS_REQUEST_LOCATION = 5254
const val END_POINT = "https://api.openweathermap.org/data/2.5/"
const val API_ID = "3cfc1d5c1a8a4e9709fd07398c77d1af"

@RequiresApi(Build.VERSION_CODES.N)
class MainFragment : BaseFragment() {

    private var service: ApiService

    private lateinit var locationManager: LocationManager
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private lateinit var city: TextView
    private lateinit var change: TextView
    private lateinit var zeeLoader: ZeeLoader

    private var wantLocation = true

    private val dias = arrayOf("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")

    private val locationListener: LocationListener = object : LocationListener {
        @SuppressLint("SetTextI18n")
        override fun onLocationChanged(location: Location) {
            latitude = location.latitude
            longitude = location.longitude
            println("latitud $latitude y longitud $longitude")
            if (wantLocation) getCurrent(latitude.toString(), longitude.toString())
        }

        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
        override fun onProviderEnabled(s: String) {}
        override fun onProviderDisabled(s: String) {}
    }

    override fun onBackPressFragment() = false

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(END_POINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ApiService::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        city = view.findViewById(R.id.city)
        change = view.findViewById(R.id.change)
        zeeLoader = view.findViewById(R.id.zeeLoader)

        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        toggleUpdates()
    }

    fun getCurrent(lat: String, lon: String){
        service.getCurrent(lat, lon, "metric", API_ID).enqueue(object : Callback<Base> {
            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                if (response.isSuccessful) {
                    wantLocation = false
                    println("body token: ${response.body()}")

                    zeeLoader.showView(false)
                    viewpager.showView(true)

                    var listDays = response.body()!!.daily!!.subList(0, 6)

                    updateViewPager(listDays)
                } else {
                    println("response token: $response")
                }
            }

            override fun onFailure(call: Call<Base>, t: Throwable) {
                println("el error es el siguiente ${t.message}")
            }
        })
    }

    fun updateViewPager(listDays: List<Daily>) {

        city.setOnClickListener { openDialog() }
        change.setOnClickListener { openDialog() }

        var listadoDays = mutableListOf<Day>()

        val calendar = Calendar.getInstance()
        var day = calendar.time.day
        var numberDay = calendar.time.date
        var mes = calendar.time.month + 1

        for (i in 0 until (6)) {
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
            var diaYMes = DateHelper.nextDay(numberDay,mes)
            numberDay = diaYMes.first()
            mes = diaYMes.last()
            if (day == 7) day = 1
            else day++
        }

        val listado: MutableList<ItemViewPager> = LinkedList()

        WeatherFragment.dias = listadoDays

        for (i in 0 until (6)) {
            listado.add(
                ItemViewPager(
                    "cancion $i",
                    WeatherFragment.newInstance(i)
                )
            )
        }
        val adapter = ViewPageAdapter(childFragmentManager, listado)
        viewpager.adapter = adapter

        viewpager.pageMargin = -64
    }

    fun openDialog() {
        val dialogBuilder = AlertDialog.Builder(this.requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_cities, null)
        dialogBuilder.setView(dialogView)

        var alert = dialogBuilder.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.show()

        dialogView.findViewById<Button>(R.id.singapur).setOnClickListener {
            wantLocation = true
            city.text = "Singapur"
            zeeLoader.showView(true)
            viewpager.showView(false)
            getCurrent("1.28967", "103.85007")
            alert.dismiss()
        }

        dialogView.findViewById<Button>(R.id.london).setOnClickListener {
            wantLocation = true
            city.text = "London"
            zeeLoader.showView(true)
            viewpager.showView(false)
            getCurrent("51.5072", "-0.1275")
            alert.dismiss()
        }

        dialogView.findViewById<Button>(R.id.madrid).setOnClickListener {
            wantLocation = true
            city.text = "Madrid"
            zeeLoader.showView(true)
            viewpager.showView(false)
            getCurrent("40.4167", "-3.70325")
            alert.dismiss()
        }

        dialogView.findViewById<Button>(R.id.new_york).setOnClickListener {
            wantLocation = true
            city.text = "New York"
            zeeLoader.showView(true)
            viewpager.showView(false)
            getCurrent("40.6643", "-73.9385")
            alert.dismiss()
        }

        dialogView.findViewById<Button>(R.id.paris).setOnClickListener {
            wantLocation = true
            city.text = "Paris"
            zeeLoader.showView(true)
            viewpager.showView(false)
            getCurrent("48.8032", "2.3511")
            alert.dismiss()
        }

        dialogView.findViewById<Button>(R.id.current).setOnClickListener {
            wantLocation = true
            city.text = "Current City"
            zeeLoader.showView(true)
            viewpager.showView(false)
            toggleUpdates()
            alert.dismiss()
        }

        dialogView.findViewById<Button>(R.id.cancel).setOnClickListener {
            alert.dismiss()
        }
    }

    private fun checkLocation(): Boolean {
        if (!isLocationEnabled())
            showAlert()
        return isLocationEnabled()
    }

    private fun showAlert() {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        dialog.setTitle("Enable Location")
            .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación usa esta app")
            .setPositiveButton("Configuración de ubicación") { _, _ ->
                var myIntent: Intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(myIntent)
            }
            .setNegativeButton("Cancelar") { _, _ -> }
        dialog.show()
    }

    private fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun toggleUpdates() {
        if (!checkLocation()) return

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
        } else {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0,
                10.0f,
                locationListener
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            0,
                            10.0f,
                            locationListener
                        )
                        return
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }

    }
}
