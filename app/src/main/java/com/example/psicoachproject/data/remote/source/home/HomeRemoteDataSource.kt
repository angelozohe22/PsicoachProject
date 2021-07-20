package com.example.psicoachproject.data.remote.source.home

import com.example.psicoachproject.data.remote.source.dto.PendingResponse
import com.example.psicoachproject.data.remote.source.dto.UserResponse
import com.example.psicoachproject.domain.model.MeetingCalendar

/**
 * Created by Angelo on 6/29/2021
 */
interface HomeRemoteDataSource {

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
    ): UserResponse

    suspend fun validateDate(
            date: String,
            startTime: String,
            endTime: String)

    suspend fun getMeetingsCalendar(year: String, month: String): MeetingCalendar
    suspend fun getPendingList(): List<PendingResponse>
    suspend fun changeStateAppointment(id: String, status: String): String
    suspend fun sendComment(id: Int, message: String): String

}