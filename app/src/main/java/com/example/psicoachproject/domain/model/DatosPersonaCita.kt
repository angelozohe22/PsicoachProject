package com.example.psicoachproject.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Angelo on 7/2/2021
 */
@Parcelize
data class DatosPersonaCita(
        val nombre      : String,
        val apellidos   : String,
        val edad        : String,
        val documentId  : Int,
        val document    : String,
        val genderId    : Int,
        val phone       : String,
        val email       : List<String>,
        val meeting     : Meeting
): Parcelable

