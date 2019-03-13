package com.sociallogin.sociallogin.presentation.main.view

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.sociallogin.sociallogin.R
import com.sociallogin.sociallogin.domain.dagger.Injectable
import com.sociallogin.sociallogin.presentation.main.MainViewModel
import com.sociallogin.sociallogin.presentation.model.Status
import com.sociallogin.sociallogin.presentation.utils.GoogleSignInUtils
import com.sociallogin.sociallogin.presentation.utils.extension.observe
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.Result
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel


    /* Activity methods */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpUi()
        setUpViewModel()

        viewModel.initGoogleLogin(this)
        viewModel.isUserLoggedInWithGoogle()

        viewModel.initTwitterLogin(this)
        viewModel.isUserLoggedInWithTwitter()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GoogleSignInUtils.GOOGLE_REQ_CODE) {
            viewModel.handleLoginResponseWithGoogle(data)
        }
    }

    /* setUp methods */

    private fun setUpUi() {
        btnTwitterLogin.callback = object : Callback<TwitterSession>() {

            override fun success(result: Result<TwitterSession>?) {
                viewModel.handleLoginResponseWithTwitter(result?.data)
            }

            override fun failure(exception: TwitterException) {
                viewModel.handleLoginErrorWithTwitter(exception)
            }
        }

        btnTwitterLogout.setOnClickListener {
            viewModel.logoutWithTwitter()
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)


        viewModel.isGoogleLoggedIn.observe(this) { resource ->
            resource ?: return@observe

            progressBar.visibility = if (resource.status == Status.LOADING) {
                View.VISIBLE
            } else {
                View.GONE
            }

            if (resource.status == Status.SUCCESS) {
                val msg: String
                if (resource.data!!) {
                    showLogoutGoogleModeButton()
                    msg = getString(com.sociallogin.sociallogin.R.string.login_google_successfully)
                } else {
                    showLoginGoogleModeButton()
                    msg = getString(com.sociallogin.sociallogin.R.string.logout_google_successfully)
                }

                Snackbar.make(
                    findViewById(android.R.id.content), msg,
                    Snackbar.LENGTH_LONG
                ).show()

            } else if (resource.status == Status.FAILED) {
                showToastError(resource.msg)
            }
        }

        viewModel.isTwitterLoggedIn.observe(this) { resource ->
            resource ?: return@observe

            progressBar.visibility = if (resource.status == Status.LOADING) {
                View.VISIBLE
            } else {
                View.GONE
            }

            if (resource.status == Status.SUCCESS) {
                val msg: String
                if (resource.data!!) {
                    showLogoutTwitterButton()
                    msg = getString(com.sociallogin.sociallogin.R.string.login_twitter_successfully)
                } else {
                    showLoginTwitterButton()
                    msg = getString(com.sociallogin.sociallogin.R.string.logout_twitter_successfully)
                }

                Snackbar.make(
                    findViewById(android.R.id.content), msg,
                    Snackbar.LENGTH_LONG
                ).show()

            } else if (resource.status == Status.FAILED) {
                showToastError(resource.msg)
            }
        }
    }

    /* UI methods */

    private fun showLogoutGoogleModeButton() {
        btnGoogleLogin.setText(R.string.logout_with_google)
        btnGoogleLogin.setOnClickListener {
            viewModel.logoutWithGoogle()
        }
    }

    private fun showLoginGoogleModeButton() {
        btnGoogleLogin.setText(R.string.sign_in_with_google)
        btnGoogleLogin.setOnClickListener {
            viewModel.loginWithGoogle()
        }
    }

    private fun showLogoutTwitterButton() {
        btnTwitterLogin.visibility = View.INVISIBLE
        btnTwitterLogout.visibility = View.VISIBLE
    }

    private fun showLoginTwitterButton() {
        btnTwitterLogin.visibility = View.VISIBLE
        btnTwitterLogout.visibility = View.INVISIBLE
    }

    private fun showToastError(errorMsg: String?) {
        Snackbar.make(
            findViewById(android.R.id.content), getString(R.string.error, errorMsg)
                ?: getString(R.string.common_error),
            Snackbar.LENGTH_LONG
        ).show()
    }
}