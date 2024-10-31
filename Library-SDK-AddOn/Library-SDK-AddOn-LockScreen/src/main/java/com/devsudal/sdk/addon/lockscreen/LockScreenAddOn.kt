package com.devsudal.sdk.addon.lockscreen

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.decaffeine.common.sdk.log.LogTracer
import com.devsudal.sdk.addon.connection.AddOnInitListener
import com.devsudal.sdk.addon.connection.lockscreen.LockScreenAddOnConnectListener

class LockScreenAddOn : AppCompatActivity(), LockScreenAddOnConnectListener {

    companion object {
        const val NAME = "LockScreenAddOn"
    }


    override fun init(
        application: Application,
        initLitener: AddOnInitListener
    ) {
        LogTracer.i { "$NAME -> init" }

        try {
            initLitener.onSuccess()
        } catch (e: Exception) {
            initLitener.onFailure()
        }
    }
}

