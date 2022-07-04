package com.example.psicoachproject.ui.modules.psicosec.pendientes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.psicoachproject.R
import com.example.psicoachproject.common.utils.getColorPackage
import com.example.psicoachproject.common.utils.showSnackBar
import com.example.psicoachproject.databinding.ItemPendingBinding
import com.example.psicoachproject.domain.model.Pending

/**
 * Created by Angelo on 7/18/2021
 */
class PendingAdapter(
    private val listener: PendingListener
): RecyclerView.Adapter<PendingAdapter.PendingViewHolder>() {

    private var _pendingList = mutableListOf<Pending>() //Change this variable

    fun setData(data: MutableList<Pending>){
        this._pendingList = data
        notifyDataSetChanged()
    }

    fun deleteItem(item: Pending){
        _pendingList.remove(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PendingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pending, parent, false)
        return PendingViewHolder(view)
    }

    override fun onBindViewHolder(holder: PendingViewHolder, position: Int) {
        val pending = _pendingList[position]
        holder.bind(pending, position)
    }

    override fun getItemCount(): Int = _pendingList.count()

    inner class PendingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val bindingPending = ItemPendingBinding.bind(itemView)

        fun bind(pending: Pending, position: Int){
            bindingPending.apply {
                lblDesc.text = "Tema: ${pending.issue}"

                containerPackage.setCardBackgroundColor(ContextCompat.getColor(itemView.context, getColorPackage(pending.packageName)))
                lblPackageName.text = "${pending.packageName}"

                btnAceptar.setOnClickListener {
                    listener.acceptAppointment(pending)
                }

                btnCancelar.setOnClickListener {
                    listener.cancelAppointment(pending)
                }

                imgIcon.setOnClickListener {
                    if(position == 0){
                        listener.goToSeeVoucher(pending)
                    }else{
                        lyItemContainer.showSnackBar("Sin voucher")
                    }
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