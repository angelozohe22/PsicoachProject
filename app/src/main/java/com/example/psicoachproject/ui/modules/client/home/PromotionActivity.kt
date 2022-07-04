package com.example.psicoachproject.ui.modules.client.home

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psicoachproject.common.utils.setStatusBarColor
import com.example.psicoachproject.databinding.ActivityPromotionBinding
import com.example.psicoachproject.domain.model.Promotion
import com.example.psicoachproject.ui.modules.client.cita.adapter.BenefitListAdapter

class PromotionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPromotionBinding
    private lateinit var promo   : Promotion
    private val benefitAdapter by lazy{ BenefitListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPromotionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.extras != null){
            promo = intent.getParcelableExtra<Promotion>("promo") as Promotion
        }

        setStatusBarColor(this, Color.parseColor(promo.color))
        binding.vContainer.setBackgroundColor(Color.parseColor(promo.color))
        binding.lblPromoDesc.text = promo.name
        binding.lblPrecioBene.text = "S/. ${promo.price}"
        binding.btnBuyNow.setOnClickListener {
            onBackPressed()
//            Toast.makeText(this, "Muy pronto!", Toast.LENGTH_SHORT).show()
        }
        setupRvBenefits()
    }

    private fun setupRvBenefits(){
        binding.rvBenefitsPromo.apply {
            adapter = benefitAdapter
            layoutManager = LinearLayoutManager( applicationContext,
                LinearLayoutManager.VERTICAL,false)
        }
        benefitAdapter.setData(promo.benefitList)
    }

}