package com.example.psicoachproject.data.remote.source.auth

import com.example.psicoachproject.data.remote.source.dto.DataResponse
import com.example.psicoachproject.data.remote.source.dto.SecretQuestion
import com.example.psicoachproject.data.remote.source.dto.UserResponse

/**
 * Created by Angelo on 5/23/2021
 */
interface AuthRemoteDataSource {

    suspend fun signIn(email: String, password: String): DataResponse
    suspend fun signUp(name: String, email: String, password: String, secretQuestion: String, secretResponse: String, helpPhrase: String): UserResponse
    suspend fun verifyEmail(email: String): SecretQuestion
    suspend fun verifySecret(email: String, response: String): UserResponse
    suspend fun changePassword(email: String, password: String): UserResponse
}