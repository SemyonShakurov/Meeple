package com.mscorp.meeple.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mscorp.meeple.R

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        findViewById<Button>(R.id.button3).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            registration()
        }
    }

    private fun registration() {
        /*  val passwordEncoder: PasswordEncoder =
              PasswordEncoderFactories.createDelegatingPasswordEncoder()
   */
    }
}