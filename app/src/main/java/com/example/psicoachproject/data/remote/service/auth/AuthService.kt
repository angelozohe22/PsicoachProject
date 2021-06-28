package com.example.psicoachproject.data.remote.service.auth

import com.example.psicoachproject.core.aplication.Constants
import com.example.psicoachproject.core.aplication.preferences
import com.example.psicoachproject.data.remote.source.dto.*
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

    @POST(Constants.SERVICE_ROUTE_VERIFY_EMAIL)
    @Headers("Accept: application/json")
    @FormUrlEncoded
    suspend fun verifyEmail(@Field("action") action      : String = "verify_mail",
                            @Field("email")  email       : String): SecretQuestion

    @POST(Constants.SERVICE_ROUTE_VERIFY_EMAIL)
    @Headers("Accept: application/json")
    @FormUrlEncoded
    suspend fun verifyResponse(@Field("action")           action   : String = "verify_secret",
                               @Field("email")            email    : String,
                               @Field("secret_response")  response : String): UserResponse

    @POST(Constants.SERVICE_ROUTE_VERIFY_EMAIL)
    @Headers("Accept: application/json")
    @FormUrlEncoded
    suspend fun changePassword(@Field("action") action      : String = "change_password",
                               @Field("email")  email       : String,
                               @Field("email")  password    : String): UserResponse


    @DELETE(Constants.SERVICE_ROUTE_LOGOUT)
    @Headers("Accept:application/json", "Content-type:application/json")
    suspend fun logout(@Header("Authorization")token: String): LogoutResponse

    @GET(Constants.SERVICE_ROUTE_PROFILE)
    @Headers("Accept:application/json", "Content-type:application/json")
    suspend fun refreshData(@Header("Authorization")token: String): ProfileResponse

}