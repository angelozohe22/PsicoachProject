package com.example.psicoachproject.ui.modules.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.psicoachproject.R
import com.example.psicoachproject.common.utils.setColorTintBottomNavigation
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.core.aplication.preferences
import com.example.psicoachproject.data.remote.source.auth.AuthRemoteDataSourceImpl
import com.example.psicoachproject.data.remote.source.home.HomeRemoteDataSourceImpl
import com.example.psicoachproject.databinding.ActivityHomeBinding
import com.example.psicoachproject.domain.repository.auth.AuthRepositoryImpl
import com.example.psicoachproject.domain.repository.home.HomeRepositoryImpl
import com.example.psicoachproject.ui.modules.home.client.activities.viewmodel.HomeViewModel
import com.example.psicoachproject.ui.modules.home.client.activities.viewmodel.HomeViewModelFactory
import com.example.psicoachproject.ui.modules.main.activities.MainActivity
import com.example.psicoachproject.ui.modules.main.activities.viewmodel.AuthViewModel
import com.example.psicoachproject.ui.modules.main.activities.viewmodel.AuthViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    lateinit var binding            : ActivityHomeBinding

    private lateinit var drawerLayout       : DrawerLayout
    private lateinit var navigationView     : NavigationView
    private lateinit var bottomNavigation   : BottomNavigationView
    private lateinit var navController      : NavController
    private lateinit var imgAppBarMenu      : AppCompatImageButton
    private lateinit var imgAppBarProfile   : AppCompatImageView

    private val viewModelAuth by viewModels<AuthViewModel>{
        AuthViewModelFactory(
            AuthRepositoryImpl(
                AuthRemoteDataSourceImpl()
            )
        )
    }

    val viewModelCita by viewModels<HomeViewModel>{
        HomeViewModelFactory(
                HomeRepositoryImpl(
                        remote = HomeRemoteDataSourceImpl()
                )
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout        = binding.drawerHomeLayout
        navigationView      = binding.navigationView
        bottomNavigation    = binding.bottomNavigation
        imgAppBarMenu       = binding.imgAppBarMenu

        imgAppBarProfile    = binding.imgAppBarProfile

        setupAppBar()
        setupNavigation()
    }

    override fun onResume() {
        super.onResume()
        if (preferences.roleId == 3){
            navigationView.setCheckedItem(R.id.navigation_home)
            bottomNavigation.selectedItemId = R.id.navigation_home
        }else{
            navigationView.setCheckedItem(R.id.nav_pending_psico)
            bottomNavigation.selectedItemId = R.id.nav_pending_psico
        }
    }

    private fun setupAppBar(){
        imgAppBarMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        imgAppBarProfile.load("https://scontent.flim17-1.fna.fbcdn.net/v/t1.6435-9/128375677_3706009076109912_302792313255666766_n.jpg?_nc_cat=108&ccb=1-3&_nc_sid=09cbfe&_nc_eui2=AeHUz-XltFejvfcZ3CW5xmlmQvi8Fooab4dC-LwWihpvh3tEL9lAPHSEYausf1mJDOcqPFI6g-KdvnaN6crcbucX&_nc_ohc=_QKpwt--5LsAX-9ne2B&_nc_ht=scontent.flim17-1.fna&oh=a1fca72f744a3cf94b2a530c65738e10&oe=60E06667"){
            placeholder(R.drawable.placeholder)
            size(50,50)
            transformations(CircleCropTransformation())
            scale(Scale.FILL)
            error(R.drawable.placeholder)
        }
    }

    private fun setupNavigation() {
        navController       = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(navigationView, navController)
        bottomNavigation.setupWithNavController(navController)
        bottomNavigation.itemIconTintList = setColorTintBottomNavigation()

        binding.navigationView.menu.clear()
        bottomNavigation.menu.clear()

        println("-->> Rol: ${preferences.roleId} ")

        if (preferences.roleId == 3){
            navController.setGraph(R.navigation.home_client_nav_graph)
            binding.navigationView.inflateMenu(R.menu.drawer_home_menu)
            bottomNavigation.inflateMenu(R.menu.btn_nav_menu)
            bottomNavigation.selectedItemId = R.id.navigation_home
        }else {
            navController.setGraph(R.navigation.home_psisec_nav_graph)
            binding.navigationView.inflateMenu(R.menu.drawer_home_psico_menu)
            bottomNavigation.inflateMenu(R.menu.btn_nav_psico_menu)
            bottomNavigation.selectedItemId = R.id.nav_pending_psico
        }

        navigationView.setNavigationItemSelectedListener { menuItem->
            when (menuItem.itemId) {
                R.id.navigation_home ->{
                    navController.navigate(R.id.navigation_home)
                    closeDrawer()
                }

                R.id.navigation_calendar ->{
                    navController.navigate(R.id.navigation_calendar)
                    closeDrawer()
                }

                R.id.navigation_cita ->{
                    navController.navigate(R.id.navigation_cita)
                    closeDrawer()
                }

                //Rol != 3
                R.id.nav_pending_psico ->{
                    navController.navigate(R.id.nav_pending_psico)
                    closeDrawer()
                }

                R.id.nav_calendar_psico ->{
                    navController.navigate(R.id.nav_calendar_psico)
                    closeDrawer()
                }

                R.id.sign_out -> {
                    logOut()
                }
            }
            true
        }

    }

    private fun logOut(){
        viewModelAuth.logOut().observe(this, Observer {
            it?.let { result ->
                when (result) {
                    is Resource.Loading -> {
                        println("--->> Cargando...")
                    }
                    is Resource.Success -> {
                        preferences.clear()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is Resource.Failure -> {
                        println("--->> Fallo")
                    }
                }
            }
        })
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