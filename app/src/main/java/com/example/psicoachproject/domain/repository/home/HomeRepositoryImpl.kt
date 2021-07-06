package com.example.psicoachproject.domain.repository.home

import com.example.psicoachproject.common.utils.toJson
import com.example.psicoachproject.data.remote.source.dto.MeetingCalendarResponse
import com.example.psicoachproject.data.remote.source.home.HomeRemoteDataSource
import com.example.psicoachproject.domain.model.DatosPersonaCita
import com.example.psicoachproject.domain.model.MeetingCalendar
import com.example.psicoachproject.domain.model.MeetingTime

/**
 * Created by Angelo on 6/29/2021
 */
class HomeRepositoryImpl(
        private val remote: HomeRemoteDataSource
): HomeRepository {

    override suspend fun registerCita(
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
    ): String {

        return remote.registerCita(
                name, surname, age, document_id, document_number,
                gender_id, phone, emails, product_id, disease,
                description, date, start_time, end_time
        ).message
    }

    override suspend fun validateDate(meetingTime: MeetingTime) {
        remote.validateDate(
                meetingTime.date,
                meetingTime.startDate,
                meetingTime.endDate)
    }

    override suspend fun getMeetingsCalendar(year: String, month: String): MeetingCalendar {
        return remote.getMeetingsCalendar(year, month)
    }
}