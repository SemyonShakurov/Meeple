package com.mscorp.meeple.ui.main

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mscorp.meeple.R
import com.mscorp.meeple.ui.viewmodel.EventsViewModel


class MapFragment : Fragment(), OnMapReadyCallback {

    lateinit var eventsViewModel: EventsViewModel
    lateinit var mMapView: MapView
    lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)
        mMapView = requireActivity().findViewById<MapView>(R.id.mapViewMain)
        mMapView.onCreate(savedInstanceState)
        mMapView.getMapAsync(this)

        MapsInitializer.initialize(requireActivity())
        eventsViewModel.loadEvents()
        eventsViewModel.eventsLiveData.observe(viewLifecycleOwner) {
            for (i in it) {
                googleMap.addMarker(
                    MarkerOptions()
                        .title(
                            i.title + ", " + DateFormat.format("dd.MM.yyyy. HH:mm", i.date)
                                .toString()
                        )
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .position(LatLng(i.lat, i.lng))
                )
            }
        }


    }

    override fun onMapReady(p0: GoogleMap?) {
        if (p0 != null) {
            googleMap = p0
        }
        p0?.apply {
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
            mapType = 3
            moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(55.756117, 37.621147), 12F))
        }
        googleMap.setOnInfoWindowClickListener {
            val bundle = Bundle()
            val x = eventsViewModel.eventsLiveData.value!!.find { event -> event.lat == it.position.latitude && event.lng == it.position.longitude }
            bundle.putSerializable(
                "event",
                x)
            findNavController().navigate(R.id.action_navigation_map_to_eventFragment, bundle)
        }
    }

    override fun onResume() {
        mMapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }
}