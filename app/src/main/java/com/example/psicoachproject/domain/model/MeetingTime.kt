package com.example.psicoachproject.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Angelo on 7/3/2021
 */
@Parcelize
data class MeetingTime(
        val date      : String,
        val startDate : String,
        val endDate   : String
): Parcelable
