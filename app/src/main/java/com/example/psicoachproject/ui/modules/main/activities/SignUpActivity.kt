package com.example.psicoachproject.ui.modules.main.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.psicoachproject.R
import com.example.psicoachproject.databinding.ActivitySignInBinding
import com.example.psicoachproject.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lblSignIn.setOnClickListener {
            println("Holaa estoy haciendo click")
        }
    }
}