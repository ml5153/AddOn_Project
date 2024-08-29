package com.devsudal.sdk.addon.connection.buzzvil

import android.app.Activity
import android.app.Application
import com.devsudal.sdk.addon.connection.AddOnInitListener

interface BuzzAddOnConnectListener {
    fun init(application: Application, initLitener: AddOnInitListener)
    fun setUserProfile(profile: BuzzProfile)
    fun loadAD(activity: Activity)
}