package com.example.psicoachproject.data.remote.source.home

import com.example.psicoachproject.common.utils.toDateStringDate
import com.example.psicoachproject.common.utils.toJson
import com.example.psicoachproject.core.Constants
import com.example.psicoachproject.core.aplication.preferences
import com.example.psicoachproject.data.remote.RetrofitBuilder
import com.example.psicoachproject.data.remote.service.home.Meet
import com.example.psicoachproject.data.remote.service.home.HomeService
import com.example.psicoachproject.data.remote.source.dto.MeetingCalendarResponse
import com.example.psicoachproject.data.remote.source.dto.UserResponse
import com.example.psicoachproject.domain.model.MeetingCalendar
import com.example.psicoachproject.domain.model.MeetingEvent
import com.example.psicoachproject.domain.model.PendingMeetingEvent

/**
 * Created by Angelo on 6/29/2021
 */
class HomeRemoteDataSourceImpl: HomeRemoteDataSource {

    private val retrofit = RetrofitBuilder.getConexionRetrofit()
    private val service = retrofit.create(HomeService::class.java)

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
    ): UserResponse {

        val asd = Meet(
            name            =  name           ,
            surname         =  surname        ,
            age             =  age            ,
            document_id     =  document_id    ,
            document_number =  document_number,
            gender_id       =  gender_id      ,
            phone           =  phone          ,
            emails          =  emails.toJson(),
            product_id      =  product_id     ,
            disease         =  disease        ,
            description     =  description    ,
            date            =  date.toJson(),
            start_time      =  start_time.toJson(),
            end_time        =  end_time.toJson()
        ).toJson()

        println("---->>> json:::: $asd")

        return service.registerCita(
            name            =  name,
            surname         =  surname,
            age             =  age,
            document_id     =  document_id,
            document_number =  document_number,
            gender_id       =  gender_id,
            phone           =  phone,
            emails          =  emails.toJson(),
            product_id      =  product_id,
            disease         =  disease,
            description     =  description,
            date            =  date.toJson(),
            start_time      =  start_time.toJson(),
            end_time        =  end_time.toJson(),
            token           = " ${Constants.TYPE_AUTH} ${preferences.token}"
        )
    }

    override suspend fun validateDate(date: String, startTime: String, endTime: String) {
        service.validateDate(date, startTime, endTime, " ${Constants.TYPE_AUTH} ${preferences.token}")
    }

    override suspend fun getMeetingsCalendar(
        year: String,
        month: String
    ): MeetingCalendar{

        val response =
            service.getMeetingsCalendar(year, month, " ${Constants.TYPE_AUTH} ${preferences.token}")

        val eventsList = response.calendar?.map {
            val nameArray = it.name?.split("-") ?: emptyList()
            MeetingEvent( packageName = nameArray[0].trim(),
                          issue = nameArray[1].trim(),
                          date = it.date?.toDateStringDate() ?: "",
                          endTime = it.endTime ?: "00:00",
                          link = it.linkMeet ?: "")
        }

        val pendingEventList = response.pending?.map{
            val nameArray = it.name?.split("-") ?: emptyList()
            PendingMeetingEvent( id = it.id ?: 0,
                                 packageName = nameArray[0].trim(),
                                 issue = nameArray[1].trim())
        }

        return MeetingCalendar(eventsList ?: emptyList(),
            pendingEventList ?: emptyList())

    }
}