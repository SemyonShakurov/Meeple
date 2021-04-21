package com.mscorp.meeple.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mscorp.meeple.R
import com.mscorp.meeple.model.User
import com.mscorp.meeple.ui.main.MenuActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /*val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra("user", User(1, "max@gmail.com", "maxgromash", "Максим \nГребенщиков", "123", null, null ,null ))
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)*/

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.mainFragmentContainer, StartFragment(), "tag")
                .commit()
        }
    }
}
