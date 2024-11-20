package com.mr.anonym.it_courses.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mr.anonym.it_courses.R
import com.mr.anonym.it_courses.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
    }

    private fun initializeViews() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.welcome_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController
    }
}