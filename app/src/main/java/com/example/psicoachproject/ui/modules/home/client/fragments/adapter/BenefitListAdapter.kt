package com.example.psicoachproject.ui.modules.home.client.fragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.psicoachproject.R
import com.example.psicoachproject.databinding.ItemBenefitBinding

/**
 * Created by Angelo on 6/28/2021
 */
class BenefitListAdapter: RecyclerView.Adapter<BenefitListAdapter.BenefitListViewHolder>() {

    private var _benefitsList = emptyList<String>()

    fun setData(data: List<String>){
        this._benefitsList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BenefitListAdapter.BenefitListViewHolder {
        return BenefitListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_benefit, parent, false))
    }

    override fun onBindViewHolder(holder: BenefitListAdapter.BenefitListViewHolder, position: Int) {
        val benefit = _benefitsList[position]
        holder.bindView(benefit)
    }

    override fun getItemCount(): Int = _benefitsList.size

    inner class BenefitListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val benefitBinding = ItemBenefitBinding.bind(itemView)

        fun bindView(benefit: String){
            benefitBinding.lblBenefit.text = benefit
        }
    }
}