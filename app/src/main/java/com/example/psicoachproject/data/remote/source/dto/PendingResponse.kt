package com.example.psicoachproject.data.remote.source.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Angelo on 7/19/2021
 */
data class PendingResponse(
    @field:SerializedName("meeting_id") val id          : Int?       = null,
    @field:SerializedName("pa_name")    val packageName : String?    = null,
    @field:SerializedName("tem_name")   val issue       : String?    = null
)
