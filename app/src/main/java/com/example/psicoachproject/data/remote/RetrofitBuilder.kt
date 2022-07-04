package com.example.psicoachproject.data.remote

import com.example.psicoachproject.common.utils.HeaderInterceptor
import com.example.psicoachproject.common.utils.UnsafeOkHttpClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Angelo on 5/23/2021
 */
object RetrofitBuilder {

    val BASE_URL = "https://psicouch-apiv2.herokuapp.com/"

    fun getConexionRetrofit(): Retrofit {
//        val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClientClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private val loggerInterceptor = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClientClient = OkHttpClient.Builder()
        .connectTimeout(120,TimeUnit.SECONDS)
        .writeTimeout(120,TimeUnit.SECONDS)
        .readTimeout(120,TimeUnit.SECONDS)
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(loggerInterceptor)
        .build()

    /*TODO
       Others retrofit's interceptors*/
    //https://www.geeksforgeeks.org/okhttp-interceptor-in-android/
}