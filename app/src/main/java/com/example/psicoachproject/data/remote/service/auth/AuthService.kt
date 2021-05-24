package com.example.psicoachproject.data.remote.service.auth

import com.example.psicoachproject.core.aplication.Constants
import com.example.psicoachproject.data.remote.source.dto.DataResponse
import com.example.psicoachproject.data.remote.source.dto.UserResponse
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Angelo on 5/23/2021
 */
interface AuthService {

    @POST(Constants.SERVICE_ROUTE_SIGN_IN + "login")
    suspend fun signIn (@Query("email")     email: String,
                        @Query("password")  password: String): DataResponse

    @POST(Constants.SERVICE_ROUTE_SIGN_UP)
    suspend fun signUp (@Query("email")     email    : String,
                        @Query("password")  password : String,
                        @Query("name")      name     : String): UserResponse
}