package com.sociallogin.sociallogin.domain.utils

import timber.log.Timber

class NotLoggingTree :  Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        // Avoid Logging in any case
    }
}