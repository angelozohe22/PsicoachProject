package com.example.psicoachproject.data.remote.service.home

import com.google.gson.annotations.SerializedName

/**
 * Created by Angelo on 7/5/2021
 */
data class Meet(
    @SerializedName("name")             val name            : String,
    @SerializedName("surname")          val surname         : String,
    @SerializedName("age")              val age             : String,
    @SerializedName("document_id")      val document_id     : Int,
    @SerializedName("document_number")  val document_number : String,
    @SerializedName("product_id")       val product_id      : Int,
    @SerializedName("gender_id")        val gender_id       : Int,
    @SerializedName("description")      val description     : String,
    @SerializedName("disease")          val disease         : String,
    @SerializedName("date_app")             val date            : String,
    @SerializedName("start_time_app")       val start_time      : String,
    @SerializedName("end_time_app")         val end_time        : String,
    @SerializedName("phone")            val phone           : String,
    @SerializedName("emails_app")           val emails          : String,
    @SerializedName("is_app")           val is_app          : Boolean = true
)
