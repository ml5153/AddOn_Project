package com.devsudal.sdk.addon.factory.strategy.buzzvil

import android.app.Activity
import com.devsudal.sdk.addon.factory.strategy.IStrategyListener

internal interface IBuzzvilStrategyListener : IStrategyListener {
    fun setUserProfile(profile: BuzzProfile)
    fun loadAD(activity: Activity)
}