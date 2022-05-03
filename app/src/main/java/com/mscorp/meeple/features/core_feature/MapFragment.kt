package com.mscorp.meeple.features.core_feature

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mscorp.meeple.R
import com.mscorp.meeple.core.MeepleFragment
import com.mscorp.meeple.features.event_feature.EventsViewModel


internal class MapFragment : MeepleFragment<EventsViewModel>(), OnMapReadyCallback {

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
        mMapView = requireActivity().findViewById(R.id.mapViewMain)
        mMapView.onCreate(savedInstanceState)
        mMapView.getMapAsync(this)

        MapsInitializer.initialize(requireActivity())
        viewModel.loadEvents()
        viewModel.eventsLiveData.observe(viewLifecycleOwner) {
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

    override fun onMapReady(p0: GoogleMap) {
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
            val x =
                viewModel.eventsLiveData.value!!.find { event -> event.lat == it.position.latitude && event.lng == it.position.longitude }
            bundle.putSerializable(
                "event",
                x
            )
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