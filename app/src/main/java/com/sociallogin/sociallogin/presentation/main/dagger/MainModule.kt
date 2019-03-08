package com.sociallogin.sociallogin.presentation.main.dagger

import android.app.Activity
import com.sociallogin.sociallogin.presentation.main.MainInteractor
import com.sociallogin.sociallogin.presentation.main.view.MainActivity
import com.sociallogin.sociallogin.presentation.utils.GoogleSignInUtils
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun providesActivity(activity: MainActivity): Activity = activity

    @Provides
    fun providesGoogleSignInUtils(): GoogleSignInUtils = GoogleSignInUtils()

    @Provides
    fun providesMainInteractor(
        googleSignInUtils: GoogleSignInUtils
    ) = MainInteractor(googleSignInUtils)

}
