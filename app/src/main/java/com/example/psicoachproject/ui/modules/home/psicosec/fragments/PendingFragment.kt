package com.example.psicoachproject.ui.modules.home.psicosec.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psicoachproject.R
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.databinding.FragmentPendingBinding
import com.example.psicoachproject.domain.model.Pending
import com.example.psicoachproject.ui.modules.home.HomeActivity
import com.example.psicoachproject.ui.modules.home.client.activities.viewmodel.HomeViewModel
import com.example.psicoachproject.ui.modules.home.psicosec.fragments.adapters.PendingAdapter

class PendingFragment : Fragment(), PendingAdapter.PendingListener {

    private var _binding: FragmentPendingBinding? = null
    private val binding get() = _binding!!

    private val pendingAdapter by lazy { PendingAdapter(this) }
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentPendingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HomeActivity).viewModelCita
        setupRecycler()
        getPendings()
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
                        val data = result.data
                        if(data.isEmpty()) showEmpty()
                        else pendingAdapter.setData(data)
                    }
                    is Resource.Failure -> {
                        hideProgress()
                        showEmpty()
                    }
                }
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

    override fun goToSeeVoucher(pending: Pending) {
    }

    override fun acceptAppointment(pending: Pending) {
    }

    override fun cancelAppointment(pending: Pending) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}