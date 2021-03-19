package com.mscorp.meeple.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mscorp.meeple.R


class MapFragment : Fragment(), OnMapReadyCallback {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
       p0?.apply {
           uiSettings.isZoomControlsEnabled = true
           mapType = 2
           val sydney = LatLng(55.660404, 37.228889)
           addMarker(
               MarkerOptions()
                   .position(sydney)
                   .title("Дубаи")
           )
           moveCamera(CameraUpdateFactory.newLatLng(sydney))
       }
    }


}