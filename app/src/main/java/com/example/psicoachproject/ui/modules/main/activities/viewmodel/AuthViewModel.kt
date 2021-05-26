package com.example.psicoachproject.ui.modules.main.activities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.data.remote.source.dto.ErrorResponse
import com.example.psicoachproject.domain.repository.auth.AuthRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import retrofit2.HttpException
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
        }catch (t: Throwable){
            if(t is HttpException){
                try {
                    val errorResponse = t.response()
                    val jsonError = JSONObject(errorResponse?.body().toString())
                    val result = Gson().fromJson(jsonError.toString(), ErrorResponse::class.java)
                    emit(Resource.Failure(result.error.first().message))
                }catch (e: Exception){
                    emit(Resource.Failure("Ocurrió un error en servicio - ${e.message.toString()}"))
                }
            }else{
                emit(Resource.Failure("Ocurrió un error"))
            }
        }
    }

    fun signUp(name: String, email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.signUp(name, email, password)))
        }catch (t: Throwable){
            println("Error sign up -> ${t.message.toString()}")
            if(t is HttpException){
                try {
                    val errorResponse = t.response()
                    val jsonError = JSONObject(errorResponse?.body().toString())
                    val result = Gson().fromJson(jsonError.toString(), ErrorResponse::class.java)
                    if (errorResponse?.code() == 422){
                        emit(Resource.Failure(result.error.first().message))
                    }else {
                        emit(Resource.Failure(result.message))
                    }
                }catch (e: Exception){
                    emit(Resource.Failure("Ocurrió un error en servicio - ${e.message.toString()}"))
                }
            }else{
                emit(Resource.Failure("Ocurrió un error"))
            }
        }
    }

}