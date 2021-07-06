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
    @field:SerializedName("name")       val name    : String?    = null,
    @field:SerializedName("date")       val date    : String?    = null,
    @field:SerializedName("end_time")   val endTime : String?    = null,
    @field:SerializedName("link_meet")  val linkMeet: String?    = null
)

data class PendingMeetingResponse(
    @field:SerializedName("meeting_id")    val id           : Int?    = null,
    @field:SerializedName("name")          val name         : String?    = null,
    @field:SerializedName("description")   val description  : String?    = null
)


