package com.sociallogin.sociallogin.presentation.twitter

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.sociallogin.sociallogin.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.TwitterAuthProvider
import kotlinx.android.synthetic.main.activity_twitter.*
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.Result
import timber.log.Timber
import java.lang.Exception

class TwitterLoginActivity : AppCompatActivity() {

    companion object {
        const val TWITTER_REQ_CODE = 140
    }

    private lateinit var auth: FirebaseAuth

    /* Activity methods */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter)

        setUpUi()
        initTwitterConfig()
        if (isUserLoggedIn()) {
            showLogoutTwitterModeButton()
        } else {
            showLoginTwitterModeButton()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TWITTER_REQ_CODE){
            btnTwitterLogin.onActivityResult(requestCode, resultCode, data)
        }
    }

    /* UI methods */

    private fun setUpUi() {
        btnTwitterLogin.callback = object : Callback<TwitterSession>() {

            override fun success(result: Result<TwitterSession>?) {
                handleLoginResponse(result?.data)
            }

            override fun failure(exception: TwitterException) {
                Timber.e("login error: ${exception.message}")
                showError(exception.message)
            }
        }

        btnTwitterLogout.setOnClickListener {
            logout()
        }
    }

    private fun showLogoutTwitterModeButton() {
        btnTwitterLogin.visibility = View.INVISIBLE
        btnTwitterLogout.visibility = View.VISIBLE
    }

    private fun showLoginTwitterModeButton() {
        btnTwitterLogin.visibility = View.VISIBLE
        btnTwitterLogout.visibility = View.INVISIBLE
    }

    private fun showLoginOk() {
        Snackbar.make(
            findViewById(android.R.id.content), getString(R.string.sign_in_with_twitter),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun showLogoutOk() {
        Snackbar.make(
            findViewById(android.R.id.content), getString(R.string.logout_successfully),
            Snackbar.LENGTH_LONG
        ).show()
    }


    private fun showError(msg: String?) {
        Snackbar.make(
            findViewById(android.R.id.content), getString(R.string.error, msg)
                ?: getString(R.string.common_error),
            Snackbar.LENGTH_LONG
        ).show()
    }

    /* Twitter methods */

    private fun initTwitterConfig() {
        auth = FirebaseAuth.getInstance()
    }

    private fun isUserLoggedIn() : Boolean = auth.currentUser != null


    private fun handleLoginResponse(data: Any?) {
        val session = data as TwitterSession
        val credential = TwitterAuthProvider.getCredential(
            session.authToken.token,
            session.authToken.secret
        )

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && auth.currentUser != null) {
                    showLoginOk()
                    Timber.d("login ok")
                    showLoginOk()
                    showLogoutTwitterModeButton()
                } else {
                    Timber.e("login error: ${task.exception}")
                    showError(task.exception.toString())
                }
            }
    }

    private fun logout() {
        try {
            FirebaseAuth.getInstance().signOut()
            TwitterCore.getInstance().sessionManager.clearActiveSession()
            Timber.d("logout ok")
            showLogoutOk()
            showLoginTwitterModeButton()
        } catch (e: Exception) {
            Timber.e("logout error: ${e.message}")
            showError(e.message)
        }
    }

}