package com.jaakrecog.fingerprint.tools

import android.util.Log
import com.jaakit.fingerscapture.BuildConfig
import io.sentry.Sentry


class LogInformationData: LogDataInterface {

    override fun printoServer(tag: String,message: String) {
        if (BuildConfig.DEBUG) {
            Sentry.captureMessage("$tag : $message")

        }else{

            Log.i("$tag","$message")

        }
    }

    }