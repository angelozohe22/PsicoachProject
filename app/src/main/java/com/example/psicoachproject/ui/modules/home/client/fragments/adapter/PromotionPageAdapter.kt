package com.example.psicoachproject.ui.modules.home.client.fragments.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.example.psicoachproject.R
import com.example.psicoachproject.databinding.ItemPromoBinding
import com.example.psicoachproject.domain.model.Promotion


/**
 * Created by Angelo on 6/28/2021
 */
class PromotionPageAdapter(
    private val context: Context
): PagerAdapter() {

//    interface OnPromoClickListener{
//        fun onPromoClickListener(promo: Promotion)
//    }

    private var _promotionList = emptyList<Promotion>()

    fun setData(data: List<Promotion>){
        this._promotionList = data
        notifyDataSetChanged()
    }

    override fun getCount(): Int = _promotionList.size

    override fun isViewFromObject(v: View, `object`: Any): Boolean {
        return v === `object` as View
    }

    override fun destroyItem(parent: ViewGroup, position: Int, `object`: Any) {
        parent.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val benefitAdapter = BenefitListAdapter()
        val view = LayoutInflater.from(context).inflate(R.layout.item_promo, container, false)
        val promoBinding = ItemPromoBinding.bind(view)

        if (_promotionList.isNotEmpty()){
            promoBinding.apply {
                lblTitle.text = _promotionList[position].name
                lblPrice.text = "S/. ${_promotionList[position].price}"
                lblDescription.text = _promotionList[position].description
                rvBenefits.apply {
                    adapter = benefitAdapter
                    layoutManager = LinearLayoutManager( context,
                        LinearLayoutManager.VERTICAL,false)
                }
                benefitAdapter.setData(_promotionList[position].benefitList)
                cardContainerPromo.setCardBackgroundColor(Color.parseColor(_promotionList[position].color))
//                cardContainerPromo.setOnClickListener {
//                    promoClickListener.onPromoClickListener(_promotionList[position])
//                }
            }
        }
        container.addView(view)
        return view
    }

}