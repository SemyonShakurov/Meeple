package com.mscorp.meeple.ui

.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mscorp.meeple.R
import com.mscorp.meeple.ui.main.MenuActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.mainFragmentContainer, StartFragment(), "tag")
                .commit()
        }
    }
}
