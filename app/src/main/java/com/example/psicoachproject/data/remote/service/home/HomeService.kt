package com.example.psicoachproject.data.remote.service.home

import com.example.psicoachproject.core.Constants
import com.example.psicoachproject.data.remote.source.dto.MeetingCalendarResponse
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
//    suspend fun registerCita(@Field("name")            name            : String,
//                             @Field("surname")         surname         : String,
//                             @Field("age")             age             : String,
//                             @Field("document_id")     document_id     : Int,
//                             @Field("document_number") document_number : String,
//                             @Field("product_id")      product_id      : Int,
//                             @Field("gender_id")       gender_id       : Int,
//                             @Field("description")     description     : String,
//                             @Field("disease")         disease         : String,
//                             @Field("date_app")        date            : String,
//                             @Field("start_time_app")  start_time      : String,
//                             @Field("end_time_app")    end_time        : String,
//                             @Field("phone")           phone           : String,
//                             @Field("emails_app")      emails          : String,
//                             @Field("is_app")          is_app          : Boolean = true,
//                             @Header("Authorization")  token           : String
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


}