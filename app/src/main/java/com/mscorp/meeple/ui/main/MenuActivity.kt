package com.mscorp.meeple.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavArgument
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mscorp.meeple.R
import com.mscorp.meeple.model.BoardGames
import com.mscorp.meeple.model.User
import com.mscorp.meeple.model.UserFriends

class MenuActivity : AppCompatActivity() {

    var user: User? = null
    private var userFriends: UserFriends? = null
    var games: BoardGames? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        user = intent.getSerializableExtra("user") as User
        userFriends = intent.getSerializableExtra("friends") as UserFriends
        games = intent.getSerializableExtra("games") as BoardGames

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.mobile_navigation)
        val navArgumentUser = NavArgument.Builder().setDefaultValue(user).build()
        val navArgumentUserFriends = NavArgument.Builder().setDefaultValue(userFriends).build()
        val navArgumentUserGames = NavArgument.Builder().setDefaultValue(games).build()
        graph.addArgument("user", navArgumentUser)
        graph.addArgument("friends", navArgumentUserFriends)
        graph.addArgument("games", navArgumentUserGames)

        navController.graph = graph
        navView.setupWithNavController(navController)
    }
}