package com.example.psicoachproject.domain.repository.auth

import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.data.remote.source.dto.DataResponse
import com.example.psicoachproject.data.remote.source.dto.UserResponse

/**
 * Created by Angelo on 5/23/2021
 */
interface AuthRepository {

    suspend fun signIn(email: String, password: String): Resource<DataResponse>
    suspend fun signUp(name: String, email: String, password: String): Resource<UserResponse>
    suspend fun recoveryPassword(email: String)
    //Colocar más funciones si va haber mas pasos para lo de recuperar

}