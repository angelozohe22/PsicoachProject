package com.example.psicoachproject.data.remote.source.home

import com.example.psicoachproject.common.utils.*
import com.example.psicoachproject.core.Constants
import com.example.psicoachproject.core.aplication.preferences
import com.example.psicoachproject.data.remote.RetrofitBuilder
import com.example.psicoachproject.data.remote.service.home.HomeService
import com.example.psicoachproject.data.remote.source.dto.CommentResponse
import com.example.psicoachproject.data.remote.source.dto.PendingResponse
import com.example.psicoachproject.data.remote.source.dto.UserResponse
import com.example.psicoachproject.domain.model.Comment
import com.example.psicoachproject.domain.model.MeetingCalendar
import com.example.psicoachproject.domain.model.MeetingEvent
import com.example.psicoachproject.domain.model.PendingMeetingEvent
import java.io.IOException
import java.lang.Exception

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

        val appointment = hashMapOf(
            "name" to  name,
            "surname" to  surname,
            "age" to  age,
            "document_id" to  document_id.toString(),
            "document_number" to  document_number,
            "gender_id" to  gender_id.toString(),
            "phone" to  phone,
            "emails_app" to  emails.toJson(),
            "product_id" to  product_id.toString(),
            "disease" to  disease,
            "description" to  description,
            "date_app" to  date.toJson(),
            "start_time_app" to  start_time.toJson(),
            "end_time_app" to  end_time.toJson(),
            "is_app" to "true"
        )

        return service.registerCita(
            appointment,
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
            MeetingEvent(
                idAppointment = it.idAppointment ?: 0,
                packageName = it.packageName ?: "",
                issue = it.issue ?: "",
                date = it.date?.toDateStringDate() ?: "",
                dateFormat = it.dateFormat ?: "",
                startTime = it.startTime ?: "00:00",
                endTime = it.endTime ?: "00:00",
                description = it.description ?: "",
                link = it.link ?: "",
                comments = commentMapper(it.comments),
                state = Constants.STATE_OK)
        }

        val pendingEventList = response.pending?.map{
            val nameArray = it.name?.split("-") ?: emptyList()
            PendingMeetingEvent( id = it.id ?: 0,
                                 packageName = nameArray[0].trim(),
                                 issue = nameArray[1].trim(),
                                 state = Constants.STATE_PENDING)
        }

        return MeetingCalendar(eventsList ?: emptyList(),
            pendingEventList ?: emptyList())

    }

    override suspend fun getPendingList(): List<PendingResponse> {
        return service.getPendingList(
            token = " ${Constants.TYPE_AUTH} ${preferences.token}")
    }

    override suspend fun changeStateAppointment(id: String, status: String): String {
        val parameters = hashMapOf(
            "meeting_id" to  id,
            "status"    to  status
        )

        return service.changeStateAppointment(parameters, " ${Constants.TYPE_AUTH} ${preferences.token}").message
    }

    override suspend fun sendComment(id: Int, message: String): String {

        val parameters = hashMapOf(
            "quote_id" to  id.toString(),
            "action"   to  "action_create",
            "comment"  to  message
        )

        return service.sendComment(
            json  = parameters,
            token = " ${Constants.TYPE_AUTH} ${preferences.token}"
        ).message
    }

    private fun commentMapper(commentList: List<CommentResponse>?): List<Comment>{
        val commentMapper = mutableListOf<Comment>()
        commentList?.forEach { comment ->
            if (comment.id != null && comment.comment != null){
                commentMapper.add(Comment(id = comment.id, comment = comment.comment))
            }
        }
        return commentMapper
    }

}