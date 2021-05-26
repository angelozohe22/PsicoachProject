package com.example.psicoachproject.ui.modules.main.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import com.example.psicoachproject.R
import com.example.psicoachproject.databinding.ActivitySignInBinding
import com.example.psicoachproject.databinding.DialogForgotPassBinding
import com.example.psicoachproject.common.utils.afterTextChanged
import com.example.psicoachproject.common.utils.customDialog
import com.example.psicoachproject.common.utils.isEmailValid
import com.example.psicoachproject.common.utils.isNullOrEmpty
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.data.remote.source.auth.AuthRemoteDataSourceImpl
import com.example.psicoachproject.domain.repository.auth.AuthRepositoryImpl
import com.example.psicoachproject.ui.modules.main.activities.viewmodel.AuthViewModel
import com.example.psicoachproject.ui.modules.main.activities.viewmodel.AuthViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignInActivity : AppCompatActivity() {

    private lateinit var binding            : ActivitySignInBinding

    private lateinit var lblForgotPassword  : AppCompatTextView
    private lateinit var btnBackSignIn      : AppCompatImageButton
    private lateinit var lblSignUp          : AppCompatTextView
    private lateinit var btnSignIn          : AppCompatButton
    private lateinit var cetEmailSignIn     : TextInputLayout
    private lateinit var etEmailSignIn      : TextInputEditText
    private lateinit var cetPasswordSignIn  : TextInputLayout
    private lateinit var etPasswordSignIn   : TextInputEditText

    private val viewModel by viewModels<AuthViewModel>{
        AuthViewModelFactory(
            AuthRepositoryImpl(
                AuthRemoteDataSourceImpl()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding
        lblForgotPassword   = binding.lblForgotPassword
        btnBackSignIn       = binding.btnBackSignIn
        lblSignUp           = binding.lblSignUp
        btnSignIn           = binding.btnSignIn
        cetEmailSignIn      = binding.cetEmailSignIn
        cetPasswordSignIn   = binding.cetPasswordSignIn
        etEmailSignIn       = binding.etEmailSignIn
        etPasswordSignIn    = binding.etPasswordSignIn

        btnSignIn.apply {
            isEnabled = false
            setBackgroundResource(R.drawable.btn_corner_disable)
        }

        lblSignUp.setOnClickListener { goToSignUp() }
        lblForgotPassword.setOnClickListener { showForgotPasswordDialog() }
        btnBackSignIn.setOnClickListener { onBackPressed(); finish() }

        inputsValidator()

    }

    private fun inputsValidator(){

        val validate = afterTextChanged {
            val email   = etEmailSignIn.text.toString().trim()
            val password= etPasswordSignIn.text.toString().trim()

            if (email.isNotEmpty()){
                cetEmailSignIn.error = when {
                    !isEmailValid(email) -> "Correo incorrecto"
                    else -> null
                }
            }

            if (password.isNotEmpty()){
                cetPasswordSignIn.error = when {
                    password.length <= 5 || password.length > 20 -> "La contraseña debe tener entre 5 a 20 caracteres"
                    else -> null
                }
            }

            btnSignIn.apply {
                isEnabled = !isNullOrEmpty(email)
                            && !isNullOrEmpty(password)
                            && password.length >= 5 || password.length < 20

                if (isEnabled) setBackgroundResource(R.drawable.btn_corner)
                else setBackgroundResource(R.drawable.btn_corner_disable)

                setOnClickListener {
                    signIn(email, password)
                }

            }
        }

        etEmailSignIn.addTextChangedListener(validate)
        etPasswordSignIn.addTextChangedListener(validate)

    }

    private fun signIn(email: String, password: String ){
        viewModel.signIn(email, password).observe(this, Observer {
            it?.let { result ->
                when (result) {
                    Resource.Loading -> {
                        println("Cargando...")
                    }
                    is Resource.Success -> {
                        Toast.makeText(this, result.data, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Failure -> {
                        Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun showForgotPasswordDialog() {
        binding.forgotCurtain.visibility = View.VISIBLE
        customDialog(this, R.layout.dialog_forgot_pass){ view, dialog ->
            val dialogBinding = DialogForgotPassBinding.bind(view)

            dialog.setOnDismissListener {
                binding.forgotCurtain.visibility = View.GONE
            }

        }
    }

    private fun goToSignUp(){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun clearInputs(){
        etPasswordSignIn.text?.clear()
        etEmailSignIn.text?.clear()
        cetEmailSignIn.clearFocus()
        cetPasswordSignIn.clearFocus()
    }
}