package com.mscorp.meeple.ui.main.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.mscorp.meeple.R
import com.mscorp.meeple.ui.viewmodel.EventsViewModel
import com.mscorp.meeple.ui.viewmodel.UserViewModel
import java.util.*

class AddNewEventFragment : Fragment() {

    lateinit var eventsViewModel: EventsViewModel
    private val userViewModel: UserViewModel by navGraphViewModels(R.id.mobile_navigation)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_event, container, false)

        val numberPickerCount = view.findViewById<NumberPicker>(R.id.numberPickerCount)
        numberPickerCount.minValue = 2
        numberPickerCount.maxValue = 30
        numberPickerCount.wrapSelectorWheel = false

        val numberPickerLevel = view.findViewById<NumberPicker>(R.id.numberPickerLevel)
        numberPickerLevel.minValue = 1
        numberPickerLevel.maxValue = 3
        numberPickerLevel.wrapSelectorWheel = false

        view.findViewById<ImageView>(R.id.imageViewBackFromAddNewEvent).setOnClickListener {
            findNavController().navigate(R.id.action_navigation_addNewEvent_to_events)
        }

        view.findViewById<Button>(R.id.btn_add_event).setOnClickListener {
            addNewEvent(view)
        }

        return view
    }

    private fun addNewEvent(view: View) {
        val title = view.findViewById<EditText>(R.id.editText_title).text.toString()

        val datePicker = view.findViewById<DatePicker>(R.id.date_picker)
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val time = (calendar.time.time / 1000).toInt()

        eventsViewModel.addEvent(
            title,
            1,
            listOf(1),
            1,
            1,
            "",
            time,
            1,
            listOf(userViewModel.user.id),
            userViewModel.user.id
        )

        findNavController().navigate(R.id.action_navigation_addNewEvent_to_events)
    }
}