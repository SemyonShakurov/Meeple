package com.mscorp.meeple.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavArgument
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mscorp.meeple.R
import com.mscorp.meeple.model.User

class  MenuActivity : AppCompatActivity() {

    companion object{
        lateinit var user: User
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        user = intent.getSerializableExtra("user") as User


        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)


        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.mobile_navigation)
        val navArgument = NavArgument.Builder().setDefaultValue(user).build()
        graph.addArgument("user",navArgument)

        navController.graph=graph

        navView.setupWithNavController(navController)

    }
}