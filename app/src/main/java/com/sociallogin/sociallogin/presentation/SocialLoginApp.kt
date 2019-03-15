package com.sociallogin.sociallogin.presentation

import android.app.Application
import com.sociallogin.sociallogin.BuildConfig
import com.sociallogin.sociallogin.domain.utils.NotLoggingTree
import timber.log.Timber

class SocialLoginApp: Application() {

    override fun onCreate() {
        super.onCreate()

        //init Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(NotLoggingTree())
        }
    }
}