package com.example.cineste

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        navView.setupWithNavController(navController)

        if(intent?.action == Intent.ACTION_SEND && intent?.type == "text/plain")
        {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                val bundle = bundleOf("search" to it)
                navView.selectedItemId= R.id.searchFragment
                navController.navigate(R.id.searchFragment,bundle)
            }
        }

        Intent(this, LatestMovieService::class.java).also {
            startForegroundService(it)
            return
        }
    }
}