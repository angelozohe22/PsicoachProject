package com.example.psicoachproject.ui.modules.client.cita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.psicoachproject.R
import com.example.psicoachproject.common.utils.afterTextChanged
import com.example.psicoachproject.common.utils.isEmailValid
import com.example.psicoachproject.common.utils.isNullOrEmpty
import com.example.psicoachproject.common.utils.showSnackBar
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.core.aplication.preferences
import com.example.psicoachproject.data.remote.source.dto.Combo
import com.example.psicoachproject.data.remote.source.home.HomeRemoteDataSourceImpl
import com.example.psicoachproject.databinding.ActivityDatosBinding
import com.example.psicoachproject.domain.model.Meeting
import com.example.psicoachproject.domain.model.DatosPersonaCita
import com.example.psicoachproject.domain.repository.home.HomeRepositoryImpl
import com.example.psicoachproject.ui.modules.client.viewmodel.PSClientViewModel
import com.example.psicoachproject.ui.modules.client.viewmodel.PSClientViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson

class DatosActivity : AppCompatActivity() {

    private lateinit var binding  : ActivityDatosBinding

    private lateinit var lyContainer           : ConstraintLayout
    private lateinit var cetNombreDatos        : TextInputLayout
    private lateinit var cetApellidosDatos     : TextInputLayout
    private lateinit var cetEdadDatos          : TextInputLayout
    private lateinit var cetTelefonoDatos      : TextInputLayout
    private lateinit var cetNrodocDatos        : TextInputLayout
    private lateinit var cetEmailDatos         : TextInputLayout
    private lateinit var etNombreDatos         : TextInputEditText
    private lateinit var etApellidosDatos      : TextInputEditText
    private lateinit var etEdadDatos           : TextInputEditText
    private lateinit var etTelefonoDatos       : TextInputEditText
    private lateinit var etNrodocDatos         : TextInputEditText
    private lateinit var etEmailDatos          : TextInputEditText
    private lateinit var cddlGeneroDatos       : TextInputLayout
    private lateinit var cddlDocumentoDatos    : TextInputLayout
    private lateinit var ddlGeneroDatos        : AutoCompleteTextView
    private lateinit var ddlDocumentoDatos     : AutoCompleteTextView
    private lateinit var btnSendDatos          : AppCompatButton

    private lateinit var meeting     : Meeting
    private lateinit var combos   : Combo

    private val viewModel by viewModels<PSClientViewModel>{
        PSClientViewModelFactory(
                HomeRepositoryImpl(
                        remote = HomeRemoteDataSourceImpl()
                )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            meeting = intent.getParcelableExtra<Meeting>("meeting") as Meeting
        }

        lyContainer        = binding.lyContainer
        cetNombreDatos     = binding.cetNombreDatos
        cetApellidosDatos  = binding.cetApellidosDatos
        cetEdadDatos       = binding.cetEdadDatos
        cetTelefonoDatos   = binding.cetTelefonoDatos
        cetNrodocDatos     = binding.cetNrodocDatos
        cetEmailDatos      = binding.cetEmailDatos
        etNombreDatos      = binding.etNombreDatos
        etApellidosDatos   = binding.etApellidosDatos
        etEdadDatos        = binding.etEdadDatos
        etTelefonoDatos    = binding.etTelefonoDatos
        etNrodocDatos      = binding.etNrodocDatos
        etEmailDatos       = binding.etEmailDatos
        cddlGeneroDatos    = binding.cddlGeneroDatos
        cddlDocumentoDatos = binding.cddlDocumentoDatos
        ddlGeneroDatos     = binding.ddlGeneroDatos
        ddlDocumentoDatos  = binding.ddlDocumentoDatos
        btnSendDatos       = binding.btnSendDatos

        combos = Gson().fromJson(preferences.combos, Combo::class.java)

        setUpDropDowns()
        textInputValidator()

        btnSendDatos.setOnClickListener {
            registrarCita()
        }

        binding.btnBackDatos.setOnClickListener {
            onBackPressed()
        }

        if (!isNullOrEmpty(preferences.email)){
            etEmailDatos.setText(preferences.email)
        }

    }

    private fun setUpDropDowns(){
        //----- genders -----
        val genderList = mutableListOf<String>()
        combos.combosList?.forEach {
            genderList.add(it.name ?: "")
        }
        ddlGeneroDatos.apply {
            setAdapter(ArrayAdapter(context, R.layout.item_drop_down, genderList))
        }

        //----- doc -----
        val docList = mutableListOf<String>()
        combos.documentList?.forEach {
            docList.add(it.name ?: "")
        }
        ddlDocumentoDatos.apply {
            setAdapter(ArrayAdapter(context, R.layout.item_drop_down, docList))
        }

    }

    private fun textInputValidator(){
        validateButton(false)

        val validator = afterTextChanged {

            val nombre     = etNombreDatos.text?.trim().toString()
            val apellidos  = etApellidosDatos.text?.trim().toString()
            val genero     = ddlGeneroDatos.text.trim().toString()
            val edad       = etEdadDatos.text?.trim().toString()
            val telefono   = etTelefonoDatos.text?.trim().toString()
            val docType    = ddlDocumentoDatos.text?.trim().toString()
            val nroDoc     = etNrodocDatos.text?.trim().toString()
            val email      = etEmailDatos.text?.trim().toString()

            cetEmailDatos.error = when{
                !isEmailValid(email) && email.isNotEmpty() -> "Correo incorrecto"
                else -> null
            }

            validateButton(!isNullOrEmpty(nombre)
                           && !isNullOrEmpty(apellidos)
                           && !isNullOrEmpty(genero)
                           && !isNullOrEmpty(edad)
                           && !isNullOrEmpty(telefono)
                           && !isNullOrEmpty(docType)
                           && !isNullOrEmpty(nroDoc)
                           && !isNullOrEmpty(email)
                           && isEmailValid(email))
        }

        etNombreDatos.addTextChangedListener(validator)
        etApellidosDatos.addTextChangedListener(validator)
        ddlGeneroDatos.addTextChangedListener(validator)
        etEdadDatos.addTextChangedListener(validator)
        etTelefonoDatos.addTextChangedListener(validator)
        ddlDocumentoDatos.addTextChangedListener(validator)
        etNrodocDatos.addTextChangedListener(validator)
        etEmailDatos.addTextChangedListener(validator)
    }

    private fun registrarCita(){
        val dateCita = DatosPersonaCita(
                etNombreDatos.text?.trim().toString(),
                etApellidosDatos.text?.trim().toString(),
                etEdadDatos.text?.trim().toString(),
                combos.documentList?.first { it.name == ddlDocumentoDatos.text?.trim().toString() }?.id ?: 0,
                etNrodocDatos.text?.trim().toString(),
                combos.combosList?.first { it.name == ddlGeneroDatos.text?.trim().toString() }?.id ?: 0,
                etTelefonoDatos.text?.trim().toString(),
                arrayListOf(),
                meeting)

         //revisar
        viewModel.registerMeeting(
                name = etNombreDatos.text?.trim().toString(),
                surname = etApellidosDatos.text?.trim().toString(),
                age = etEdadDatos.text?.trim().toString(),
                document_id = combos.documentList?.first { it.name == ddlDocumentoDatos.text?.trim().toString() }?.id ?: 0,
                document_number = etNrodocDatos.text?.trim().toString(),
                gender_id = combos.combosList?.first { it.name == ddlGeneroDatos.text?.trim().toString() }?.id ?: 0,
                phone = etTelefonoDatos.text?.trim().toString(),
                emails = listOf(etEmailDatos.text?.trim().toString()),
                product_id = meeting.productId,
                disease = meeting.disease,
                description = meeting.description,
                date = meeting.date,
                start_time = meeting.startDate,
                end_time = meeting.endDate
        ).observe(this){
            it?.let { result->
                when(result){
                    is Resource.Loading -> {
                        lyContainer.showSnackBar("Cargando...")
                    }
                    is Resource.Success -> {
                        println("Pasooooo success")
                        lyContainer.showSnackBar(result.data)
                        val intent = Intent(this, PagosActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is Resource.Failure -> {
                        println("Pasooooo error")
                        lyContainer.showSnackBar(result.message)
                    }
                }
            }
        }
    }

    private fun validateButton(validator: Boolean){
        btnSendDatos.apply {
            isEnabled = validator
            if (isEnabled) setBackgroundResource(R.drawable.btn_corner)
            else setBackgroundResource(R.drawable.btn_corner_disable)
        }
    }
}
