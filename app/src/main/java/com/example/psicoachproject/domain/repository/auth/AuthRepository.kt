package com.example.psicoachproject.domain.repository.auth

import com.example.psicoachproject.data.remote.source.dto.SecretQuestion

/**
 * Created by Angelo on 5/23/2021
 */
interface AuthRepository {

    suspend fun signIn(email: String, password: String): String
    suspend fun signUp(name: String, email: String, password: String, secretQuestion: String, secretResponse: String, helpPhrase: String): String
    suspend fun verifyEmail(email: String): SecretQuestion
    suspend fun verifyResponse(email: String, phrase: String): String
    suspend fun changePassword(email: String, password: String): String
    suspend fun refreshData(): String
    suspend fun logOut(): String
}