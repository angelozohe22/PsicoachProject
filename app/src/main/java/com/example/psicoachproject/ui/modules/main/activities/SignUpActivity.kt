package com.example.psicoachproject.ui.modules.main.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.psicoachproject.R
import com.example.psicoachproject.databinding.ActivitySignUpBinding
import com.example.psicoachproject.common.utils.afterTextChanged
import com.example.psicoachproject.common.utils.isEmailValid
import com.example.psicoachproject.common.utils.isNullOrEmpty
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.data.remote.source.auth.AuthRemoteDataSourceImpl
import com.example.psicoachproject.domain.repository.auth.AuthRepositoryImpl
import com.example.psicoachproject.ui.modules.main.activities.viewmodel.AuthViewModel
import com.example.psicoachproject.ui.modules.main.activities.viewmodel.AuthViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding            : ActivitySignUpBinding
    private lateinit var cetUsernameSignUp  : TextInputLayout
    private lateinit var cetEmailSignUp     : TextInputLayout
    private lateinit var cetPasswordSignUp  : TextInputLayout
    private lateinit var cetSecretQuestion  : TextInputLayout
    private lateinit var cetSecretResponse  : TextInputLayout
    private lateinit var cetHelpPhrase      : TextInputLayout
    private lateinit var etUsernameSignUp   : TextInputEditText
    private lateinit var etEmailSignUp      : TextInputEditText
    private lateinit var etPasswordSignUp   : TextInputEditText
    private lateinit var etSecretQuestion   : TextInputEditText
    private lateinit var etSecretResponse   : TextInputEditText
    private lateinit var etHelpPhrase       : TextInputEditText
    private lateinit var btnSignUp          : AppCompatButton
    private lateinit var btnBackSignUp      : AppCompatImageButton
    private lateinit var progress           : ProgressBar
    private lateinit var lblSignIn          : AppCompatTextView

    private val viewModel by viewModels<AuthViewModel>{
        AuthViewModelFactory(
            AuthRepositoryImpl(
                AuthRemoteDataSourceImpl()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cetUsernameSignUp   = binding.cetUsernameSignUp
        cetEmailSignUp      = binding.cetEmailSignUp
        cetPasswordSignUp   = binding.cetPasswordSignUp
        cetSecretQuestion   = binding.cetSecretQuestion
        cetSecretResponse   = binding.cetSecretResponse
        cetHelpPhrase       = binding.cetHelpPhrase

        etUsernameSignUp    = binding.etUsernameSignUp
        etEmailSignUp       = binding.etEmailSignUp
        etPasswordSignUp    = binding.etPasswordSignUp
        etSecretQuestion    = binding.etSecretQuestion
        etSecretResponse    = binding.etSecretResponse
        etHelpPhrase        = binding.etHelpPhrase

        btnSignUp           = binding.btnSignUp
        btnBackSignUp       = binding.btnBackSignUp
        progress            = binding.progressSignUp
        lblSignIn           = binding.lblSignIn

        inputsValidator()

        //Click listeners
        lblSignIn.setOnClickListener { goToSignIn() }
        btnBackSignUp.setOnClickListener {
            onBackPressed()
            finish()
        }

    }

    private fun inputsValidator(){

        validateButton(false)
        val validate = afterTextChanged {
            val name        = etUsernameSignUp.text.toString().trim()
            val email       = etEmailSignUp.text.toString().trim()
            val password    = etPasswordSignUp.text.toString().trim()
            val question    = etSecretQuestion.text.toString().trim()
            val answer      = etSecretResponse.text.toString().trim()
            val helpPhrase  = etHelpPhrase.text.toString().trim()

            cetEmailSignUp.error = when {
                !isEmailValid(email) && !isNullOrEmpty(email) -> "Correo incorrecto"
                else -> null
            }

            cetPasswordSignUp.error = when {
                password.length !in 5..20 && !isNullOrEmpty(password) -> "La contraseña debe tener entre 5 a 20 caracteres"
                else -> null
            }

            cetSecretQuestion.error = when {
                isNullOrEmpty(question) -> "Ingresa una pregunta"
                else -> null
            }

            cetSecretResponse.error = when {
                isNullOrEmpty(answer) -> "Ingresa una respuesta"
                else -> null
            }

            cetHelpPhrase.error = when {
                isNullOrEmpty(helpPhrase) -> "Ingresa una frase de ayuda"
                else -> null
            }

            validateButton(!isNullOrEmpty(email)
                    && isEmailValid(email)
                    && !isNullOrEmpty(password)
                    && password.length in 5..20
                    && !isNullOrEmpty(question)
                    && !isNullOrEmpty(answer)
                    && !isNullOrEmpty(helpPhrase))

            btnSignUp.setOnClickListener {
                signUp(name, email, password, question, answer, helpPhrase)
            }

        }

        etEmailSignUp.addTextChangedListener(validate)
        etPasswordSignUp.addTextChangedListener(validate)
        etSecretQuestion.addTextChangedListener(validate)
        etSecretResponse.addTextChangedListener(validate)
        etHelpPhrase.addTextChangedListener(validate)

    }

    private fun signUp(name: String,
                       email: String,
                       password: String,
                       secretQuestion: String,
                       secretResponse: String,
                       helpPhrase: String ){
        viewModel.signUp(name, email, password, secretQuestion, secretResponse, helpPhrase).observe(this, Observer {
            it?.let { result ->
                when (result) {
                    Resource.Loading -> { showProgress() }
                    is Resource.Success -> {
                        hideProgress()
                        validateButton(false)
                        Toast.makeText(this, result.data, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Failure -> {
                        hideProgress()
                        validateButton(false)
                        Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun goToSignIn(){
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showProgress(){
        progress.visibility = View.VISIBLE
        btnSignUp.visibility = View.INVISIBLE
    }

    private fun hideProgress(){
        progress.visibility = View.INVISIBLE
        btnSignUp.visibility = View.VISIBLE
    }

    private fun validateButton(validator: Boolean){

        btnSignUp.apply {
            isEnabled = validator
            if (isEnabled) setBackgroundResource(R.drawable.btn_corner)
            else setBackgroundResource(R.drawable.btn_corner_disable)
        }

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