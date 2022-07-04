package com.example.psicoachproject.ui.modules.client.cita

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psicoachproject.R
import com.example.psicoachproject.common.design.DatePickerFragment
import com.example.psicoachproject.common.utils.*
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.core.aplication.preferences
import com.example.psicoachproject.data.remote.source.dto.Combo
import com.example.psicoachproject.databinding.ActivityHomeBinding
import com.example.psicoachproject.databinding.DialogTerminosBinding
import com.example.psicoachproject.databinding.FragmentCitaBinding
import com.example.psicoachproject.domain.model.Meeting
import com.example.psicoachproject.domain.model.MeetingTime
import com.example.psicoachproject.ui.modules.PSMainActivity
import com.example.psicoachproject.ui.modules.viewmodel.PSViewModel
import com.example.psicoachproject.ui.modules.client.cita.adapter.BenefitListAdapter
import com.example.psicoachproject.ui.modules.client.fragments.adapter.MeetingAdapter
import com.example.psicoachproject.ui.modules.client.fragments.adapter.MeetingListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson

class MeetingFragment : Fragment(), MeetingListener {

    private var _binding: FragmentCitaBinding? =null
    private val binding get() = _binding!!

    private lateinit var bindingAc: ActivityHomeBinding

    private lateinit var lyContainer    : ConstraintLayout
    private lateinit var cddlPaquete    : TextInputLayout
    private lateinit var ddlPaquete     : AutoCompleteTextView
    private lateinit var cetPriceCita   : TextInputLayout
    private lateinit var cddlTema       : TextInputLayout
    private lateinit var etPriceCita    : TextInputEditText
    private lateinit var cetDescription : TextInputLayout
    private lateinit var etDescription  : TextInputEditText
    private lateinit var ddlTema        : AutoCompleteTextView
    private lateinit var rvCita         : RecyclerView

    private lateinit var btnRegisterCita: AppCompatButton
    private var meetingValidator: Boolean = false
    private var inputsValidator: Boolean = false

    private lateinit var combos: Combo
    private val meetingAdapter by lazy { MeetingAdapter(this) }
    private val terminosAdapter by lazy{ BenefitListAdapter() }

    private lateinit var viewModel: PSViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitaBinding.inflate(layoutInflater, container, false)

        //binding
        lyContainer     = binding.lyContainer
        cddlPaquete     = binding.cddlPaquete
        ddlPaquete      = binding.ddlPaquete
        cetPriceCita    = binding.cetPriceCita
        cddlTema        = binding.cddlTema
        etPriceCita     = binding.etPriceCita
        ddlTema         = binding.ddlTema
        btnRegisterCita = binding.btnRegisterCita
        rvCita          = binding.rvCitas
        cetDescription  = binding.cetDescripcionCita
        etDescription   = binding.etDescripcionCita

        combos = Gson().fromJson(preferences.combos, Combo::class.java)
        viewModel = (activity as PSMainActivity).viewModelCita
        bindingAc = (activity as PSMainActivity).binding

        setUpDropDowns()
        textInputValidator()
        setUpCitaRecycler()

        btnRegisterCita.setOnClickListener { goToRegisterUserInformation() }
        binding.btnInfo.setOnClickListener {
            showDialogTerms()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setUpDropDowns(){
        //----- packages -----
        val packageList = mutableListOf<String>()
        combos.products?.forEach { product ->
            packageList.add(product.name ?: "")
        }
        ddlPaquete.apply {
            setAdapter(ArrayAdapter(requireContext(), R.layout.item_drop_down, packageList))
            setOnItemClickListener { _, _, position, _ ->
                val countCitas = combos.products?.get(position)?.cantSession?.toInt() ?: 0
                meetingAdapter.setData(List(countCitas){""})
                etPriceCita.setText(combos.products?.get(position)?.price ?: "")
            }
        }

        //----- themes -----
        val themeList = mutableListOf<String>()
        combos.diseasesList?.forEach { theme->
            themeList.add(theme.name ?: "")
        }
        ddlTema.apply {
            setAdapter(ArrayAdapter(requireContext(), R.layout.item_drop_down, themeList))
        }

    }

    private fun showDialogTerms(){
//        binding.curtainModal.visibility = View.VISIBLE
        bindingAc.curtainModalGeneral.visibility = View.VISIBLE
        customDialog(requireContext(), R.layout.dialog_terminos){ view, dialog->
            val bindingDialog = DialogTerminosBinding.bind(view)

            bindingDialog.rvTerminos.apply {
                adapter = terminosAdapter
                layoutManager = LinearLayoutManager( requireContext(),
                    LinearLayoutManager.VERTICAL,false)
            }
            val termsList = listOf(
                "Si se ausenta su sesión, no habrá opción a recuperarlo",
                "Se podrá cancelar o reprogramar la sesión con 24 horas de anticipación",
                "Cada sesión se realiza 1 vez por semana a menos que el especialista modifique la frecuencia de las sesiones con previo aviso"
            )
            terminosAdapter.setData(termsList)

            dialog.setOnDismissListener {
                bindingAc.curtainModalGeneral.visibility = View.GONE
            }

        }
    }

    private fun setUpCitaRecycler(){
        rvCita.apply {
            adapter = meetingAdapter
            layoutManager = LinearLayoutManager( context,
                    LinearLayoutManager.VERTICAL,false)
        }
        meetingAdapter.setData(emptyList())
    }

    private fun textInputValidator(){
        validateButton(false)

        val validator = afterTextChanged {
            val validate = !isNullOrEmpty(ddlPaquete.text.trim().toString())
                           && !isNullOrEmpty(etPriceCita.text?.trim().toString())
                           && !isNullOrEmpty(ddlTema.text.trim().toString())
                           //&& !isNullOrEmpty(etDescription.text?.trim().toString())

            inputsValidator = validate
            validateButton(validate)
        }

        ddlPaquete.addTextChangedListener(validator)
        etPriceCita.addTextChangedListener(validator)
        ddlTema.addTextChangedListener(validator)
        etDescription.addTextChangedListener(validator)
    }

    private fun validateButton(validator: Boolean){
        binding.btnRegisterCita.apply {
            isEnabled = validator && meetingValidator
            if (isEnabled) setBackgroundResource(R.drawable.btn_corner)
            else setBackgroundResource(R.drawable.btn_corner_disable)
        }
    }

    override fun showDatePicker(datePicker: DatePickerFragment) {
        datePicker.show(requireActivity().supportFragmentManager, "datePicker")
    }

    override fun validateMeeting(horaCita: MeetingTime, completion: (Boolean) -> Unit) {
        viewModel.validateCita(horaCita).observe(viewLifecycleOwner) {
            it?.let { result ->
                when(result){
                    is Resource.Loading -> {
                        lyContainer.showSnackBar("Validando...")
                    }
                    is Resource.Success -> {
                        completion(true)
                        meetingAdapter.apply {
                            setNewMeeting(horaCita.date, horaCita.startDate, horaCita.endDate)
                            meetingValidator = !(getValidateList().contains(false))
                            validateButton(inputsValidator && meetingValidator)
                        }
                        lyContainer.showSnackBar("Es valido")
                    }
                    is Resource.Failure -> {
                        lyContainer.showSnackBar(result.message)
                        completion(false)
                    }
                }
            }
        }
    }

    private fun goToRegisterUserInformation(){
        //validar que ninguna meeting tenga el mismo horario
        // un liveData en el viewModel para los validator de los botones, como estará en constante escucha solo cuando todoss sus valores sean true, valido los otros campos y activo btn
        // una funcion que me traiga las fechas y las horas

        val citaBundle = Bundle()

        val idx = combos.products?.first { it.name == ddlPaquete.text.trim().toString() }?.id
        if (idx == null){
            validateButton(false)
            lyContainer.showSnackBar("Elige un paquete")
            return
        }

        meetingAdapter.getMeetingValues{ dateList,
                                         startTimeList,
                                         endTimeList->
            val meeting = Meeting(productId   = idx,
                    disease     = ddlTema.text.trim().toString(),
                    description = etDescription.text?.trim().toString(),
                    date        = dateList,
                    startDate   = startTimeList,
                    endDate     = endTimeList)

            validateButton(false)
            citaBundle.putParcelable("meeting", meeting)
            val intent = Intent(requireContext(), DatosActivity::class.java)
            intent.putExtras(citaBundle)
            requireContext().startActivity(intent)
        }
    }

    override fun changeValidatorState() { validateButton(false) }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}