package com.example.psicoachproject.ui.modules.main.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.psicoachproject.R
import com.example.psicoachproject.databinding.ActivityMainBinding
import com.example.psicoachproject.ui.modules.main.fragments.intro.IntroFragment
import com.example.psicoachproject.utils.transform.FadePageTransfomer
import com.example.psicoachproject.utils.transform.MyPageAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var btnSignIn   : AppCompatButton
    private lateinit var btnSignUp   : AppCompatButton
    private lateinit var lnIndSlider : LinearLayout
    private lateinit var vpSlider    : ViewPager

    //Variables
    private var listView    : MutableList<ImageView> = mutableListOf()
    private var mPageAdapter: MyPageAdapter? = null

    //Constants
    private val alphaHidden = 85
    private val marginItem  = 8
    private val marginMin   = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnSignIn   = binding.btnSignIn
        btnSignUp   = binding.btnSignUp
        lnIndSlider = binding.lnIndSlider
        vpSlider    = binding.vpSlider

        btnSignIn.setOnClickListener { goToSignIn() }
        btnSignUp.setOnClickListener { goToSignUp() }

        setUpPagerSlider()
    }

    private fun setUpPagerSlider() {
        val fragments: List<Fragment> = getFragments()
        mPageAdapter = MyPageAdapter(supportFragmentManager, fragments)

        vpSlider.setPageTransformer(false, FadePageTransfomer())
        vpSlider.adapter = mPageAdapter

        addBottomDots(0)

        vpSlider?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                addBottomDots(position)
            }
        })

    }

    private fun getFragments() : List<Fragment> {
        val fList = arrayListOf<Fragment>()

        fList.apply {
            add(IntroFragment().newInstance( R.drawable.ic_google,"Titulo 1","Descripcion 1"))
            add(IntroFragment().newInstance( R.drawable.ic_google,"Titulo 2","Descripcion 3"))
            add(IntroFragment().newInstance( R.drawable.ic_google,"Titulo 3","Descripcion 3"))
        }

        return fList
    }

    private fun addBottomDots(currentPage: Int) {
        lnIndSlider?.removeAllViews()
        val drawaInactive = ContextCompat.getDrawable(this, R.drawable.slider_dot_inactive)
        val drawaActive= ContextCompat.getDrawable(this, R.drawable.slider_dot_active)
        drawaInactive?.alpha = alphaHidden
        mPageAdapter?.count?.let {
            for (i: Int in 0 until it) {
                listView.add(ImageView(this))
                listView[i].setImageDrawable(drawaInactive)
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.setMargins(marginItem, marginMin, marginItem, marginMin)
                lnIndSlider?.addView(listView[i], params)
            }
            listView[currentPage].setImageDrawable(drawaActive)
        }
    }

    private fun goToSignIn(){
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    private fun goToSignUp(){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

}