package com.example.psicoachproject.data.remote.service.auth

import com.example.psicoachproject.core.aplication.Constants
import com.example.psicoachproject.data.remote.source.dto.DataResponse
import com.example.psicoachproject.data.remote.source.dto.UserResponse
import retrofit2.http.*

/**
 * Created by Angelo on 5/23/2021
 */
interface AuthService {

    @POST(Constants.SERVICE_ROUTE_SIGN_IN + "login")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    suspend fun signIn (@Field("email")        email: String,
                        @Field("password")     password: String): DataResponse

    @POST(Constants.SERVICE_ROUTE_SIGN_UP)
    @Headers("Accept: application/json")
    @FormUrlEncoded
    suspend fun signUp (@Field("email")           email          : String,
                        @Field("password")        password       : String,
                        @Field("name")            name           : String,
                        @Field("secret_question") secretQuestion : String,
                        @Field("secret_response") secretResponse : String,
                        @Field("help_phrase")     helpPhrase     : String): UserResponse

    @POST(Constants.SERVICE_ROUTE_SIGN_UP)
    @Headers("Accept: application/json")
    @FormUrlEncoded
    suspend fun signUpWithoutName (@Field("email")           email          : String,
                                   @Field("password")        password       : String,
                                   @Field("secret_question") secretQuestion : String,
                                   @Field("secret_response") secretResponse : String,
                                   @Field("help_phrase")     helpPhrase     : String): UserResponse
}