package com.example.climadiario.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
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
import androidx.viewpager.widget.ViewPager
import com.agrawalsuneet.dotsloader.loaders.ZeeLoader
import com.example.climadiario.R
import com.example.climadiario.data.models.Base
import com.example.climadiario.data.models.Clima
import com.example.climadiario.data.models.Daily
import com.example.climadiario.data.models.Day
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
private const val END_POINT = "https://api.openweathermap.org/data/2.5/"
private const val API_ID = "3cfc1d5c1a8a4e9709fd07398c77d1af"

@RequiresApi(Build.VERSION_CODES.N)
class MainFragment : BaseFragment() {

    private lateinit var service: ApiService

    private lateinit var locationManager: LocationManager
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private lateinit var cities: Spinner
    private lateinit var zeeLoader: ZeeLoader

    private val locationListener: LocationListener = object : LocationListener {
        @SuppressLint("SetTextI18n")
        override fun onLocationChanged(location: Location) {
            latitude = location.latitude
            longitude = location.longitude
            println("latitud $latitude y longitud $longitude")
            //getWeather(latitude.toString(), longitude.toString())
            getCurrent(latitude.toString(), longitude.toString())
        }

        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
        override fun onProviderEnabled(s: String) {}
        override fun onProviderDisabled(s: String) {}
    }

    override fun onBackPressFragment() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        cities = view.findViewById(R.id.cities)
        zeeLoader = view.findViewById(R.id.zeeLoader)

        locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        toggleUpdates()
    }

    private fun getWeather(lat: String, lon: String) {
        service.getWeather(lat, lon, API_ID, "metric").enqueue(object : Callback<Clima> {
            override fun onResponse(call: Call<Clima>, response: Response<Clima>) {
                if (response.isSuccessful) {
                    println("body token: ${response.body()}")

                    zeeLoader.showView(false)
                    /*txtWeather.text = (response.body()!!.main.temp.roundToInt()).toString()
                    txtWeather.showView(true)
                    txtMetric.showView(true)
                    viento.showView(true)
                    cities.showView(true)
                    wordViento.showView(true)
                    description.showView(true)*/

                    //cities.text = response.body()!!.name
                    /*viento.text = "${response.body()!!.wind.speed} km/h"
                    description.text = response.body()!!.weather.first().main*/
                } else {
                    println("response token: $response")
                }
            }

            override fun onFailure(call: Call<Clima>, t: Throwable) {
                println("el error es el siguiente ${t.message}")
            }
        })
    }

    private fun getCurrent(lat: String, lon: String) {
        service.getCurrent(lat, lon, "metric", API_ID).enqueue(object : Callback<Base> {
            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                if (response.isSuccessful) {
                    println("body token: ${response.body()}")

                    zeeLoader.showView(false)
                    cities.showView(true)
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

    private fun updateViewPager(listDays: List<Daily>){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(context!!, R.array.cities, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            cities.adapter = adapter
        }

        cities.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent!!.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                (parent.getChildAt(0) as TextView).textSize = 18.0f
                when(position){
                    0 -> {
                        /*viewpager.showView(false)
                        getCurrent(latitude.toString(), longitude.toString())*/
                    }
                    1 -> {
                        /*viewpager.showView(false)
                        getCurrent(latitude.toString(), longitude.toString())*/
                    }
                    2 -> {
                        /*viewpager.showView(false)
                        getCurrent(latitude.toString(), longitude.toString())*/
                    }
                    3 -> {
                        /*viewpager.showView(false)
                        getCurrent(latitude.toString(), longitude.toString())*/
                    }
                    4 -> {
                        /*viewpager.showView(false)
                        getCurrent(latitude.toString(), longitude.toString())*/
                    }
                    5 -> {
                        /*viewpager.showView(false)
                        getCurrent(latitude.toString(), longitude.toString())*/
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        var listadoCity = mutableListOf<Day>()

        val c = Calendar.getInstance()
        var day = c.time.day
        var numberDay = c.time.date
        var mes = c.time.month+1

        var dias = arrayOf("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")

        for (i in 0 until (6)) {
            listadoCity.add(Day(
                dias[day -1],
                numberDay.toString(),
                mes,
                listDays[i].temp!!.day!!.toFloat().roundToInt().toString(),
                listDays[i].wind_speed!!.toFloat().roundToInt().toString(),
                listDays[i].weather!!.first().main.toString()
            ))
            numberDay++ //TODO: Debería validar que no se pase del límite del mes
            println("Dia: ${listadoCity.last().nombre}")
            if(day == 7) day = 1
            else day++
        }

        val listado: MutableList<ItemViewPager> = LinkedList()
        for (i in 0 until (6)) {
            listado.add(
                ItemViewPager(
                    "cancion $i",
                    WeatherFragment.newInstance(i, listadoCity)
                )
            )
        }
        val adapter = ViewPageAdapter(childFragmentManager, listado)
        viewpager.adapter = adapter

        viewpager.pageMargin = -64
    }

    private fun checkLocation(): Boolean {
        if (!isLocationEnabled())
            showAlert()
        return isLocationEnabled()
    }

    private fun showAlert() {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(context!!)
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
                context!!,
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
                            context!!,
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
