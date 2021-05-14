package com.example.psicoachproject.ui.modules.main.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import com.example.psicoachproject.R
import com.example.psicoachproject.databinding.ActivityMainBinding
import com.example.psicoachproject.databinding.ActivitySignInBinding
import com.example.psicoachproject.databinding.DialogForgotPassBinding
import com.example.psicoachproject.utils.customDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignInActivity : AppCompatActivity() {

    private lateinit var binding            : ActivitySignInBinding

    private lateinit var lblForgotPassword  : AppCompatTextView
    private lateinit var btnBackSignIn      : AppCompatImageButton
    private lateinit var lblSignUp          : AppCompatTextView
    private lateinit var btnSignIn          : AppCompatButton
    private lateinit var cetEmail           : TextInputLayout
    private lateinit var etEmail            : TextInputEditText
    private lateinit var cetPassword        : TextInputLayout
    private lateinit var etPassword         : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding
        lblForgotPassword   = binding.lblForgotPassword
        btnBackSignIn       = binding.btnBackSignIn
        lblSignUp           = binding.lblSignUp
        btnSignIn           = binding.btnSignIn
        cetEmail            = binding.cetEmailSignIn
        etEmail             = binding.etEmailSignIn
        cetPassword         = binding.cetPasswordSignIn
        etPassword          = binding.etPasswordSignIn

        lblForgotPassword.setOnClickListener { showForgotPasswordDialog() }



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
}