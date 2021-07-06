package com.example.psicoachproject.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Angelo on 7/2/2021
 */
@Parcelize
data class Meeting(
        val productId   : Int,
        val disease     : String,
        val description : String,
        val date        : List<String>,
        val startDate   : List<String>,
        val endDate     : List<String>
): Parcelable
