package com.example.psicoachproject.core

/**
 * Created by Angelo on 5/23/2021
 */
object Constants {

    val ROOT_URL: String = ""
    const val TYPE_AUTH = "Bearer"

    const val SERVICE_ROUTE_SIGN_IN = "auth/"
    const val SERVICE_ROUTE_SIGN_UP = "register"
    const val SERVICE_ROUTE_VERIFY_EMAIL = "auth/recovery"
    const val SERVICE_ROUTE_LOGOUT  = "auth/logout"
    const val SERVICE_ROUTE_PROFILE = "auth/profile"
    const val SERVICE_ROUTE_REGISTER = "meeting/register"
    const val SERVICE_ROUTE_MEETINGS_CALENDAR = "meeting/calendar"
    const val SERVICE_ROUTE_VALIDATE_DATE = "crossing"


    const val VERIFY_EMAIL = 1
    const val VERIFY_RESPONSE = 2
    const val CHANGE_PASSWORD = 3
}