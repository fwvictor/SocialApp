package com.sociallogin.sociallogin.presentation.main.dagger

import android.app.Activity
import com.sociallogin.sociallogin.presentation.main.MainInteractor
import com.sociallogin.sociallogin.presentation.main.view.MainActivity
import com.sociallogin.sociallogin.presentation.utils.GoogleSignInUtils
import com.sociallogin.sociallogin.presentation.utils.TwitterSignInUtils
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun providesActivity(activity: MainActivity): Activity = activity

    @Provides
    fun providesGoogleSignInUtils(): GoogleSignInUtils = GoogleSignInUtils()

    @Provides
    fun providesTwitterSignInUtils(): TwitterSignInUtils = TwitterSignInUtils()

    @Provides
    fun providesMainInteractor(
        googleSignInUtils: GoogleSignInUtils,
        twitterSignInUtils: TwitterSignInUtils
    ) = MainInteractor(googleSignInUtils, twitterSignInUtils)

}
