package com.example.psicoachproject.data.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Angelo on 5/23/2021
 */
object RetrofitBuilder {

    private const val BASE_URL = "https://psicouch-apiv2.herokuapp.com/"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
    }
}