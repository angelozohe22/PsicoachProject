package com.example.psicoachproject.ui.modules.home.psicosec.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.psicoachproject.R
import com.example.psicoachproject.common.utils.getColorPackage
import com.example.psicoachproject.databinding.ItemPendingBinding
import com.example.psicoachproject.domain.model.Pending

/**
 * Created by Angelo on 7/18/2021
 */
class PendingAdapter(
    private val listener: PendingListener
): RecyclerView.Adapter<PendingAdapter.PendingViewHolder>() {

    private var _pendingList = emptyList<Pending>() //Change this variable

    fun setData(data: List<Pending>){
        this._pendingList = data
        notifyDataSetChanged()
    }

    fun deleteItem(){
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PendingAdapter.PendingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pending, parent, false)
        return PendingViewHolder(view)
    }

    override fun onBindViewHolder(holder: PendingAdapter.PendingViewHolder, position: Int) {
        val pending = _pendingList[position]
        holder.bind(pending)
    }

    override fun getItemCount(): Int = _pendingList.count()

    inner class PendingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val bindingPending = ItemPendingBinding.bind(itemView)

        fun bind(pending: Pending){
            bindingPending.apply {
                lblDesc.text = "Tema: ${pending.issue}"

                println("---> package name: ${pending.packageName}")
                containerPackage.setCardBackgroundColor(ContextCompat.getColor(itemView.context, getColorPackage(pending.packageName)))
                lblPackageName.text = "${pending.packageName}"

                btnAceptar.setOnClickListener {
                    listener.acceptAppointment(pending)
                }

                btnCancelar.setOnClickListener {
                    listener.cancelAppointment(pending)
                }

                lblSeeVoucher.setOnClickListener {

                }
            }
        }

    }

    interface PendingListener{
        fun acceptAppointment(pending: Pending)
        fun cancelAppointment(pending: Pending)
        fun goToSeeVoucher(pending: Pending)
    }

}