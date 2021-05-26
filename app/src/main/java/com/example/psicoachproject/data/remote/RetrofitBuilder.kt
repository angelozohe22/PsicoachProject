package com.example.psicoachproject.data.remote

import com.example.psicoachproject.common.UnsafeOkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Angelo on 5/23/2021
 */
object RetrofitBuilder {
    val BASE_URL = "https://psicouch-apiv2.herokuapp.com/"

    fun getConexionRetrofit(): Retrofit {
        val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
        return Retrofit.Builder()
            .client(okHttpClient.build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}