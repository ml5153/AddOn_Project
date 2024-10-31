package com.devsudal.sdk.addon.factory

import android.app.Activity
import android.app.Application
import com.decaffeine.common.sdk.log.LogTracer
import com.devsudal.sdk.addon.connection.buzzvil.BuzzProfile
import com.devsudal.sdk.addon.factory.strategy.IBaseStrategyListener
import com.devsudal.sdk.addon.factory.strategy.buzzvil.BuzzvilStrategy
import com.devsudal.sdk.addon.factory.strategy.lockscreen.LockScreenStrategy

object AddOnFactory {

    const val NAME: String = "AddOnFactory"

    private val strategies = mutableMapOf<String, IBaseStrategyListener?>()

    fun initialize(application: Application) {
        LogTracer.i { "$NAME -> initialize" }

        strategies[BuzzvilStrategy.NAME] = BuzzvilStrategy().apply {
            initialize(application)
        }
        strategies[LockScreenStrategy.NAME] = LockScreenStrategy().apply {
            initialize(application)
        }
    }

    private fun getStrategy(name: String): IBaseStrategyListener? {
        return strategies[name]
    }


    object Buzzvil {
        private val strategy by lazy {
            getStrategy(BuzzvilStrategy.NAME) as? BuzzvilStrategy
                ?: throw Exception("strategy not initialized")
        }

        fun setUserProfile(profile: BuzzProfile) {
            LogTracer.i { "$NAME -> setUserProfile" }
            strategy.setUserProfile(profile)
        }

        fun load(activity: Activity) {
            LogTracer.i { "$NAME -> load" }
            strategy.loadAD(activity)
        }
    }

    object LockScreen {
        private val strategy by lazy {
            getStrategy(LockScreenStrategy.NAME) as? LockScreenStrategy
                ?: throw Exception("strategy not initialized")
        }
    }

}