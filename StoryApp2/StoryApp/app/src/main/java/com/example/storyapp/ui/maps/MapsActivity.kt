package com.example.storyapp.ui.maps

import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityMapsBinding
import com.example.storyapp.factory.ViewModelFactory
import com.example.storyapp.helper.Result
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var mapsViewModel: MapsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        mapsViewModel.maps().observe(this, { result ->
            if (result != null) {
                when(result) {
                    is Result.Success -> {
                        println(result.data)
                        for (i in result.data) {
                            mMap.addMarker(
                                MarkerOptions()
                                .position(LatLng(i.lat, i.lon))
                                .title(i.name)
                                .snippet(i.description)
                            )
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(result.data[4].lat!!.toDouble(), result.data[4].lon!!.toDouble()), 15f))
                        }
                    }
                    is Result.Error -> {
                        Toast.makeText(this@MapsActivity, "Gagal Menampilkan Peta", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        setMapStyle()
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e("MapsActivity", "Can't find style. Error: ", exception)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.title = "List Story"
    }

    private fun setupViewModel() {
        mapsViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[MapsViewModel::class.java]
    }
}