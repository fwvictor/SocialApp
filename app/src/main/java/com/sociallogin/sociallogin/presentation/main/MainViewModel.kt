package com.sociallogin.sociallogin.presentation.main

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sociallogin.sociallogin.presentation.model.Resource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
                     private val mainInteractor: MainInteractor): ViewModel() {

    val disposables = CompositeDisposable()

    val isGoogleLoggedIn = MutableLiveData<Resource<Boolean>>()

    val isTwitterLoggedIn = MutableLiveData<Resource<Boolean>>()

    fun initGoogleLogin(activity: Activity) {
        mainInteractor.initGoogleLogin(activity)
    }

    fun initTwitterLogin(activity: Activity) {
        mainInteractor.initTwitterLogin(activity)
    }

    fun isUserLoggedInWithGoogle() {
        isGoogleLoggedIn.value = Resource.loading()

        disposables += mainInteractor.isGoogleUserLoggedIn()
            .subscribeBy(
                onSuccess = {
                    Timber.d("isUserLoggedInWithGoogle.onComplete")
                    isGoogleLoggedIn.value = Resource.success(it)
                },
                onError = {
                    Timber.e("isUserLoggedInWithGoogle.onError ${it.message}")
                    isGoogleLoggedIn.value = Resource.error(it)
                }
            )
    }

    fun isUserLoggedInWithTwitter() {
        isTwitterLoggedIn.value = Resource.loading()

        disposables += mainInteractor.isTwitterUserLoggedIn()
            .subscribeBy(
                onSuccess = {
                    Timber.d("isUserLoggedInWithTwitter.onComplete")
                    isTwitterLoggedIn.value = Resource.success(it)
                },
                onError = {
                    Timber.e("isUserLoggedInWithTwitter.onError ${it.message}")
                    isTwitterLoggedIn.value = Resource.error(it)
                }
            )
    }

    fun loginWithGoogle() {
        isGoogleLoggedIn.value = Resource.loading()

        disposables += mainInteractor.loginWithGoogle()
            .subscribeBy(
                onComplete = {
                    Timber.d("loginWithGoogle.onComplete")
                },
                onError = {
                    Timber.e("loginWithGoogle.onError ${it.message}")
                    isGoogleLoggedIn.value = Resource.error(it)
                }
            )
    }

    fun handleLoginResponseWithGoogle(data: Any?) {
        disposables += mainInteractor.handleLoginResponseWithGoogle(data)
            .subscribeBy(
                onComplete = {
                    Timber.d("handleLoginResponseWithGoogle.onComplete")
                    isGoogleLoggedIn.value = Resource.success(true)
                },
                onError = {
                    Timber.e("handleLoginResponseWithGoogle.onError ${it.message}")
                    isGoogleLoggedIn.value = Resource.error(it)
                }
            )
    }

    fun handleLoginResponseWithTwitter(data: Any?) {
        disposables += mainInteractor.handleLoginResponseWithTwitter(data)
            .subscribeBy(
                onComplete = {
                    Timber.d("handleLoginResponseWithTwitter.onComplete")
                    isTwitterLoggedIn.value = Resource.success(true)
                },
                onError = {
                    Timber.e("handleLoginResponseWithTwitter.onError ${it.message}")
                    isTwitterLoggedIn.value = Resource.error(it)
                }
            )
    }

    fun handleLoginErrorWithTwitter(throwable: Throwable?) {
        throwable?.let {
            Timber.e("handleLoginErrorWithTwitter.onError ${it.message}")
            isTwitterLoggedIn.value = Resource.error(it)
        }
    }

    fun logoutWithGoogle() {
        disposables += mainInteractor.logoutWithGoogle()
            .subscribeBy(
                onComplete = {
                    Timber.d("logoutWithGoogle.onComplete")
                    isGoogleLoggedIn.value = Resource.success(false)
                },
                onError = {
                    Timber.e("logoutWithGoogle.onError ${it.message}")
                    isGoogleLoggedIn.value = Resource.error(it)
                }
            )
    }

    fun logoutWithTwitter() {
        disposables += mainInteractor.logoutWithTwitter()
            .subscribeBy(
                onComplete = {
                    Timber.d("logoutWithTwitter.onComplete")
                    isTwitterLoggedIn.value = Resource.success(false)
                },
                onError = {
                    Timber.e("logoutWithTwitter.onError ${it.message}")
                    isTwitterLoggedIn.value = Resource.error(it)
                }
            )
    }

    override fun onCleared() {
        disposables.clear()
    }
}