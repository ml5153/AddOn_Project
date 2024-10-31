package com.devsudal.sdk.ph

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.decaffeine.common.sdk.log.LogTracer
import com.devsudal.sdk.addon.factory.AddOnFactory
import com.devsudal.sdk.ph.ui.PointHomeMainActivity

object PointHomeSDK {

    const val NAME: String = "PointHomeSDK"

    /**
     * 초기화
     */
    fun initialized(application: Application) {
        LogTracer.i { "$NAME -> initialized" }

        // AddOn init
        AddOnFactory.initialize(application = application)
    }


    fun open(activity:Activity) {
        val intent:Intent = Intent(activity, PointHomeMainActivity::class.java)
        activity.startActivity(intent)
    }
}