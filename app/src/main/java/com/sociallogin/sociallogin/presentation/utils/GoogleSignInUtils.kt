package com.sociallogin.sociallogin.presentation.utils

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.Exception
import javax.inject.Inject



class GoogleSignInUtils @Inject constructor() : SignInUtils {

    companion object {
        const val GOOGLE_REQ_CODE = 100
    }

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth : FirebaseAuth
    private lateinit var activity: Activity

    override fun init(activity: Activity) {
        this.activity = activity
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity, gso)

        auth = FirebaseAuth.getInstance()
    }

    override fun performLogin(): Completable {
        return Completable.create { emitter ->
            val signInIntent = googleSignInClient.signInIntent
            try {
                startActivityForResult(activity, signInIntent, GOOGLE_REQ_CODE, null)

                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    override fun handleLoginResponse(data: Any?): Completable {
        return Completable.create { emitter ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(data as Intent)
            try {
                task.getResult(ApiException::class.java)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    override fun isUserLoggedIn(): Single<Boolean> {
        return Single.create { emitter ->
            val currentUser = auth.currentUser
            emitter.onSuccess(currentUser != null)
        }
    }

    override fun logout(): Completable {
        return Completable.create { emitter ->
            try {
                googleSignInClient.signOut()
                FirebaseAuth.getInstance().signOut()
                emitter.onComplete()
            } catch (e: ApiException) {
                emitter.onError(e)
            }
        }
    }
}