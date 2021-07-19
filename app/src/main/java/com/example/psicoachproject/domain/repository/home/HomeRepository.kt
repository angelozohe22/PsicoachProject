package com.example.psicoachproject.domain.repository.home

import com.example.psicoachproject.data.remote.source.dto.MeetingCalendarResponse
import com.example.psicoachproject.domain.model.DatosPersonaCita
import com.example.psicoachproject.domain.model.MeetingCalendar
import com.example.psicoachproject.domain.model.MeetingTime
import com.example.psicoachproject.domain.model.Pending

/**
 * Created by Angelo on 6/29/2021
 */
interface HomeRepository {

    suspend fun registerCita(
            name            : String,
            surname         : String,
            age             : String,
            document_id     : Int,
            document_number : String,
            gender_id       : Int,
            phone           : String,
            emails          : List<String>,
            product_id      : Int,
            disease         : String,
            description     : String,
            date            : List<String>,
            start_time      : List<String>,
            end_time        : List<String>
    ): String

    suspend fun validateDate(meetingTime: MeetingTime)
    suspend fun getMeetingsCalendar(year: String, month: String): MeetingCalendar
    suspend fun saveStateAppointment(): String
    suspend fun getPendingList(): List<Pending>
}