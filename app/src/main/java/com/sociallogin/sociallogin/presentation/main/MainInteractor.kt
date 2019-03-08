package com.sociallogin.sociallogin.presentation.main

import android.app.Activity
import com.sociallogin.sociallogin.presentation.utils.GoogleSignInUtils
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MainInteractor @Inject constructor(private val googleSignInUtils: GoogleSignInUtils) {

    fun initGoogleLogin(activity: Activity) = googleSignInUtils.init(activity)

    fun isGoogleUserLoggedIn(): Single<Boolean> = googleSignInUtils.isUserLoggedIn()

    fun loginWithGoogle() : Completable = googleSignInUtils.performLogin()

    fun handleLoginResponseWithGoogle(data: Any?): Completable = googleSignInUtils.handleLoginResponse(data)

    fun logoutWithGoogle(): Completable = googleSignInUtils.logout()

}