package com.mscorp.meeple.ui.main.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mscorp.meeple.R
import com.mscorp.meeple.databinding.FragmentEventBinding
import com.mscorp.meeple.databinding.FragmentEventsBinding
import com.mscorp.meeple.databinding.FragmentProfileBinding

class EventFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentEventBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMapReady(p0: GoogleMap) {
        p0.apply {
            uiSettings.isZoomControlsEnabled = true
            mapType = 1
            val sydney = LatLng(55.660404, 37.228889)
            addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("Дубаи")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            )
            moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
    }
}