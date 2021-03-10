package com.mscorp.meeple.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mscorp.meeple.R
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        findViewById<Button>(R.id.btn_registr).setOnClickListener {
            registration()
        }
    }

    private fun registration() {
        val passwordEncoder: PasswordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}