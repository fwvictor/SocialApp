package com.sociallogin.sociallogin.presentation.utils

import android.app.Activity
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.TwitterAuthProvider
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.Exception
import java.lang.RuntimeException
import javax.inject.Inject
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterSession


class TwitterSignInUtils @Inject constructor() : SignInUtils {

    companion object {
        const val TWITTER_REQ_CODE = 140
    }

    private lateinit var auth: FirebaseAuth
    lateinit var activity: Activity

    override fun init(activity: Activity) {
        auth = FirebaseAuth.getInstance()
        this.activity = activity
    }

    override fun isUserLoggedIn(): Single<Boolean> {
        // todo
        return Single.just(true)
    }

    override fun performLogin(): Completable {
        // Not being used
        return Completable.complete()
    }

    override fun handleLoginResponse(data: Any?): Completable {
        return Completable.create { emitter ->
            try {

                val session = data as TwitterSession
                val credential = TwitterAuthProvider.getCredential(
                    session.authToken.token,
                    session.authToken.secret
                )

                auth.signInWithCredential(credential)
                    .addOnCompleteListener(activity) { task ->
                        if (task.isSuccessful && auth.currentUser != null) {
                            emitter.onComplete()
                        } else {
                            emitter.onError(task.exception ?: RuntimeException("Login not working on twitter"))
                        }
                    }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    override fun logout(): Completable {
        return Completable.complete()
        // todo
    }

}