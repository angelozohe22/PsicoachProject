package com.example.psicoachproject.ui.login.intropager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.psicoachproject.R
import com.example.psicoachproject.databinding.FragmentIntroBinding

private val EXTRA_IMG = "EXTRA_ING_INTRO_FRAGMENT"
private val EXTRA_TITLE = "EXTRA_TITLE_INTRO_FRAGMENT"
private val EXTRA_DESC = "EXTRA_DESC_INTRO_FRAGMENT"

class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(layoutInflater, container, false)

        arguments?.let {
            binding.lblTitleSlider.text = it.getString(EXTRA_TITLE)
            binding.lblDescSlider.text = it.getString(EXTRA_DESC)
            binding.imgSlider.setImageResource(it.getInt(EXTRA_IMG))
        }
        return binding.root
    }

    fun newInstance(img: Int, title: String, desc: String) =
        IntroFragment().apply {
            arguments = Bundle().apply {
                putInt(EXTRA_IMG, img)
                putString(EXTRA_TITLE, title)
                putString(EXTRA_DESC, desc)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}