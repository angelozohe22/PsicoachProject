package com.example.psicoachproject.ui.modules.psicosec.pendientes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.psicoachproject.R
import com.example.psicoachproject.common.utils.customDialog
import com.example.psicoachproject.common.utils.showSnackBar
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.core.aplication.preferences
import com.example.psicoachproject.databinding.ActivityHomeBinding
import com.example.psicoachproject.databinding.DialogCancelPendingBinding
import com.example.psicoachproject.databinding.DialogImgVoucherBinding
import com.example.psicoachproject.databinding.FragmentPendingBinding
import com.example.psicoachproject.domain.model.Pending
import com.example.psicoachproject.ui.modules.MainActivity
import com.example.psicoachproject.ui.modules.client.viewmodel.PSClientViewModel
import com.example.psicoachproject.ui.modules.psicosec.pendientes.adapter.PendingAdapter

class PendingFragment : Fragment(), PendingAdapter.PendingListener {

    private var _binding: FragmentPendingBinding? = null
    private val binding get() = _binding!!

    private lateinit var bindingAc: ActivityHomeBinding

    private val pendingAdapter by lazy { PendingAdapter(this) }
    private lateinit var viewModel: PSClientViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentPendingBinding.inflate(layoutInflater, container, false)

        val customPhrase = preferences.phrase.toLowerCase()
        val customName = if (preferences.name.contains("Usuario")) " (:" else ", ${preferences.name}"

        binding.lblName.text = "Bienvenid@$customName"
        binding.lblPhrase.text = "\" ${customPhrase.capitalize()} \" "

        viewModel = (activity as MainActivity).viewModelCita
        bindingAc = (activity as MainActivity).binding

        setupRecycler()
        getPendings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupRecycler(){
        binding.rvPending.apply {
            adapter = pendingAdapter
            layoutManager = LinearLayoutManager( context,
                LinearLayoutManager.VERTICAL,false)
        }
    }

    private fun getPendings(){
        viewModel.getPendingList().observe(viewLifecycleOwner){
            it?.let { result ->
                when(result){
                    is Resource.Loading -> {
                        hideEmpty()
                        showProgress()
                    }
                    is Resource.Success -> {
                        hideProgress()
                        println("--->> Result ${result.data}")
                        val data = result.data.toMutableList()
                        if(data.isEmpty()) showEmpty()
                        else pendingAdapter.setData(data)
                    }
                    is Resource.Failure -> {
                        println("--->>Falla")
                        hideProgress()
                        showEmpty()
                    }
                }
            }
        }
    }

    override fun acceptAppointment(pending: Pending) {
        viewModel.changeStateAppointment(pending.id.toString(), "1").observe(viewLifecycleOwner){
            it?.let { result->
                when(result){
                    is Resource.Loading -> {
                        binding.lyContainer.showSnackBar("Cargando...")
                    }
                    is Resource.Success -> {
                        println("--->>aceptar entra")
                        pendingAdapter.deleteItem(pending)
                        binding.lyContainer.showSnackBar(result.data)
                    }
                    is Resource.Failure -> {
                        println("--->>aceptar falla")
                        binding.lyContainer.showSnackBar("Ha ocurrido un error")
                    }
                }
            }
        }
    }

    override fun cancelAppointment(pending: Pending) {
        //mostrar modal
//        binding.curtainModal.visibility = View.VISIBLE
        bindingAc.curtainModalGeneral.visibility = View.VISIBLE
        customDialog(requireContext(), R.layout.dialog_cancel_pending){ view, dialog ->
            val dialogBinding = DialogCancelPendingBinding.bind(view)

            val titleDialogDelete = dialogBinding.titleDialogDelete
            val btnConfim= dialogBinding.btnConfirmDeleteTask
            val btnCancel = dialogBinding.btnCancelDeleteTask

            btnConfim.setOnClickListener {
                dialog.dismiss()
                viewModel.changeStateAppointment(pending.id.toString(), "0").observe(viewLifecycleOwner){
                    it?.let { result->
                        when(result){
                            is Resource.Loading -> {
                                binding.lyContainer.showSnackBar("Cargando...")
                            }
                            is Resource.Success -> {
                                pendingAdapter.deleteItem(pending)
                                binding.lyContainer.showSnackBar(result.data)
                            }
                            is Resource.Failure -> {
                                binding.lyContainer.showSnackBar("Ha ocurrido un error")
                            }
                        }
                    }
                }
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setOnDismissListener {
                bindingAc.curtainModalGeneral.visibility = View.GONE
//                binding.curtainModal.visibility = View.GONE
            }
        }
    }

    override fun goToSeeVoucher(pending: Pending) {
//        binding.curtainModal.visibility = View.VISIBLE
        bindingAc.curtainModalGeneral.visibility = View.VISIBLE

        customDialog(requireContext(), R.layout.dialog_img_voucher){ view, dialog ->
            val dialogVoucherBindning = DialogImgVoucherBinding.bind(view)

            dialogVoucherBindning.imgVoucher.load(R.drawable.image_voucher)

            dialog.setOnDismissListener {
//                binding.curtainModal.visibility = View.GONE
                bindingAc.curtainModalGeneral.visibility = View.GONE
            }

        }
    }


    private fun showProgress(){
        binding.apply {
            progress.visibility = View.VISIBLE
            lblProgress.visibility = View.VISIBLE
        }
    }

    private fun hideProgress(){
        binding.apply {
            progress.visibility = View.GONE
            lblProgress.visibility = View.GONE
        }
    }

    private fun showEmpty(){
        binding.apply {
            imageEmpty.visibility = View.VISIBLE
            lblEmpty.visibility = View.VISIBLE
        }
    }

    private fun hideEmpty(){
        binding.apply {
            imageEmpty.visibility = View.GONE
            lblEmpty.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}