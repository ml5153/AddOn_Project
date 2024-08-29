package com.devsudal.sdk.addon.factory.strategy

import android.app.Activity
import android.app.Application
import com.devsudal.sdk.addon.connection.buzzvil.BuzzProfile

internal interface IStrategyListener {
    fun initialize(application:Application)

    interface IBuzzvilStrategyListener : IStrategyListener {
        fun setUserProfile(profile: BuzzProfile)
        fun loadAD(activity: Activity)
    }
}

