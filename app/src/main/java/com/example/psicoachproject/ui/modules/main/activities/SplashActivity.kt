package com.example.psicoachproject.ui.modules.main.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.psicoachproject.common.utils.isNullOrEmpty
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.core.aplication.preferences
import com.example.psicoachproject.data.remote.source.auth.AuthRemoteDataSourceImpl
import com.example.psicoachproject.domain.repository.auth.AuthRepositoryImpl
import com.example.psicoachproject.ui.modules.home.HomeActivity
import com.example.psicoachproject.ui.modules.main.activities.viewmodel.AuthViewModel
import com.example.psicoachproject.ui.modules.main.activities.viewmodel.AuthViewModelFactory

/**
 * Created by Angelo on 6/27/2021
 */
class SplashActivity: AppCompatActivity() {

    private val viewModel by viewModels<AuthViewModel>{
        AuthViewModelFactory(
            AuthRepositoryImpl(
                AuthRemoteDataSourceImpl()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goTo()
    }

    private fun goTo(){
        if(isNullOrEmpty(preferences.token)){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            viewModel.refreshData().observe(this, Observer {
                it?.let { result ->
                    when(result){
                        is Resource.Loading -> {
                            println("Cargando...")
                        }
                        is Resource.Success -> {
                            println("Hecho...")
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        is Resource.Failure -> {
                            println("Falla...")
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            preferences.clear()
                            finish()
                        }
                    }
                }
            })
        }
    }

}