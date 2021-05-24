package com.example.psicoachproject.ui.modules.main.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.addTextChangedListener
import com.example.psicoachproject.R
import com.example.psicoachproject.databinding.ActivitySignUpBinding
import com.example.psicoachproject.common.utils.afterTextChanged
import com.example.psicoachproject.common.utils.isEmailValid
import com.example.psicoachproject.common.utils.isNullOrEmpty
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding            : ActivitySignUpBinding
    private lateinit var cetUsernameSignUp  : TextInputLayout
    private lateinit var etUsernameSignUp   : TextInputEditText
    private lateinit var cetEmailSignUp     : TextInputLayout
    private lateinit var etEmailSignUp      : TextInputEditText
    private lateinit var cetPasswordSignUp  : TextInputLayout
    private lateinit var etPasswordSignUp   : TextInputEditText
    private lateinit var btnSignUp          : AppCompatButton
    private lateinit var lblSignIn          : AppCompatTextView
    private lateinit var btnBackSignUp      : AppCompatImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cetUsernameSignUp   = binding.cetUsernameSignUp
        etUsernameSignUp    = binding.etUsernameSignUp
        cetEmailSignUp      = binding.cetEmailSignUp
        etEmailSignUp       = binding.etEmailSignUp
        cetPasswordSignUp   = binding.cetPasswordSignUp
        etPasswordSignUp    = binding.etPasswordSignUp
        btnSignUp           = binding.btnSignUp
        lblSignIn           = binding.lblSignIn
        btnBackSignUp       = binding.btnBackSignUp

        inputsValidator()

        btnSignUp.apply {
            isEnabled = false
            setBackgroundResource(R.drawable.btn_corner_disable)
        }

        //Click listeners
        lblSignIn.setOnClickListener { goToSignIn() }
        btnBackSignUp.setOnClickListener {
            onBackPressed()
            finish()
        }

    }

    private fun inputsValidator(){

        val validate = afterTextChanged {
            val name    = etUsernameSignUp.text.toString().trim()
            val email   = etEmailSignUp.text.toString().trim()
            val password= etPasswordSignUp.text.toString().trim()

            cetEmailSignUp.error = when {
                !isEmailValid(email) && !isNullOrEmpty(email) -> "Correo incorrecto"
                else -> null
            }

            cetPasswordSignUp.error = when {
                password.length !in 5..20 && !isNullOrEmpty(password) -> "La contraseña debe tener entre 5 a 20 caracteres"
                else -> null
            }

            btnSignUp.apply {
                isEnabled = !isNullOrEmpty(email)
                            && !isNullOrEmpty(password)
                            && password.length !in 5..20

                if (isEnabled) setBackgroundResource(R.drawable.btn_corner)
                else setBackgroundResource(R.drawable.btn_corner_disable)

                setOnClickListener {
                    signUp(name, email, password)
                }
            }
        }

        etEmailSignUp.addTextChangedListener(validate)
        etPasswordSignUp.addTextChangedListener(validate)

    }

    private fun signUp(name: String, email: String, password: String ){
        //Usar viewmodel
    }

    private fun goToSignIn(){
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun clearInputs(){
        etUsernameSignUp.text?.clear()
        etPasswordSignUp.text?.clear()
        etEmailSignUp.text?.clear()
        cetUsernameSignUp.clearFocus()
        cetEmailSignUp.clearFocus()
        cetPasswordSignUp.clearFocus()
    }

}