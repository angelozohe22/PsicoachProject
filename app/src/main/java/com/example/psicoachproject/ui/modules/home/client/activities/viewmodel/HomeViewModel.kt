package com.example.psicoachproject.ui.modules.home.client.activities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.data.remote.source.dto.ErrorResponse
import com.example.psicoachproject.domain.model.MeetingTime
import com.example.psicoachproject.domain.repository.home.HomeRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception

/**
 * Created by Angelo on 7/3/2021
 */
class HomeViewModel(
        private val repository: HomeRepository
): ViewModel() {

    fun getMeetingCalendar(year: String, month: String) = liveData(Dispatchers.IO){
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.getMeetingsCalendar(year, month)))
        }catch (t: Throwable){
            println("Se cayo--->> ${t.message.toString()}")
            if(t is HttpException){
                val errorResponse = t.response()
                try {
                    val jsonError = JSONObject(errorResponse?.errorBody()?.string())
                    val result = Gson().fromJson(jsonError.toString(), ErrorResponse::class.java)
                    if (errorResponse?.code() == 422){
                        println("error1: ${result.error.first().message}")
                        emit(Resource.Failure(result.error.first().message))
                    }else {
                        println("error2: ${result.message}")
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

    fun registerMeeting(
            name            : String,
            surname         : String,
            age             : String,
            document_id     : Int,
            document_number : String,
            gender_id       : Int,
            phone           : String,
            emails          : List<String>,
            product_id      : Int,
            disease         : String,
            description     : String,
            date            : List<String>,
            start_time      : List<String>,
            end_time        : List<String>
    ) = liveData(Dispatchers.IO){
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.registerCita(
                    name, surname, age, document_id, document_number,
                    gender_id, phone, emails, product_id, disease, description,
                    date, start_time, end_time)))
        }catch (t: Throwable){
            println("Se cayo--->> ${t.message.toString()}")
            println("se cae --->>> $t")
            if(t is HttpException){
                val errorResponse = t.response()
                println("--->>> $t")
                println("----->> ${t.response()}")

                try {
                    val jsonError = JSONObject(errorResponse?.errorBody()?.string())

                    println("--->>> esta wea jsonError2: ${errorResponse?.errorBody()}")
                    println("--->>> esta wea jsonError: $jsonError")
                    println("--->>> esta wea erro body: ${errorResponse?.errorBody()?.string()}")
                    val result = Gson().fromJson(jsonError.toString(), ErrorResponse::class.java)
                    if (errorResponse?.code() == 422){
                        println("error1: ${result.error.first().message}")
                        emit(Resource.Failure(result.error.first().message))
                    }else {
                        println("error2: ${result.message}")
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

    fun validateCita(meetingTime: MeetingTime) = liveData(Dispatchers.IO){
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.validateDate(meetingTime)))
        }catch (t: Throwable){
            println("Se cayo--->> ${t.message.toString()}")
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

    fun getPendingList() = liveData(Dispatchers.IO){
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.getPendingList()))
        }catch (t: Throwable){
            println("Se cayo--->> ${t.message.toString()}")
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

    fun changeStateAppointment(
        id: String,
        status: String
    ) = liveData(Dispatchers.IO){
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.changeStateAppointment(id, status)))
        }catch (t: Throwable){
            println("Se cayo--->> ${t.message.toString()}")
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

    fun sendComment(id: Int, message: String) = liveData(Dispatchers.IO){
        emit(Resource.Loading)
        try {
            emit(Resource.Success(repository.sendComment(id, message)))
        }catch (t: Throwable){
            println("Se cayo--->> ${t.message.toString()}")
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

}