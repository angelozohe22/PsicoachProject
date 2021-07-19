package com.example.psicoachproject.ui.modules.home.client.fragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.psicoachproject.R
import com.example.psicoachproject.common.utils.*
import com.example.psicoachproject.databinding.ItemCitaHourBinding
import com.example.psicoachproject.domain.model.MeetingTime
import com.example.psicoachproject.common.design.DatePickerFragment
import java.util.*

/**
 * Created by Angelo on 7/3/2021
 */
class MeetingAdapter(
        private val meetingListener: MeetingListener
): RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder>() {

    private var _list = emptyList<String>()
    private var validateList = mutableListOf<Boolean>()
    private val dateList = mutableListOf<String>()
    private val startTimeList = mutableListOf<String>()
    private val endTimeList = mutableListOf<String>()

    fun setData(data: List<String>){
        this._list = data
        this.validateList = MutableList(data.size){ false }
        notifyDataSetChanged()
    }

    fun setNewMeeting(date: String, startTime: String, endTime: String){
        dateList.add(date)
        startTimeList.add(startTime)
        endTimeList.add(endTime)
    }

    fun getMeetingValues(completion: (List<String>,
                                      List<String>,
                                      List<String>) -> Unit){
        completion(dateList, startTimeList, endTimeList)
    }

    fun getValidateList() = validateList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingAdapter.MeetingViewHolder {
        return MeetingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cita_hour, parent, false))
    }

    override fun onBindViewHolder(holder: MeetingAdapter.MeetingViewHolder, position: Int) {
        holder.apply {
            bindView(position)
            textInputValidatorItem()
        }
    }

    override fun getItemCount(): Int = _list.size

    inner class MeetingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val meetingBinding = ItemCitaHourBinding.bind(itemView)

        fun bindView(position: Int){
            clearInputs(position)
            println("Pasooooo $position")
            meetingBinding.apply {
                titleCita.text = "Cita ${position+1}"

                //----- time dropdown -----
                etFechaCita.setOnClickListener {
                    val datePicker = DatePickerFragment{ day, month, year ->
                        val daySelected = getFormatDateString(day, month + 1, year)

                        if (Date().toParseString() > getFormatDateString2(daySelected)){
                            lyContainerCita.showSnackBar("Elige una fecha valida")
                            return@DatePickerFragment
                        }

                        etFechaCita.setText(daySelected)
                        getSchedulesPerDay(daySelected){ scheduleLsit ->
                            cddlHoraIn.isEnabled = scheduleLsit.isNotEmpty()
                            ddlHoraIn.apply {
                                setAdapter(ArrayAdapter(itemView.context, R.layout.item_drop_down, scheduleLsit))
                                setOnItemClickListener { _, _, position, _ ->
                                    if (scheduleLsit.lastIndex != position) etHoraOut.setText(scheduleLsit[position+1])
                                    else {
                                        lyContainerCita.showSnackBar("No puedes agendar una meeting en este horario")
                                        etHoraOut.text?.clear()
                                    }

                                }
                            }
                        }
                    }
                    meetingListener.showDatePicker(datePicker)
                }

                //button click to validate meeting
                btnValidateTime.setOnClickListener { validateMeeting(position) }

                //to clear fields
                cetFechaCita.setEndIconOnClickListener{ clearInputs(position) }
            }
        }

        private fun validateMeeting(position: Int){
            meetingBinding.apply {
                validateButton(false)
                showProgress()
                meetingListener.validateMeeting(
                        MeetingTime(date = getFormatDateString2(etFechaCita.text?.trim().toString()),
                                startDate = ddlHoraIn.text.trim().toString(),
                                endDate = etHoraOut.text?.trim().toString() )
                ){ isValid ->
                    hideProgress()
                    //Activar btn
                    validateList[position] = isValid
//                        validateList.get(position) = isValid
                    println("Llega?? $isValid")
                    validateList.forEach {
                        println("Value  $it")
                    }
                }
            }
        }

        fun textInputValidatorItem(){
            validateButton(false)
            meetingBinding.cddlHoraIn.isEnabled = false

            meetingBinding.apply {
                val validator = afterTextChanged {
                    validateButton(!isNullOrEmpty(etFechaCita.text?.trim().toString())
                                   && !isNullOrEmpty(ddlHoraIn.text.trim().toString())
                                   && !isNullOrEmpty(etHoraOut.text?.trim().toString()))
                }

                etFechaCita.addTextChangedListener(validator)
                ddlHoraIn.addTextChangedListener(validator)
                etHoraOut.addTextChangedListener(validator)
            }
        }

        private fun validateButton(validator: Boolean){
            meetingBinding.btnValidateTime.apply {
                isEnabled = validator
                if (isEnabled) setBackgroundResource(R.drawable.btn_corner)
                else setBackgroundResource(R.drawable.btn_corner_disable)
            }
        }

        private fun showProgress(){
            meetingBinding.apply {
                progress.visibility = View.VISIBLE
                btnValidateTime.visibility = View.INVISIBLE
            }
        }

        private fun hideProgress(){
            meetingBinding.apply {
                progress.visibility = View.GONE
                btnValidateTime.visibility = View.VISIBLE
            }
        }

        private fun clearInputs(position: Int){
            meetingBinding.apply {
                ddlHoraIn.setAdapter(ArrayAdapter(itemView.context, R.layout.item_drop_down, emptyList<String>()))
                cddlHoraIn.isEnabled = false
                validateList[position] = false

                /*if(dateList.size > 0
                   && startTimeList.size > 0
                   && endTimeList.size > 0) {
                    dateList.removeAt(position)
                    startTimeList.removeAt(position)
                    endTimeList.removeAt(position)
                }*/

                meetingListener.changeValidatorState()
                ddlHoraIn.text.clear()
                etFechaCita.text?.clear()
                etHoraOut.text?.clear()
            }
        }

    }
}

interface MeetingListener{
    fun showDatePicker(datePicker: DatePickerFragment)
    fun validateMeeting(horaCita: MeetingTime, completion:(Boolean) -> Unit )
    fun changeValidatorState()
}