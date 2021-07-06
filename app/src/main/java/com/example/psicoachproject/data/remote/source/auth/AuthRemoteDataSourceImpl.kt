package com.example.psicoachproject.data.remote.source.auth

import com.example.psicoachproject.common.utils.isNullOrEmpty
import com.example.psicoachproject.core.Constants.TYPE_AUTH
import com.example.psicoachproject.core.aplication.preferences
import com.example.psicoachproject.data.remote.RetrofitBuilder.getConexionRetrofit
import com.example.psicoachproject.data.remote.service.auth.AuthService
import com.example.psicoachproject.data.remote.source.dto.*


/**
 * Created by Angelo on 5/23/2021
 */
class AuthRemoteDataSourceImpl
    : AuthRemoteDataSource{

    private val retrofit = getConexionRetrofit()
    private val service = retrofit.create(AuthService::class.java)

    override suspend fun signIn(email: String, password: String): DataResponse {
        return service.signIn(email, password)
    }

    override suspend fun signUp(name: String,
                                email: String,
                                password: String,
                                secretQuestion: String,
                                secretResponse: String,
                                helpPhrase: String): UserResponse {

        return if (!isNullOrEmpty(name)) service.signUp(email, password, name, secretQuestion, secretResponse, helpPhrase)
        else service.signUpWithoutName(email, password, secretQuestion, secretResponse, helpPhrase)
    }

    override suspend fun verifyEmail(email: String): SecretQuestion {
        return service.verifyEmail(email = email)
    }

    override suspend fun verifySecret(email: String, phrase: String): UserResponse {
        return service.verifyResponse(email = email, response = phrase)
    }

    override suspend fun changePassword(email: String, password: String): UserResponse {
        return service.changePassword(email = email, password = password)
    }

    override suspend fun logOut(): LogoutResponse {
        return service.logout(" $TYPE_AUTH ${preferences.token ?: ""}")
    }

    override suspend fun refreshData(): ProfileResponse {
        return service.refreshData(" $TYPE_AUTH ${preferences.token ?: ""}")
    }
}