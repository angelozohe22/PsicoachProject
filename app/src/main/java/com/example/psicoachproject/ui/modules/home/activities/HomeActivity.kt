package com.example.psicoachproject.ui.modules.home.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.psicoachproject.R
import com.example.psicoachproject.common.utils.setColorTintBottomNavigation
import com.example.psicoachproject.databinding.ActivityHomeBinding
import com.example.psicoachproject.ui.modules.main.activities.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding            : ActivityHomeBinding

    private lateinit var drawerLayout       : DrawerLayout
    private lateinit var navigationView     : NavigationView
    private lateinit var bottomNavigation   : BottomNavigationView
    private lateinit var navController      : NavController
    private lateinit var imgAppBarMenu      : AppCompatImageButton
    private lateinit var imgAppBarProfile   : AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout        = binding.drawerHomeLayout
        navigationView      = binding.navigationView
        bottomNavigation    = binding.bottomNavigation
        imgAppBarMenu       = binding.imgAppBarMenu
        navController       = findNavController(R.id.nav_host_fragment)
        imgAppBarProfile    = binding.imgAppBarProfile

        setupAppBar()
        setupNavigation()
    }

    private fun setupAppBar(){
        imgAppBarMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        imgAppBarProfile.load("https://rebasando.com/images/biblicas/el-nino-y-el-perrito.jpg"){
            placeholder(R.drawable.ic_google)
            size(50,50)
            transformations(CircleCropTransformation())
            scale(Scale.FILL)
            error(R.drawable.ic_google)
        }
    }

    private fun setupNavigation() {
        NavigationUI.setupWithNavController(navigationView, navController)
        bottomNavigation.setupWithNavController(navController)
        bottomNavigation.itemIconTintList = setColorTintBottomNavigation()

        navigationView.setNavigationItemSelectedListener { menuItem->
            when (menuItem.itemId) {
                R.id.navigation_home ->{
                    navController.navigate(R.id.navigation_home)
                    closeDrawer()
                }

                R.id.navigation_opcion1 ->{
                    navController.navigate(R.id.navigation_opcion1)
                    closeDrawer()
                }

                R.id.navigation_opcion2 ->{
                    navController.navigate(R.id.navigation_opcion2)
                    closeDrawer()
                }

                R.id.navigation_opcion3 ->{
                    navController.navigate(R.id.navigation_opcion3)
                    closeDrawer()
                }

                R.id.navigation_contact_us ->{
//                    val intent = Intent(this, ContactUsActivity::class.java)
//                    startActivity(intent)
                    closeDrawer()
                }

//                R.id.navigation_about_us ->{
//                    val intent = Intent(this, AboutUsActivity::class.java)
//                    startActivity(intent)
//                    closeDrawer()
//                }

                R.id.sign_out -> {
//                    val auth = FirebaseAuth.getInstance()
//                    auth.signOut()
//                    preferences.clear()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }

    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()
    }

    private fun closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
    }

}