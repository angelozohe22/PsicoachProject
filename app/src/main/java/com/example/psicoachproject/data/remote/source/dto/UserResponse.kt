package com.example.psicoachproject.data.remote.source.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Angelo on 5/23/2021
 */
data class UserResponse(
    @field:SerializedName("message") var message: String = ""
)

data class DataResponse(
    @field:SerializedName("type")   val type  : String,
    @field:SerializedName("token")  val token : String
)

data class ErrorResponse(
    @field:SerializedName("errors")   val error  : List<Error>,
    @field:SerializedName("message")  val message : String
)

data class Error(
    @field:SerializedName("rule")    val rule    : String,
    @field:SerializedName("field")   val field   : String,
    @field:SerializedName("message") val message : String,
    @field:SerializedName("args")    val args    : Args
)

data class Args(
    @field:SerializedName("minLength") val minLength : Int
)

data class SecretQuestion(
    @field:SerializedName("secret_question")    val question  : String,
    @field:SerializedName("help_phrase")        val phrase    : String,
    @field:SerializedName("message")            val message   : String
)