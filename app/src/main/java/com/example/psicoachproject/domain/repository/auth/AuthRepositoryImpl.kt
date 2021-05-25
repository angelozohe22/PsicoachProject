package com.example.psicoachproject.domain.repository.auth

import com.example.psicoachproject.common.utils.isNullOrEmpty
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.core.aplication.preferences
import com.example.psicoachproject.data.remote.source.auth.AuthRemoteDataSource
import com.example.psicoachproject.data.remote.source.dto.DataResponse
import com.example.psicoachproject.data.remote.source.dto.UserResponse
import com.example.psicoachproject.domain.repository.auth.AuthRepository

/**
 * Created by Angelo on 5/23/2021
 */
class AuthRepositoryImpl(
    private val remote: AuthRemoteDataSource
): AuthRepository {

    override suspend fun signIn(email: String, password: String): String {
        val result = remote.signIn(email, password)
        if (!isNullOrEmpty(result.type)){
            preferences.token = result.token
        }

        return "Logeo correctamente"
    }

    override suspend fun signUp(name: String, email: String, password: String): String {
        return remote.signUp(name, email, password).message
    }

    override suspend fun recoveryPassword(email: String) {
        TODO("Not yet implemented")
    }
}