package com.devsudal.sdk.ph

import android.app.Application
import android.util.Log
import com.devsudal.sdk.addon.factory.AddOnFactory
import com.devsudal.sdk.addon.connection.buzzvil.BuzzProfile

object PointHomeSDK {

    const val NAME: String = "PointHomeSDK"

    /**
     * 초기화
     */
    fun initialized(application: Application) {
        Log.e(NAME, "$NAME -> initialized")

        // AddOn init
        AddOnFactory.initialized(application = application)
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