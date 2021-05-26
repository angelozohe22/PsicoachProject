package com.example.psicoachproject.core.aplication

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.psicoachproject.data.preferences.MySharePreferences

val preferences     : MySharePreferences by lazy { MyApp.prefs!! }

class MyApp: Application() {

    companion object{
        var prefs: MySharePreferences? = null
    }

    override fun onCreate() {
        super.onCreate()
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        prefs = MySharePreferences(applicationContext)
    }
}