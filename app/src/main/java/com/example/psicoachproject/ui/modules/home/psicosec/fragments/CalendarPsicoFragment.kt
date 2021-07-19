package com.example.psicoachproject.ui.modules.home.psicosec.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psicoachproject.R
import com.example.psicoachproject.common.compactcalendarview.CompactCalendarView
import com.example.psicoachproject.common.compactcalendarview.domain.Event
import com.example.psicoachproject.common.utils.capitalizeFully
import com.example.psicoachproject.common.utils.dateStringToTimeMilli
import com.example.psicoachproject.common.utils.getColorWithAlpha
import com.example.psicoachproject.common.utils.toParseString
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.databinding.FragmentCalendarPsicoBinding
import com.example.psicoachproject.domain.model.MeetingCalendar
import com.example.psicoachproject.ui.modules.home.HomeActivity
import com.example.psicoachproject.ui.modules.home.client.activities.viewmodel.HomeViewModel
import com.example.psicoachproject.ui.modules.home.client.fragments.adapter.EventAdapter
import java.text.SimpleDateFormat
import java.util.*

class CalendarPsicoFragment : Fragment() {

    private var _binding: FragmentCalendarPsicoBinding? = null
    private val binding get() = _binding!!

    private val eventsAdapter by lazy { EventAdapter() }
    private lateinit var viewModel: HomeViewModel

    private var lastDate: Date? = null
    private var lastYear: String = ""
    private var lastMonth: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentCalendarPsicoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HomeActivity).viewModelCita
        setupRecycler()
        setUpCalendar()
    }

    private fun setupRecycler(){
        binding.rvEvents.apply {
            adapter = eventsAdapter
            layoutManager = LinearLayoutManager( context,
                LinearLayoutManager.VERTICAL,false)
        }
    }

    private fun setUpCalendar(){
        val calendar = binding.calendar

        val date = Date()
        val mes = resources.getStringArray(R.array.meses_array)[date.month]
        lastYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(date)
        lastMonth = SimpleDateFormat("MM", Locale.getDefault()).format(date)

        getEvents(lastYear, lastMonth)
        binding.lblMonth.text = "$mes $lastYear".capitalizeFully()

        calendar.setCalendarBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
        calendar.setCurrentSelectedDayBackgroundColor(getColorWithAlpha(Color.WHITE, 0.2F))
        calendar.setCurrentDayBackgroundColor(getColorWithAlpha(ContextCompat.getColor(requireContext(), R.color.primary), 0.5F))

        calendar.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                if (lastDate == dateClicked) { return }
                lastDate = dateClicked
                lastYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(dateClicked)
                lastMonth = SimpleDateFormat("MM", Locale.getDefault()).format(dateClicked)
                getEvents(lastYear, lastMonth)
            }

            override fun onMonthScroll(date: Date) {
                if (lastDate == date) { return }
                lastDate  = date
                val mes = resources.getStringArray(R.array.meses_array)[date.month]
                lastYear  = SimpleDateFormat("yyyy", Locale.getDefault()).format(date)
                lastMonth = SimpleDateFormat("MM", Locale.getDefault()).format(date)
                getEvents(lastYear, lastMonth)
                binding.lblMonth.text = "$mes $lastYear".capitalizeFully()
            }
        })
    }

    private fun getEvents(year: String, month: String){
        viewModel.getMeetingCalendar(year, month).observe(viewLifecycleOwner){
            it?.let { result ->
                when(result){
                    is Resource.Loading -> {
                        hideEmpty()
                        showProgress()
                    }
                    is Resource.Success -> {
                        hideProgress()
                        loadEvent(result.data)
                        val currentEventList = result.data.eventList.filter { lastDate?.toParseString() == it.date }

                        if (currentEventList.isEmpty())showEmpty()
                        else eventsAdapter.setData(currentEventList)
                        println("setData -->> $currentEventList")
//                        eventsAdapter.setData(currentEventList)
                    }
                    is Resource.Failure -> {
                        hideProgress()
                        showEmpty()
                    }
                }
            }
        }
    }

    private fun loadEvent(data: MeetingCalendar){
        removeAllEvents()
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = format.format(Date())

        data.eventList.forEach {
            if (it.date == currentDate){
                addEvent(Date().time)
            }else{
                dateStringToTimeMilli(it.date)?.let { value ->
                    addEvent(value)
                }
            }
        }
    }

    private fun addEvent(time: Long) {
        binding.calendar.addEvent(Event(Color.WHITE, time))
    }

    private fun removeAllEvents() {
        binding.calendar.removeAllEvents()
    }

    private fun showProgress(){
        binding.apply {
            progress.visibility = View.VISIBLE
            lblProgress.visibility = View.VISIBLE
        }
    }

    private fun hideProgress(){
        binding.apply {
            progress.visibility = View.GONE
            lblProgress.visibility = View.GONE
        }
    }

    private fun showEmpty(){
        binding.apply {
            imageEmpty.visibility = View.VISIBLE
            lblEmpty.visibility = View.VISIBLE
        }
    }

    private fun hideEmpty(){
        binding.apply {
            imageEmpty.visibility = View.GONE
            lblEmpty.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}