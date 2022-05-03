package com.mscorp.meeple.features.core_feature.login

import android.os.Bundle
import com.mscorp.meeple.R
import com.mscorp.meeple.core.MeepleActivity

internal class LoginActivity : MeepleActivity() {
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
