package com.devsudal.sdk.ph

import android.app.Application
import com.devsudal.sdk.addon.connection.buzzvil.BuzzProfile
import com.devsudal.sdk.addon.factory.AddOnFactory
import com.devsudal.sdk.log.LogTracer
import com.devsudal.sdk.log.LoggerSettings

object PointHomeSDK {

    const val NAME: String = "PointHomeSDK"

    /**
     * 초기화
     */
    fun initialized(application: Application) {
        LoggerSettings.retrieveLog(application = application)

        LogTracer.i { "$NAME -> initialized" }

        // AddOn init
        AddOnFactory.initialize(application = application)
        AddOnFactory.Buzzvil.setUserProfile(
            BuzzProfile(
                userId = "asdefq22",
                gender = BuzzProfile.Gender.MALE,
                birth = 1979
            )
        )
//        AddOnFactory.Buzzvil.load(activity = application.applicationContext as Activity)
    }
}