package com.example.psicoachproject.data.remote.source.auth

import android.util.Log
import android.widget.Toast
import com.example.psicoachproject.data.remote.RetrofitBuilder.getConexionRetrofit
import com.example.psicoachproject.data.remote.service.auth.AuthService
import com.example.psicoachproject.data.remote.source.dto.DataResponse
import com.example.psicoachproject.data.remote.source.dto.ErrorResponse
import com.example.psicoachproject.data.remote.source.dto.UserResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.*
import java.util.*


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

    override suspend fun signUp(name: String, email: String, password: String): UserResponse {
        return service.signUp(email, password, name)
    }
}