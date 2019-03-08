package com.sociallogin.sociallogin.domain.dagger

import android.app.Application
import com.sociallogin.sociallogin.presentation.SocialLoginApp
import dagger.Module
import dagger.Binds



@Module
abstract class AppModule {

    @Binds
    abstract fun bindApplication(app: SocialLoginApp): Application

}