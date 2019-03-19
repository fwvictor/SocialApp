package com.sociallogin.sociallogin.presentation

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.sociallogin.sociallogin.BuildConfig
import com.sociallogin.sociallogin.domain.utils.NotLoggingTree
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import timber.log.Timber

class SocialLoginApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // Init firebase
        FirebaseApp.initializeApp(this)

        // Twitter
        val config = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(
                TwitterAuthConfig(
                    BuildConfig.TWITTER_CONSUMER_KEY,
                    BuildConfig.TWITTER_CONSUMER_SECRET
                )
            )
            .debug(BuildConfig.DEBUG)
            .build()
        Twitter.initialize(config)

        //init Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(NotLoggingTree())
        }
    }
}