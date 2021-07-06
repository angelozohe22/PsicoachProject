package com.example.psicoachproject.domain.model

/**
 * Created by Angelo on 7/5/2021
 */
data class MeetingEvent(
    val packageName : String,
    val issue       : String,
    val date        : String,
    val endTime     : String,
    val link        : String
)
