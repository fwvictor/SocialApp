package com.sociallogin.sociallogin.domain.dagger

import com.sociallogin.sociallogin.presentation.SocialLoginApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class)
)
abstract class AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: SocialLoginApp): Builder

        fun build(): AppComponent
    }

    abstract fun inject(application: SocialLoginApp)
}