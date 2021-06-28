package com.example.psicoachproject.ui.modules.main.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import com.example.psicoachproject.R
import com.example.psicoachproject.common.utils.afterTextChanged
import com.example.psicoachproject.common.utils.customDialog
import com.example.psicoachproject.common.utils.isEmailValid
import com.example.psicoachproject.common.utils.isNullOrEmpty
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.core.aplication.Constants.CHANGE_PASSWORD
import com.example.psicoachproject.core.aplication.Constants.VERIFY_EMAIL
import com.example.psicoachproject.core.aplication.Constants.VERIFY_RESPONSE
import com.example.psicoachproject.data.remote.source.auth.AuthRemoteDataSourceImpl
import com.example.psicoachproject.databinding.ActivitySignInBinding
import com.example.psicoachproject.databinding.DialogForgotPassBinding
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
    private lateinit var cetPasswordSignIn  : TextInputLayout
    private lateinit var etEmailSignIn      : TextInputEditText
    private lateinit var etPasswordSignIn   : TextInputEditText
    private lateinit var progress           : ProgressBar

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
        progress            = binding.progressSignIn

        inputsValidator()
        lblSignUp.setOnClickListener { goToSignUp() }
        lblForgotPassword.setOnClickListener { showForgotPasswordDialog() }
        btnBackSignIn.setOnClickListener {
            onBackPressed()
            finish()
        }

    }

    private fun inputsValidator(){

        validateButton(false)
        val validate = afterTextChanged {
            val email   = etEmailSignIn.text.toString().trim()
            val password= etPasswordSignIn.text.toString().trim()

            cetEmailSignIn.error = when {
                !isEmailValid(email) && !isNullOrEmpty(email) -> "Correo incorrecto"
                else -> null
            }

            if (password.isNotEmpty()){
                cetPasswordSignIn.error = when {
                    password.length <= 5 || password.length > 20 -> "La contraseña debe tener entre 5 a 20 caracteres"
                    else -> null
                }
            }

            validateButton(
                !isNullOrEmpty(email)
                        && !isNullOrEmpty(password)
                        && password.length >= 5 || password.length < 20
            )

            btnSignIn.setOnClickListener { signIn(email, password) }

        }

        etEmailSignIn.addTextChangedListener(validate)
        etPasswordSignIn.addTextChangedListener(validate)

    }

    private fun signIn(email: String, password: String){
        viewModel.signIn(email, password).observe(this, Observer {
            it?.let { result ->
                when (result) {
                    Resource.Loading -> {
                        showProgress()
                        Toast.makeText(this, "Cargando...", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
                        hideProgress()
                        validateButton(false)
                        Toast.makeText(this, result.data, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, SplashActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
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

    private fun showForgotPasswordDialog() {
        binding.forgotCurtain.visibility = View.VISIBLE
        customDialog(this, R.layout.dialog_forgot_pass){ view, dialog ->
            val dialogBinding = DialogForgotPassBinding.bind(view)

            lateinit var email: String
            lateinit var answer: String
            lateinit var newPassword: String
            lateinit var confirmPassword: String
            var status = VERIFY_EMAIL

            //binding
            val cetEmail         = dialogBinding.cetEmailRecoveryPass
            val etEmail         = dialogBinding.etEmailRecoveryPass
            val cetAnswer        = dialogBinding.cetAnswerRecoveryPass
            val etAnswer        = dialogBinding.etAnswerRecoveryPass
            val cetPassword      = dialogBinding.cetPasswordRecoveryPass
            val etPassword      = dialogBinding.etPasswordRecoveryPass
            val cetConfirmPassword = dialogBinding.cetConfirmPasswordRecoveryPass
            val etConfirmPassword = dialogBinding.etConfirmPasswordRecoveryPass
            val lblQuestion    = dialogBinding.lblQuestion
            val lblHelpPhrase  = dialogBinding.lblHelpPhrase
            val progressRecover       = dialogBinding.progressRecover
            val btnRecoveryPass  = dialogBinding.btnRecoveryPass

            btnRecoveryPass.apply {
                isEnabled = false
                setBackgroundResource(R.drawable.btn_corner_disable)
            }

            //First action
            val validatorEmail = afterTextChanged {
                email   = etEmail.text.toString().trim()

                cetEmail.error = when {
                    !isEmailValid(email) && !isNullOrEmpty(email) -> "Correo incorrecto"
                    else -> null
                }

                btnRecoveryPass.apply {
                    isEnabled = isEmailValid(email) && !isNullOrEmpty(email)
                    if (isEnabled) setBackgroundResource(R.drawable.btn_corner)
                    else setBackgroundResource(R.drawable.btn_corner_disable)
                }
            }

            //Second action
            val validatorAnswer = afterTextChanged {
                answer   = etAnswer.text.toString().trim()

                btnRecoveryPass.apply {
                    isEnabled = !isNullOrEmpty(answer)
                    if (isEnabled) setBackgroundResource(R.drawable.btn_corner)
                    else setBackgroundResource(R.drawable.btn_corner_disable)
                }
            }

            //Thrid action
            val validatorPassword = afterTextChanged {
                newPassword   = etPassword.text.toString().trim()
                confirmPassword   = etConfirmPassword.text.toString().trim()

                cetPassword.error = when {
                    isNullOrEmpty(newPassword) -> "Ingrese una contraseña"
                    newPassword.length !in 5..20 -> "La contraseña debe tener entre 5 a 20 caracteres"
                    else -> null
                }

                if (confirmPassword.isNotEmpty()){
                    cetConfirmPassword.error = when {
                        confirmPassword.length !in 5..20 -> "La contraseña debe tener entre 5 a 20 caracteres"
                        newPassword != confirmPassword -> "Las contraseñas no coinciden"
                        else -> null
                    }
                }

                btnRecoveryPass.apply {
                    isEnabled = !isNullOrEmpty(newPassword)
                                && !isNullOrEmpty(confirmPassword)
                                && newPassword.length in 5..20
                                && confirmPassword.length in 5..20
                                && newPassword == confirmPassword
                    if (isEnabled) setBackgroundResource(R.drawable.btn_corner)
                    else setBackgroundResource(R.drawable.btn_corner_disable)
                }
            }

            etEmail.addTextChangedListener(validatorEmail)
            etAnswer.addTextChangedListener(validatorAnswer)
            etPassword.addTextChangedListener(validatorPassword)
            etConfirmPassword.addTextChangedListener(validatorPassword)

            btnRecoveryPass.setOnClickListener {
                when(status){
                    VERIFY_EMAIL -> {
                        viewModel.verifyEmail(email).observe(this, Observer {
                            it?.let { result ->
                                when (result) {
                                    is Resource.Loading -> {
                                        progressRecover.visibility = View.VISIBLE
                                        btnRecoveryPass.apply {
                                            visibility = View.INVISIBLE
                                            isEnabled = false
                                            setBackgroundResource(R.drawable.btn_corner_disable)
                                        }
                                    }
                                    is Resource.Success -> {
                                        cetEmail.visibility = View.GONE
                                        progressRecover.visibility = View.GONE
                                        btnRecoveryPass.visibility = View.VISIBLE

                                        //Set data
                                        status = VERIFY_RESPONSE
                                        viewModel.email = email
                                        lblQuestion.text = "Pregunta: ${result.data.question}"
                                        lblHelpPhrase.text = "Ayuda: ${result.data.phrase}"

                                        //Success
                                        lblQuestion.visibility = View.VISIBLE
                                        lblHelpPhrase.visibility = View.VISIBLE
                                        cetAnswer.visibility = View.VISIBLE
                                    }
                                    is Resource.Failure -> {
                                        progressRecover.visibility = View.GONE
                                        btnRecoveryPass.visibility = View.VISIBLE

                                        Toast.makeText(this, result.message, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }
                        })
                    }

                    VERIFY_RESPONSE -> {
                        viewModel.verifyResponse(viewModel.email, answer).observe(this, Observer {
                            it?.let { result ->
                                when (result) {
                                    is Resource.Loading -> {
                                        progressRecover.visibility = View.VISIBLE
                                        btnRecoveryPass.apply {
                                            visibility = View.INVISIBLE
                                            isEnabled = false
                                            setBackgroundResource(R.drawable.btn_corner_disable)
                                        }
                                    }
                                    is Resource.Success -> {
                                        status = CHANGE_PASSWORD
                                        progressRecover.visibility = View.GONE
                                        btnRecoveryPass.visibility = View.VISIBLE

                                        lblQuestion.visibility = View.GONE
                                        cetAnswer.visibility = View.GONE
                                        lblHelpPhrase.visibility = View.GONE

                                        cetPassword.visibility = View.VISIBLE
                                        cetConfirmPassword.visibility = View.VISIBLE
                                    }
                                    is Resource.Failure -> {
                                        progressRecover.visibility = View.GONE
                                        btnRecoveryPass.visibility = View.VISIBLE

                                        Toast.makeText(this, result.message, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }
                        })
                    }

                    else -> {
                        viewModel.changePassword(viewModel.email, newPassword)
                            .observe(this, Observer {
                                it?.let { result ->
                                    when (result) {
                                        is Resource.Loading -> {
                                            progressRecover.visibility = View.VISIBLE
                                            btnRecoveryPass.apply {
                                                visibility = View.INVISIBLE
                                                isEnabled = false
                                                setBackgroundResource(R.drawable.btn_corner_disable)
                                            }
                                        }
                                        is Resource.Success -> {
                                            progressRecover.visibility = View.GONE
                                            btnRecoveryPass.visibility = View.VISIBLE
                                            Toast.makeText(this, result.data, Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                        is Resource.Failure -> {
                                            progressRecover.visibility = View.GONE
                                            btnRecoveryPass.visibility = View.VISIBLE

                                            Toast.makeText(this, result.message, Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    }
                                }
                            })
                    }
                }
            }

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

    private fun showProgress(){
        progress.visibility = View.VISIBLE
        btnSignIn.visibility = View.GONE
    }

    private fun hideProgress(){
        progress.visibility = View.GONE
        btnSignIn.visibility = View.VISIBLE
    }

    private fun validateButton(validator: Boolean){
        btnSignIn.apply {
            isEnabled = validator
            if (isEnabled) setBackgroundResource(R.drawable.btn_corner)
            else setBackgroundResource(R.drawable.btn_corner_disable)
        }
    }

    private fun clearInputs(){
        etPasswordSignIn.text?.clear()
        etEmailSignIn.text?.clear()
        cetEmailSignIn.clearFocus()
        cetPasswordSignIn.clearFocus()
    }
}