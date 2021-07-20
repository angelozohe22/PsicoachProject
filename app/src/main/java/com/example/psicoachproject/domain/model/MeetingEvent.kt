package com.example.psicoachproject.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Angelo on 7/5/2021
 */
@Parcelize
data class MeetingEvent(
    val idAppointment   : Int,
    val packageName     : String,
    val issue           : String,
    val date            : String,
    val dateFormat      : String,
    val startTime       : String,
    val endTime         : String,
    val description     : String,
    val link            : String,
    val comments        : List<Comment>,
    val state           : String
): Parcelable

@Parcelize
data class Comment (
    val id: Int,
    val comment: String
): Parcelable
