package com.example.psicoachproject.ui.modules.main.activities.viewmodel

import android.util.Log
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

    lateinit var email: String

    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.signIn(email, password)))
        }catch (t: Throwable){
            if(t is HttpException){
                val errorResponse = t.response()
                try {
                    val jsonError = JSONObject(errorResponse?.errorBody()?.string())
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

    fun signUp(name: String,
               email: String,
               password: String,
               secretQuestion: String,
               secretResponse: String,
               helpPhrase: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.signUp(name, email, password, secretQuestion, secretResponse, helpPhrase)))

        } catch (t: Throwable){
            if(t is HttpException){
                val response = t.response()

                try {
                    val jObjError = JSONObject(response?.errorBody()?.string())

                    val result = Gson().fromJson(jObjError.toString(), ErrorResponse::class.java)

                    if (response?.code() == 422){
                        emit(Resource.Failure(result.error.first().message))
                    }else {
                        emit(Resource.Failure(result.message))
                    }

                } catch (e: Exception){
                    emit(Resource.Failure("Ocurrió un error en servicio - ${e.message.toString()}"))
                }
            }else{
                emit(Resource.Failure("Ocurrió un error"))
            }
        }
    }

    fun verifyEmail(email: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.verifyEmail(email)))
        }catch (t: Throwable){
            if(t is HttpException){
                val response = t.response()

                try {
                    val jObjError = JSONObject(response?.errorBody()?.string())
                    val result = Gson().fromJson(jObjError.toString(), ErrorResponse::class.java)

                    if (response?.code() == 422){
                        emit(Resource.Failure(result.error.first().message))
                    }else {
                        emit(Resource.Failure(result.message))
                    }

                } catch (e: Exception){
                    emit(Resource.Failure("Ocurrió un error en servicio - ${e.message.toString()}"))
                }
            }else{
                emit(Resource.Failure("Ocurrió un error"))
            }
        }
    }

    fun verifyResponse(email: String, phrase: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.verifyResponse(email, phrase)))
        }catch (t: Throwable){
            if(t is HttpException){
                val response = t.response()

                try {
                    val jObjError = JSONObject(response?.errorBody()?.string())
                    val result = Gson().fromJson(jObjError.toString(), ErrorResponse::class.java)

                    if (response?.code() == 422){
                        emit(Resource.Failure(result.error.first().message))
                    }else {
                        emit(Resource.Failure(result.message))
                    }

                } catch (e: Exception){
                    emit(Resource.Failure("Ocurrió un error en servicio - ${e.message.toString()}"))
                }
            }else{
                emit(Resource.Failure("Ocurrió un error"))
            }
        }
    }

    fun changePassword(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {

            emit(Resource.Success(repository.changePassword(email, password)))
        }catch (t: Throwable){
            if(t is HttpException){
                val response = t.response()

                try {
                    val jObjError = JSONObject(response?.errorBody()?.string())
                    val result = Gson().fromJson(jObjError.toString(), ErrorResponse::class.java)

                    if (response?.code() == 422){
                        emit(Resource.Failure(result.error.first().message))
                    }else {
                        emit(Resource.Failure(result.message))
                    }

                } catch (e: Exception){
                    emit(Resource.Failure("Ocurrió un error en servicio - ${e.message.toString()}"))
                }
            }else{
                emit(Resource.Failure("Ocurrió un error"))
            }
        }
    }




}