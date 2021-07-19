package com.example.psicoachproject.domain.model

/**
 * Created by Angelo on 7/5/2021
 */
data class MeetingEvent(
    val idAppointment   : Int,
    val packageName     : String,
    val issue           : String,
    val date            : String,
    val startTime       : String,
    val endTime         : String,
    val description     : String,
    val link            : String,
    val state           : String
)
