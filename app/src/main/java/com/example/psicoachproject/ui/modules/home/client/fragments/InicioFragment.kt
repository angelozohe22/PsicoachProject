package com.example.psicoachproject.ui.modules.home.client.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.psicoachproject.core.aplication.preferences
import com.example.psicoachproject.databinding.FragmentInicioBinding
import com.example.psicoachproject.domain.model.Promotion
import com.example.psicoachproject.ui.modules.home.client.activities.PromotionActivity
import com.example.psicoachproject.ui.modules.home.client.fragments.adapter.PromotionPageAdapter

class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? =null
    private val binding get() = _binding!!

    private val promoAdapter by lazy{ PromotionPageAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInicioBinding.inflate(layoutInflater, container, false)

        val customPhrase = preferences.phrase.toLowerCase()
        val customName = if (preferences.name.contains("Usuario")) " (:" else ", ${preferences.name}"

        binding.lblName.text = "Bienvenid@$customName"
        binding.lblPhrase.text = "\" ${customPhrase.capitalize()} \" "
        setupPromoRV()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupPromoRV(){
        binding.vpPromo.apply {
            adapter = promoAdapter
        }
        val promoList = listOf(
            Promotion("Básica", 70.00, "x 1 sesiones (70 c/u) \n+consulta","#f4a261", listOf("Informe firmado y sellado: S/. 60")),
            Promotion("Premium", 340.00, "x 5 sesiones (60 c/u) \n+consulta","#EE6055", listOf("Informe firmado y sellado: S/. 60")),
            Promotion("Medium", 360.00, "x 5 sesiones (65 c/u) \n+consulta","#F4F269", listOf("Acceso exclusivo al grupo de apoyo psicológico", "Acceso gratuito a charlas psicológicas", "Informe firmado y sellado: S/. 30")),
            Promotion("Regular", 380.00, "x 5 sesiones (70 c/u) \n+ consulta","#99EB72", listOf("Acceso exclusivo al grupo de apoyo psicológico", "Acceso gratuito a charlas psicológicas", "Acceso gratuito a  talleres, charlas y capacitaciones", "Informe firmado y sellado: S/. 15")),
            Promotion("Luxury", 420.00, "x 8 sesiones (50 c/u) \n+consulta","#BBDEF9", listOf("Beneficios del paquete Premium", "Pagos en 2 cuotas (210 c/u)")) //
        )
        promoAdapter.setData(promoList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onPromoClickListener(promo: Promotion) {
//        val promoBundle = Bundle()
//        promoBundle.putParcelable("promo", promo)
//        val intent = Intent(requireActivity(), PromotionActivity::class.java)
//        intent.putExtras(promoBundle)
//        startActivity(intent)
//    }

}