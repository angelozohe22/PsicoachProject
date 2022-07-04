package com.example.psicoachproject.common.utils

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("content-type", "application/json")
            .addHeader("Accept:", "application/json")
            .build()
        return chain.proceed(request)
    }
}