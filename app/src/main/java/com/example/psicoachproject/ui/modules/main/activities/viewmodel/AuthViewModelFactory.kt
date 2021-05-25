package com.example.psicoachproject.ui.modules.main.activities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.psicoachproject.domain.repository.auth.AuthRepository

class AuthViewModelFactory(private val repository: AuthRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AuthRepository::class.java).newInstance(repository)
    }
}