package com.carlosdmg.ecoclinic.app.presentation

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapView : OnMapReadyCallback {

    private lateinit var mMap: GoogleMap


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val coords = convertLatLong("40.293901356464914", "-4.580089770692452")
        mMap.addMarker(
            MarkerOptions().position(coords).title("Consulta de la Dra. Isabel Gil Carrera")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 14f))
    }

    fun convertLatLong(lat: String, long: String): LatLng {
        return LatLng(lat.toDouble(), long.toDouble())

    }

}