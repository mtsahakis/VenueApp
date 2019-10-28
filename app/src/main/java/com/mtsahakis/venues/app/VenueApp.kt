package com.mtsahakis.venues.app

import android.app.Application
import android.util.Log
import com.mtsahakis.venues.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree


class VenueApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // set up timber
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(object : Timber.Tree() {

                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                        return
                    }

                    when (priority) {
                        Log.WARN -> Log.w(tag, message)
                        Log.ERROR -> if (t == null) {
                            Log.e(tag, message)
                        } else {
                            Log.e(tag, message, t)
                        }
                        else -> Log.wtf(tag, message)
                    }
                }
            })
        }
    }
}