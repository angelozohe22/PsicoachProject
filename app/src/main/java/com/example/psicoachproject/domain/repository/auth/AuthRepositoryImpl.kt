package com.example.psicoachproject.domain.repository.auth

import com.example.psicoachproject.common.utils.isNullOrEmpty
import com.example.psicoachproject.core.aplication.preferences
import com.example.psicoachproject.data.remote.source.auth.AuthRemoteDataSource
import com.example.psicoachproject.data.remote.source.dto.Day
import com.example.psicoachproject.data.remote.source.dto.SecretQuestion
import com.google.gson.Gson

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

    override suspend fun logOut(): String {
        return remote.logOut().message
    }

    override suspend fun signUp(name: String,
                                email: String,
                                password: String,
                                secretQuestion: String,
                                secretResponse: String,
                                helpPhrase: String): String {
        return remote.signUp(name, email, password, secretQuestion, secretResponse, helpPhrase).message
    }

    override suspend fun verifyEmail(email: String): SecretQuestion {
        return remote.verifyEmail(email)
    }

    override suspend fun verifyResponse(email: String, phrase: String): String {
        return remote.verifySecret(email, phrase).message
    }

    override suspend fun changePassword(email: String, password: String): String {
        return remote.changePassword(email, password).message
    }

    override suspend fun refreshData(): String {
        val profile = remote.refreshData()
        preferences.apply {
            ide             = profile.id ?: 0
            roleId          = profile.roleId ?: 0
            email           = profile.email ?: ""
            name            = profile.name ?: ""
            surname         = profile.surname ?: ""
            age             = profile.age ?: ""
            phone           = profile.phone ?: ""
            documentId      = profile.documentId ?: 0
            genderId        = profile.genderId ?: 0
            documentNumber  = profile.documentNumber ?: ""
            phrase          = profile.phrase ?: ""
            combos          = Gson().toJson(profile.combos ?: "")   ?: ""
            schedule        = Gson().toJson(profile.schedule ?: "") ?: ""
        }
        return ""
    }
}