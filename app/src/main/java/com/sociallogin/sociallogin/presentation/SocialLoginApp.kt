package com.sociallogin.sociallogin.presentation

import android.app.Activity
import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.sociallogin.sociallogin.BuildConfig
import com.sociallogin.sociallogin.domain.dagger.AppInjector
import com.sociallogin.sociallogin.domain.utils.NotLoggingTree
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class SocialLoginApp: Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

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

        // Dagger 2 injection
        AppInjector.init(this)

        //init Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(NotLoggingTree())
        }
    }
}