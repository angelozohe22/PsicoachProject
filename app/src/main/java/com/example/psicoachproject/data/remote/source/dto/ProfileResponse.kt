package com.example.psicoachproject.data.remote.source.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Angelo on 6/27/2021
 */
data class ProfileResponse(
    @field:SerializedName("id")              val id             : Int?    = null,
    @field:SerializedName("role_id")         val roleId         : Int?    = null,
    @field:SerializedName("email")           val email          : String? = null,
    @field:SerializedName("name")            val name           : String? = null,
    @field:SerializedName("surname")         val surname        : String? = null,
    @field:SerializedName("age")             val age            : String? = null,
    @field:SerializedName("phone")           val phone          : String? = null,
    @field:SerializedName("document_id")     val documentId     : Int?    = null,
    @field:SerializedName("gender_id")       val genderId       : Int?    = null,
    @field:SerializedName("document_number") val documentNumber : String? = null,
    @field:SerializedName("phrase")          val phrase         : String? = null,
    @field:SerializedName("combos")          val combos         : Combo?  = null,
    @field:SerializedName("schedule")        val schedule       : Day?    = null
)

data class Combo(
    @field:SerializedName("genders")       val combosList     : List<DataInformation>? = null,
    @field:SerializedName("document_type") val documentList   : List<DataInformation>? = null,
    @field:SerializedName("diseases_type") val diseasesList   : List<DataInformation>? = null
)

data class DataInformation(
    @field:SerializedName("id")   val id   : String? = null,
    @field:SerializedName("name") val name : String? = null
)

data class Day(
    @field:SerializedName("Monday")    val monday    : List<String>? = null,
    @field:SerializedName("Tuesday")   val tuesday   : List<String>? = null,
    @field:SerializedName("Wednesday") val wednesday : List<String>? = null,
    @field:SerializedName("Thursday")  val thursday  : List<String>? = null,
    @field:SerializedName("Friday")    val friday    : List<String>? = null,
    @field:SerializedName("Saturday")  val saturday  : List<String>? = null,
)