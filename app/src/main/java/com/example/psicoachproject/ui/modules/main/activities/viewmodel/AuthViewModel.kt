package com.example.psicoachproject.ui.modules.main.activities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.domain.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

/**
 * Created by Angelo on 5/23/2021
 */
class AuthViewModel(
    private val repository: AuthRepository
): ViewModel() {

    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.signIn(email, password)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun signUp(name: String, email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.signUp(name, email, password)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}