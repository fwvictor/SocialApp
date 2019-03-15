package com.sociallogin.sociallogin.presentation.google.dagger

import android.app.Activity
import com.sociallogin.sociallogin.presentation.google.MainInteractor
import com.sociallogin.sociallogin.presentation.google.view.GoogleActivity
import com.sociallogin.sociallogin.presentation.utils.GoogleSignInUtils
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun providesActivity(activity: GoogleActivity): Activity = activity

    @Provides
    fun providesGoogleSignInUtils(): GoogleSignInUtils = GoogleSignInUtils()

    @Provides
    fun providesMainInteractor(
        googleSignInUtils: GoogleSignInUtils
    ) = MainInteractor(googleSignInUtils)

}
