package com.devsudal.sdk.addon.factory

import android.app.Activity
import android.app.Application
import android.util.Log
import com.devsudal.sdk.addon.factory.strategy.buzzvil.BuzzProfile
import com.devsudal.sdk.addon.factory.strategy.buzzvil.BuzzvilStrategy
import com.devsudal.sdk.addon.factory.strategy.lockscreen.LockScreenStrategy

object AddOnFactory {

    const val NAME: String = "AddOnSDK"

    private val buzzvilStrategy by lazy {
        BuzzvilStrategy()
    }

    private val lockscreenStrategy by lazy {
        LockScreenStrategy()
    }

    fun initialized(application: Application) {
        Log.e(NAME, "$NAME -> initialized")
        AddOnFactoryHelper.registerStrategy(buzzvilStrategy)
        AddOnFactoryHelper.registerStrategy(lockscreenStrategy)
        AddOnFactoryHelper.initializeAll(application = application)
    }


    object Buzzvil {
        private const val ADDON_NAME = "${NAME}<Buzzvil>"

        fun setUserProfile(profile: BuzzProfile) {
            buzzvilStrategy.setUserProfile(profile = profile)
        }

        fun load(activity: Activity) {
            buzzvilStrategy.loadAD(activity = activity)
        }

    }


    object LockScreen {
        private const val ADDON_NAME = "${NAME}<LockScreen>"


    }

}