package com.sociallogin.sociallogin.domain.dagger

import com.sociallogin.sociallogin.presentation.google.dagger.MainModule
import com.sociallogin.sociallogin.presentation.google.view.GoogleActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [(ViewModelModule::class)])
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [(MainModule::class)])
    internal abstract fun contributeMainActivity(): GoogleActivity

}
