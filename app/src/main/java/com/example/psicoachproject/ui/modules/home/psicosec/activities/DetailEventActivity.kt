package com.example.psicoachproject.ui.modules.home.psicosec.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.psicoachproject.R
import com.example.psicoachproject.databinding.ActivityDetailEventBinding

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}