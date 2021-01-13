package com.example.climadiario.ui

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
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climadiario.R
import com.example.climadiario.common.hide
import com.example.climadiario.common.show
import com.example.climadiario.data.models.Day
import com.example.climadiario.databinding.FragmentMainBinding
import com.example.climadiario.ui.adapter.ItemViewPager
import com.example.climadiario.ui.adapter.ViewPageAdapter
import com.example.climadiario.viewmodel.DayViewModel
import org.koin.android.ext.android.inject
import java.util.*

private const val MY_PERMISSIONS_REQUEST_LOCATION = 5254
const val END_POINT = "https://api.openweathermap.org/data/2.5/"
const val API_ID = "3cfc1d5c1a8a4e9709fd07398c77d1af"

const val SINGAPORE = "Singapur"
const val LONDON = "London"
const val MADRID = "Madrid"
const val PARIS = "Paris"
const val NEW_YORK = "New York"
const val CURRENT_CITY = "Current City"

/**
 * Primer fragment que se inicializa en el activity principal de la aplicación
 * @author Axel Sanchez
 */
@RequiresApi(Build.VERSION_CODES.N)
class MainFragment : Fragment() {

    private val viewModelFactory: DayViewModel.MyViewModelFactory by inject()

    private lateinit var locationManager: LocationManager
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    /**Se utiliza para que solo se pida la ubicación cuando quiera el desarrollador*/
    private var wantLocation = true

    private val viewModel: DayViewModel by lazy {
        ViewModelProviders.of(requireActivity(), viewModelFactory).get(DayViewModel::class.java)
    }

    private val locationListener: LocationListener = object : LocationListener {
        @SuppressLint("SetTextI18n")
        override fun onLocationChanged(location: Location) {
            latitude = location.latitude
            longitude = location.longitude
            if (wantLocation) {
                viewModel.getListDays(latitude.toString(), longitude.toString())
            }
        }

        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
        override fun onProviderEnabled(s: String) {}
        override fun onProviderDisabled(s: String) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModelAndObserve()
    }

    private var fragmentMainBinding: FragmentMainBinding? = null
    private val binding get() = fragmentMainBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentMainBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        toggleUpdates()
    }

    private fun setupViewModelAndObserve() {
        val daysObserver = Observer<List<Day>> {
            binding.zeeLoader.hide()
            binding.viewpager.show()
            WeatherFragment.staticDays = it
            binding.city.setOnClickListener { openDialogWithLocations() }
            binding.change.setOnClickListener { openDialogWithLocations() }

            val list: MutableList<ItemViewPager> = LinkedList()

            for (i in 0 until (6)) {
                list.add(
                    ItemViewPager(
                        "cancion $i",
                        WeatherFragment.newInstance(i)
                    )
                )
            }
            val adapter = ViewPageAdapter(childFragmentManager, list)
            binding.viewpager.adapter = adapter

            binding.viewpager.pageMargin = -64

        }
        viewModel.getListDaysLiveData().observe(this, daysObserver)
    }

    private fun openDialogWithLocations() {
        val dialogBuilder = AlertDialog.Builder(this.requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_cities, null)
        dialogBuilder.setView(dialogView)

        val alert = dialogBuilder.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.show()

        dialogView.findViewById<Button>(R.id.singapur).setOnClickListener {
            wantLocation = true
            binding.city.text = SINGAPORE
            binding.zeeLoader.show()
            binding.viewpager.hide()
            viewModel.getListDays("1.28967", "103.85007")
            alert.dismiss()
        }

        dialogView.findViewById<Button>(R.id.london).setOnClickListener {
            wantLocation = true
            binding.city.text = LONDON
            binding.zeeLoader.show()
            binding.viewpager.hide()
            viewModel.getListDays("51.5072", "-0.1275")
            alert.dismiss()
        }

        dialogView.findViewById<Button>(R.id.madrid).setOnClickListener {
            wantLocation = true
            binding.city.text = MADRID
            binding.zeeLoader.show()
            binding.viewpager.hide()
            viewModel.getListDays("40.4167", "-3.70325")
            alert.dismiss()
        }

        dialogView.findViewById<Button>(R.id.new_york).setOnClickListener {
            wantLocation = true
            binding.city.text = NEW_YORK
            binding.zeeLoader.show()
            binding.viewpager.hide()
            viewModel.getListDays("40.6643", "-73.9385")
            alert.dismiss()
        }

        dialogView.findViewById<Button>(R.id.paris).setOnClickListener {
            wantLocation = true
            binding.city.text = PARIS
            binding.zeeLoader.show()
            binding.viewpager.hide()
            viewModel.getListDays("48.8032", "2.3511")
            alert.dismiss()
        }

        dialogView.findViewById<Button>(R.id.current).setOnClickListener {
            wantLocation = true
            binding.city.text = CURRENT_CITY
            binding.zeeLoader.show()
            binding.viewpager.hide()
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
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun toggleUpdates() {
        if (!checkLocation()) return

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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