package com.sociallogin.sociallogin.presentation.utils

import android.app.Activity
import io.reactivex.Completable
import io.reactivex.Single

interface SignInUtils {

    fun init(activity: Activity)

    fun isUserLoggedIn(): Single<Boolean>

    fun performLogin(): Completable

    fun handleLoginResponse(data: Any?) : Completable

    fun logout(): Completable
}