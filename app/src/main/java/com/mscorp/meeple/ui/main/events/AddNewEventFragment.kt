package com.mscorp.meeple.ui.main.events

import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.mscorp.meeple.R
import com.mscorp.meeple.databinding.FragmentAddEventBinding
import com.mscorp.meeple.model.BoardGame
import com.mscorp.meeple.model.Request
import com.mscorp.meeple.ui.viewmodel.EventsViewModel
import com.mscorp.meeple.ui.viewmodel.UserViewModel
import java.util.*

class AddNewEventFragment : Fragment() {

    lateinit var eventsViewModel: EventsViewModel
    private val userViewModel: UserViewModel by navGraphViewModels(R.id.mobile_navigation)
    private lateinit var binding: FragmentAddEventBinding
    private var date: Long = 0L
    private lateinit var lastCodrs: Marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        eventsViewModel.loadEvents()
        eventsViewModel.createEventLiveData.observe(viewLifecycleOwner) {
            if (it is Request.Success)
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Событие создано!")
                    .setPositiveButton("ОК") { dialog, which ->
                        findNavController().navigate(R.id.action_navigation_addNewEvent_to_events)
                    }
                    .show()
        }
    }

    private fun setOnClickListeners() {
        binding.imageViewBackFromAddNewEvent.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_addNewEvent_to_events)
        }

        binding.imageViewMenuLevel.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Выберите уровень игроков")
                .setNegativeButton("Отменить", null)
                .setPositiveButton("OK") { dialog, which ->
                    val lw: ListView = (dialog as AlertDialog).listView
                    val checkedItem = lw.adapter.getItem(lw.checkedItemPosition) as String
                    binding.textViewLevelOfPlayers.text = checkedItem
                }
                .setSingleChoiceItems(arrayOf("easy", "medium", "professional"), 0, null)
                .show()
        }

        binding.imageViewMenuFriends.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Выберите друзей, которыйх хотите пригласить")
                .setNegativeButton("Отменить", null)
                .setPositiveButton("Пригласить") { dialog, which ->
                    val lw: ListView = (dialog as AlertDialog).listView
                    val builder = StringBuilder()
                    val positions = lw.checkedItemPositions
                    for (i in 0..lw.count) {
                        if (positions.get(i)) {
                            if (builder.isNotEmpty())
                                builder.append(", ")
                            builder.append(lw.adapter.getItem(i))
                        }
                    }

                    binding.textViewSelectedFriends.text =
                        if (builder.isEmpty()) "Не выбраны" else builder.toString()
                }
                .setMultiChoiceItems(
                    userViewModel.userFriends.friends.map { it.nickname }.toTypedArray(),
                    null,
                    null
                )
                .show()
        }

        binding.imageViewMenuGames.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Выберите игры")
                .setNegativeButton("Отменить", null)
                .setPositiveButton("ОК") { dialog, which ->
                    val lw: ListView = (dialog as AlertDialog).listView
                    val builder = StringBuilder()
                    val positions = lw.checkedItemPositions
                    for (i in 0..lw.count) {
                        if (positions.get(i)) {
                            if (builder.isNotEmpty())
                                builder.append(", ")
                            builder.append(lw.adapter.getItem(i))
                        }
                    }

                    binding.textViewSelectedGames.text =
                        if (builder.isEmpty()) "Не выбраны" else builder.toString()
                }
                .setMultiChoiceItems(
                    userViewModel.games.filter { userViewModel.user.games!!.contains(it.id) }
                        .map { it.name }.toTypedArray(),
                    null,
                    null
                )
                .show()
        }

        binding.imageViewMenuDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Выберите дату")
                .build()
            datePicker.addOnPositiveButtonClickListener {
                val timePicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(12)
                        .setMinute(10)
                        .build()
                timePicker.addOnPositiveButtonClickListener {
                    val dateSel = Date(datePicker.selection!!)
                    val dataCal = Calendar.getInstance()
                    dataCal.time = dateSel
                    val calendar = Calendar.getInstance()
                    calendar.set(
                        2021,
                        dataCal.get(Calendar.MONTH),
                        dataCal.get(Calendar.DAY_OF_MONTH),
                        timePicker.hour,
                        timePicker.minute
                    )
                    binding.textViewSelectedDate.text =
                        DateFormat.format("dd.MM.yyyy; HH:mm", calendar).toString()
                    date = calendar.time.time
                }
                timePicker.show(requireActivity().supportFragmentManager, "")
            }
            datePicker.show(requireActivity().supportFragmentManager, "")
        }

        binding.imageViewMenuPlace.setOnClickListener {
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_map)
            dialog.window!!.setLayout(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT
            )

            var googleMap: GoogleMap

            MapsInitializer.initialize(requireActivity())

            val mMapView = dialog.findViewById<MapView>(R.id.mapView)
            mMapView.onCreate(dialog.onSaveInstanceState())
            mMapView.onResume()


            mMapView.getMapAsync {
                googleMap = it
                it.uiSettings.isZoomControlsEnabled = true
                it.setOnMapClickListener {
                    if (::lastCodrs.isInitialized)
                        lastCodrs.remove()
                    lastCodrs = googleMap.addMarker(
                        MarkerOptions()
                            .position(it)
                            .title("Место выбрано!")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    )!!
                }
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(55.756117, 37.621147), 12F))
            }

            val butBack = dialog.findViewById<ImageView>(R.id.imageViewBackFromMyFriends2)
            butBack?.setOnClickListener {
                dialog.hide()
            }

            val butAccept = dialog.findViewById<Button>(R.id.buttonBackFromMap)
            butAccept?.setOnClickListener {
                if (this::lastCodrs.isInitialized) {
                    binding.textViewSelectedPlace.text = "Место выбрано"
                    dialog.hide()
                }
            }

            dialog.show()
        }

        binding.button.setOnClickListener {
            val list = userViewModel.userFriends.friends.filter {
                binding.textViewSelectedFriends.text.split(", ").contains(it.nickname)
            }.map { it.id }.toMutableList()
            list.add(userViewModel.user.id)
            try {
                eventsViewModel.addEvent(
                    binding.editTextTitle.text.trim().toString(),
                    binding.editTextCountOfPlayers.text.trim().toString(),
                    userViewModel.games.filter {
                        binding.textViewSelectedGames.text.toString().split(", ").contains(it.name)
                    }.map { it.id },
                    when (binding.textViewLevelOfPlayers.text) {
                        "easy" -> 0
                        "medium" -> 1
                        "professional" -> 2
                        else -> 0
                    },
                    binding.editTextTitle2.text.toString(),
                    date,
                    list,
                    lastCodrs.position.latitude,
                    lastCodrs.position.longitude,
                    userViewModel.user.id
                )
            } catch (ex: Exception) {
                Toast.makeText(
                    context,
                    "Проверьте корректность заполнения полей!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}