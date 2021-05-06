package com.mscorp.meeple.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mscorp.meeple.R

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
