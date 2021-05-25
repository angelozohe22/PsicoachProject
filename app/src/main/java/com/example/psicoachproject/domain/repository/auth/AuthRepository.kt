package com.example.psicoachproject.domain.repository.auth

import com.example.psicoachproject.core.Resource

/**
 * Created by Angelo on 5/23/2021
 */
interface AuthRepository {

    suspend fun signIn(email: String, password: String): String
    suspend fun signUp(name: String, email: String, password: String): String
    suspend fun recoveryPassword(email: String)
    //Colocar más funciones si va haber mas pasos para lo de recuperar

}