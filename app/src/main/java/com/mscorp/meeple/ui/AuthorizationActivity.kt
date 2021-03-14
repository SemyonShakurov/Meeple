package com.mscorp.meeple.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.EventLog
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import com.mscorp.meeple.R
import com.mscorp.meeple.api.Event
import com.mscorp.meeple.viewmodel.AuthorizationViewModel

class AuthorizationActivity : AppCompatActivity() {
    val viewModel = AuthorizationViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        val password = findViewById<EditText>(R.id.editTextPassword)
        val email = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val but = findViewById<Button>(R.id.btnSignIn)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        viewModel.loginResponse.observe(this, Observer {
            if (it is Event.Loading)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.INVISIBLE
            if (it is Event.Success)
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else if (it is Event.Failure) {
                Toast.makeText(this, "Some problems", Toast.LENGTH_SHORT).show()
            }
        })

        but.setOnClickListener {
            viewModel.login(email.text.toString(), password.text.toString())
        }
    }
}