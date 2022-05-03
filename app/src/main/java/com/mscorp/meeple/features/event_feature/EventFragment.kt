package com.mscorp.meeple.features.event_feature

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mscorp.meeple.R
import com.mscorp.meeple.core.MeepleFragment
import com.mscorp.meeple.databinding.FragmentEventBinding
import com.mscorp.meeple.features.core_feature.view_models.UserViewModel
import com.mscorp.meeple.model.Event
import com.mscorp.meeple.model.Request

internal class EventFragment : MeepleFragment<UserViewModel>(), OnMapReadyCallback {

    private lateinit var binding: FragmentEventBinding
    private lateinit var event: Event
    private lateinit var mMapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        event = arguments?.getSerializable("event") as Event
        binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (event.members.map { it.id }.contains(viewModel.user.id)) {
            binding.buttonBackFromEvent.text = "Отказаться"
            binding.buttonBackFromEvent.setOnClickListener {
                viewModel.unsubscribeEvent(event.id, viewModel.user.id)
                binding.imageViewBack2.setOnClickListener {
                    findNavController().navigate(R.id.action_eventFragment_to_navigation_events)
                }
            }
        } else {
            binding.buttonBackFromEvent.text = "Учавствовать"
            binding.buttonBackFromEvent.setOnClickListener {
                viewModel.subscribeEvent(event.id, viewModel.user.id)
            }
            binding.imageViewBack2.setOnClickListener {
                findNavController().navigate(R.id.action_eventFragment_to_navigation_map)
            }
        }

        binding.textView14.text = event.title
        binding.textView19.text = DateFormat.format("dd.MM.yyyy; HH:mm", event.date).toString()
        binding.textView21.text = event.count
        when (event.playersLevel) {
            0 -> binding.view7.backgroundTintList =
                AppCompatResources.getColorStateList(requireContext(), R.color.black)
            1 -> binding.view6.backgroundTintList =
                AppCompatResources.getColorStateList(requireContext(), R.color.black)
            2 -> binding.view8.backgroundTintList =
                AppCompatResources.getColorStateList(requireContext(), R.color.black)
        }
        val builder = StringBuilder()
        for (i in viewModel.games.filter { event.games.contains(it.id) }) {
            if (builder.isNotEmpty())
                builder.append(", ")
            builder.append(i.name)
        }
        binding.textView28.text = builder.toString()
        builder.clear()
        for (i in event.members) {
            if (builder.isNotEmpty())
                builder.append(", ")
            builder.append(i.nickname)
        }
        binding.textView34.text = builder.toString()
        binding.textView31.text = event.info


        var googleMap: GoogleMap
        MapsInitializer.initialize(requireActivity())
        mMapView = requireActivity().findViewById(R.id.mapView2)
        mMapView.onCreate(savedInstanceState)
        mMapView.getMapAsync(this)

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.subscribeEventResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Request.Success -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Вы записаны!")
                        .setPositiveButton("OK") { _, _ ->
                            findNavController().navigate(R.id.action_eventFragment_to_navigation_events)
                        }.show()
                    viewModel.subscribeEventResponse.value = null
                }
                is Request.Failure -> {
                    Toast.makeText(requireContext(), "Запись не удалась", Toast.LENGTH_SHORT).show()
                    viewModel.subscribeEventResponse.value = null
                }
                else -> Unit
            }
        }

        viewModel.unsubscribeEventResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Request.Success -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Вы больше участвуете в данном мероприятии")
                        .setPositiveButton("OK") { _, _ ->
                            findNavController().navigate(R.id.action_eventFragment_to_navigation_events)
                        }.show()
                    viewModel.unsubscribeEventResponse.value = null
                }
                is Request.Failure -> {
                    viewModel.unsubscribeEventResponse.value = null
                    Toast.makeText(requireContext(), "Отмена не удалась", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    override fun onMapReady(p0: GoogleMap) {
        p0.apply {
            uiSettings.isZoomControlsEnabled = false
            mapType = 3
            val sydney = LatLng(event.lat, event.lng)
            addMarker(
                MarkerOptions()
                    .position(sydney)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            )
            moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15F))
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