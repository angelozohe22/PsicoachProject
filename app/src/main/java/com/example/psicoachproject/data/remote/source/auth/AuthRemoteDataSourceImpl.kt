package com.example.psicoachproject.data.remote.source.auth

import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.data.remote.RetrofitBuilder.getRetrofit
import com.example.psicoachproject.data.remote.service.auth.AuthService
import com.example.psicoachproject.data.remote.source.dto.DataResponse
import com.example.psicoachproject.data.remote.source.dto.UserResponse

/**
 * Created by Angelo on 5/23/2021
 */
class AuthRemoteDataSourceImpl
    : AuthRemoteDataSource{

    private val retrofit = getRetrofit().create(AuthService::class.java)

    override suspend fun signIn(email: String, password: String): Resource<DataResponse> {
        return Resource.Success(retrofit.signIn(email, password))
    }

    override suspend fun signUp(name: String, email: String, password: String): Resource<UserResponse> {
        return Resource.Success(retrofit.signUp(email, password, name))
    }
}