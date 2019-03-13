package com.sociallogin.sociallogin.presentation.main

import android.app.Activity
import com.sociallogin.sociallogin.presentation.utils.GoogleSignInUtils
import com.sociallogin.sociallogin.presentation.utils.TwitterSignInUtils
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MainInteractor @Inject constructor(private val googleSignInUtils: GoogleSignInUtils,
                                         private val twitterSignInUtils: TwitterSignInUtils) {

    fun initGoogleLogin(activity: Activity) = googleSignInUtils.init(activity)

    fun isGoogleUserLoggedIn(): Single<Boolean> = googleSignInUtils.isUserLoggedIn()

    fun loginWithGoogle() : Completable = googleSignInUtils.performLogin()

    fun handleLoginResponseWithGoogle(data: Any?): Completable = googleSignInUtils.handleLoginResponse(data)

    fun logoutWithGoogle(): Completable = googleSignInUtils.logout()

    fun initTwitterLogin(activity: Activity) = twitterSignInUtils.init(activity)

    fun isTwitterUserLoggedIn(): Single<Boolean> = twitterSignInUtils.isUserLoggedIn()

    fun handleLoginResponseWithTwitter(data: Any?): Completable = twitterSignInUtils.handleLoginResponse(data)

    fun logoutWithTwitter(): Completable = twitterSignInUtils.logout()
}