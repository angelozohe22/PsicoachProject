package com.example.psicoachproject.ui.modules.client.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.psicoachproject.domain.repository.home.HomeRepository

/**
 * Created by Angelo on 7/4/2021
 */
class PSClientViewModelFactory(private val repository: HomeRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeRepository::class.java).newInstance(repository)
    }
}