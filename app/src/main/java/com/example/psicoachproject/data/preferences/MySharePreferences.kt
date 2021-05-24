package com.example.psicoachproject.data.preferences

import android.content.Context

class MySharePreferences(context: Context) {

    private val fileName = "psicoach-preferences"
    private val prefs = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    var token: String?
        get() = prefs.getString("token","")
        set(value) = prefs.edit().putString("token", value).apply()

    var username: String?
        get() = prefs.getString("username","")
        set(value) = prefs.edit().putString("username", value).apply()

    var email: String?
        get() = prefs.getString("email","")
        set(value) = prefs.edit().putString("email", value).apply()

    fun clear(){
        prefs.edit().remove("token").apply()
        prefs.edit().remove("username").apply()
        prefs.edit().remove("email").apply()
    }

}