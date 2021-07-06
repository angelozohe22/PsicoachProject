package com.example.psicoachproject.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Angelo on 6/28/2021
 */

@Parcelize
data class Promotion(
    val name        : String,
    val price       : Double,
    val description : String,
    val color       : String,
    val benefitList : List<String>
): Parcelable
