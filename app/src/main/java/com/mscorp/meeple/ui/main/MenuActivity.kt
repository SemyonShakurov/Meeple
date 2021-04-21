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
import com.mscorp.meeple.model.UserFriends

class MenuActivity : AppCompatActivity() {

    var user: User? = null
    var userFriends: UserFriends? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        user = intent.getSerializableExtra("user") as User
        userFriends = intent.getSerializableExtra("friends") as UserFriends

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.mobile_navigation)
        val navArgumentUser = NavArgument.Builder().setDefaultValue(user).build()
        val navArgumentUserFriends = NavArgument.Builder().setDefaultValue(userFriends).build()
        graph.addArgument("user", navArgumentUser)
        graph.addArgument("friends", navArgumentUserFriends)

        navController.graph = graph
        navView.setupWithNavController(navController)
    }
}