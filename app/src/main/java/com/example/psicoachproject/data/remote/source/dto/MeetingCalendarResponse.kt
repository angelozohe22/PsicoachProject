package com.example.psicoachproject.data.remote.source.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Angelo on 7/5/2021
 */
data class MeetingCalendarResponse(
    @field:SerializedName("calendar") val calendar  : List<MeetingResponse>?        = null,
    @field:SerializedName("pending")  val pending   : List<PendingMeetingResponse>? = null
)

data class MeetingResponse(
    @field:SerializedName("quote_id")       val idAppointment : Int?       = null,
    @field:SerializedName("name")           val name          : String?    = null,
    @field:SerializedName("pa_name")        val packageName   : String?    = null,
    @field:SerializedName("tem_name")       val issue         : String?    = null,
    @field:SerializedName("date")           val date          : String?    = null,
    @field:SerializedName("date_format")    val dateFormat    : String?    = null,
    @field:SerializedName("time_format")    val timeFormat    : String?    = null,
    @field:SerializedName("start_time")     val startTime     : String?    = null,
    @field:SerializedName("end_time")       val endTime       : String?    = null,
    @field:SerializedName("description")    val description   : String?    = null,
    @field:SerializedName("link_meet")      val link          : String?    = null,
    @field:SerializedName("comments")       val comments      : List<CommentResponse>?    = null
)

data class PendingMeetingResponse(
    @field:SerializedName("meeting_id")    val id           : Int?    = null,
    @field:SerializedName("name")          val name         : String?    = null,
    @field:SerializedName("description")   val description  : String?    = null
)

data class CommentResponse(
    @field:SerializedName("id")      val id   : Int?    = null,
    @field:SerializedName("comment") val comment : String? = null
)


