package com.sociallogin.sociallogin.presentation.loginselector

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sociallogin.sociallogin.R
import com.sociallogin.sociallogin.presentation.google.GoogleLoginActivity
import com.sociallogin.sociallogin.presentation.twitter.TwitterLoginActivity
import kotlinx.android.synthetic.main.activity_login_selector.*

class LoginSelectorActivity: AppCompatActivity() {

    /* Activity methods */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_selector)

        btnOpenGoogle.setOnClickListener {
            val intent = Intent(this, GoogleLoginActivity::class.java)
            startActivity(intent)
        }

        btnOpenTwitter.setOnClickListener {
            val intent = Intent(this, TwitterLoginActivity::class.java)
            startActivity(intent)
        }
    }

}