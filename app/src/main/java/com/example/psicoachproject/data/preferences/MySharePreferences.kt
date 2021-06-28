package com.example.psicoachproject.data.preferences

import android.content.Context

class MySharePreferences(context: Context) {

    private val fileName = "psicoach-preferences"
    private val prefs = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    var token: String
        get() = prefs.getString("token","") ?: ""
        set(value) = prefs.edit().putString("token", value).apply()

    var ide: Int
        get() = prefs.getInt("ide",0)
        set(value) = prefs.edit().putInt("ide", value).apply()

    var roleId: Int
        get() = prefs.getInt("role_id",0)
        set(value) = prefs.edit().putInt("role_id", value).apply()

    var email: String
        get() = prefs.getString("email","") ?: ""
        set(value) = prefs.edit().putString("email", value).apply()

    var name: String
        get() = prefs.getString("name","") ?: ""
        set(value) = prefs.edit().putString("name", value).apply()

    var surname: String
        get() = prefs.getString("surname","") ?: ""
        set(value) = prefs.edit().putString("surname", value).apply()

    var age: String
        get() = prefs.getString("age","") ?: ""
        set(value) = prefs.edit().putString("age", value).apply()

    var phone: String
        get() = prefs.getString("phone","") ?: ""
        set(value) = prefs.edit().putString("phone", value).apply()

    var documentId: Int
        get() = prefs.getInt("document_id",0)
        set(value) = prefs.edit().putInt("document_id", value).apply()

    var genderId: Int
        get() = prefs.getInt("gender_id",0)
        set(value) = prefs.edit().putInt("gender_id", value).apply()

    var documentNumber: String
        get() = prefs.getString("document_number","") ?: ""
        set(value) = prefs.edit().putString("document_number", value).apply()

    var phrase: String
        get() = prefs.getString("phrase","") ?: ""
        set(value) = prefs.edit().putString("phrase", value).apply()

    var combos: String
        get() = prefs.getString("combos","") ?: ""
        set(value) = prefs.edit().putString("combos", value).apply()

    var schedule: String
        get() = prefs.getString("schedule","") ?: ""
        set(value) = prefs.edit().putString("schedule", value).apply()

    fun clear(){
        prefs.edit().remove("token").apply()
        prefs.edit().remove("ide").apply()
        prefs.edit().remove("role_id").apply()
        prefs.edit().remove("email").apply()
        prefs.edit().remove("name").apply()
        prefs.edit().remove("surname").apply()
        prefs.edit().remove("age").apply()
        prefs.edit().remove("phone").apply()
        prefs.edit().remove("document_id").apply()
        prefs.edit().remove("gender_id").apply()
        prefs.edit().remove("document_number").apply()
        prefs.edit().remove("phrase").apply()
        prefs.edit().remove("combos").apply()
        prefs.edit().remove("schedule").apply()
    }

}