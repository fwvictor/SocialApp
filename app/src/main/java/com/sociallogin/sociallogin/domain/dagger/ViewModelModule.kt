package com.sociallogin.sociallogin.domain.dagger

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sociallogin.sociallogin.presentation.common.viewmodel.ViewModelFactory
import com.sociallogin.sociallogin.presentation.google.GoogleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GoogleViewModel::class)
    abstract fun bindMainViewModel(viewModel: GoogleViewModel): ViewModel

}