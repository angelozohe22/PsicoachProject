package com.example.psicoachproject.domain.repository.auth

import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.data.remote.source.auth.AuthRemoteDataSource
import com.example.psicoachproject.data.remote.source.dto.DataResponse
import com.example.psicoachproject.data.remote.source.dto.UserResponse
import com.example.psicoachproject.domain.repository.auth.AuthRepository

/**
 * Created by Angelo on 5/23/2021
 */
class AuthRepositoryImpl(
    val remote: AuthRemoteDataSource
): AuthRepository {

    override suspend fun signIn(email: String, password: String): Resource<DataResponse> {
        return remote.signIn(email, password)
    }

    override suspend fun signUp(name: String, email: String, password: String): Resource<UserResponse> {
        return remote.signUp(name, email, password)
    }

    override suspend fun recoveryPassword(email: String) {
        TODO("Not yet implemented")
    }
}