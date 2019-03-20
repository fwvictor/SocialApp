package com.sociallogin.sociallogin.presentation.google


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.sociallogin.sociallogin.R
import kotlinx.android.synthetic.main.activity_google.*
import timber.log.Timber
import java.lang.Exception

class GoogleLoginActivity : AppCompatActivity() {

    companion object {
        private const val GOOGLE_REQ_CODE = 100
    }

    private lateinit var googleSignInClient: GoogleSignInClient

    /* Activity methods */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google)

        initGoogleConfig()
        if (isUserLoggedIn()) {
            showLogoutGoogleModeButton()
        } else {
            showLoginGoogleModeButton()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_REQ_CODE) {
            handleLoginResponse(data)
        }
    }

    /* UI methods */

    private fun showLogoutGoogleModeButton() {
        btnGoogleLogin.setText(R.string.logout_with_google)
        btnGoogleLogin.setOnClickListener {
            logout()
        }
    }

    private fun showLoginGoogleModeButton() {
        btnGoogleLogin.setText(R.string.sign_in_with_google)
        btnGoogleLogin.setOnClickListener {
            performLogin()
        }
    }

    private fun showLoginOk() {
        Snackbar.make(
            findViewById(android.R.id.content), getString(R.string.sign_in_successfully),
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

    /* Google methods */

    private fun performLogin() {
        val signInIntent = googleSignInClient.signInIntent
        try {
            startActivityForResult(signInIntent, GOOGLE_REQ_CODE)
        } catch (e: Exception) {
            Timber.e("performLogin error: ${e.message}")
            showError(e.message)
        }
    }

    private fun isUserLoggedIn() : Boolean {
        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)
        return  googleSignInAccount != null
    }

    private fun handleLoginResponse(data: Any?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data as Intent)
        try {
            task.getResult(ApiException::class.java)
            Timber.d("handleLoginResponse ok")
            showLoginOk()
            showLogoutGoogleModeButton()
        } catch (e: Exception) {
            Timber.e("handleLoginResponse error: ${e.message}")
            showError(e.message)
        }
    }

    private fun initGoogleConfig() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun logout() {
        try {
            googleSignInClient.signOut()
            Timber.d("logout ok")
            showLogoutOk()
            showLoginGoogleModeButton()
        } catch (e: Exception) {
            Timber.e("logout error: ${e.message}")
            showError(e.message)
        }
    }

}