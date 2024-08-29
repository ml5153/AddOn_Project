package com.devsudal.sdk.addon.connection.lockscreen

import android.app.Application
import com.devsudal.sdk.addon.connection.AddOnInitListener

interface LockScreenAddOnConnectListener {
    fun init(application: Application, initLitener: AddOnInitListener)
}