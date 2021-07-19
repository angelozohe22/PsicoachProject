package com.example.psicoachproject.data.remote.service.home

import com.example.psicoachproject.core.Constants
import com.example.psicoachproject.data.remote.source.dto.MeetingCalendarResponse
import com.example.psicoachproject.data.remote.source.dto.PendingResponse
import com.example.psicoachproject.data.remote.source.dto.UserResponse
import retrofit2.http.*

/**
 * Created by Angelo on 6/29/2021
 */
interface HomeService {

    @POST(Constants.SERVICE_ROUTE_REGISTER)
    @Headers("content-type: application/json")
    suspend fun registerCita(@Body json: Map<String, String>,
                             @Header("Authorization")token: String
    ): UserResponse

    @GET(Constants.SERVICE_ROUTE_VALIDATE_DATE)
    suspend fun validateDate(
            @Query("date")          date      : String,
            @Query("start_time")    startTime : String,
            @Query("end_time")      endTime   : String,
            @Header("Authorization")token     : String)

    @GET(Constants.SERVICE_ROUTE_MEETINGS_CALENDAR)
    suspend fun getMeetingsCalendar(
        @Query("year")          date      : String,
        @Query("month")         startTime : String,
        @Header("Authorization")token     : String): MeetingCalendarResponse

    //News
    @POST(Constants.SERVICE_ROUTE_REGISTER)
    @Headers("content-type: application/json")
    suspend fun saveStateAppointment(): UserResponse

    @GET(Constants.SERVICE_ROUTE_MEETINGS_PENDING)
    suspend fun getPendingList(
        @Header("Authorization")token     : String): List<PendingResponse>


}