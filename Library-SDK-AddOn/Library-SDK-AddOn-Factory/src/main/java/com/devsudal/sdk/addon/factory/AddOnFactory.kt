package com.devsudal.sdk.addon.factory

import android.app.Activity
import android.app.Application
import com.devsudal.sdk.addon.connection.buzzvil.BuzzProfile
import com.devsudal.sdk.addon.factory.strategy.IBaseStrategyListener
import com.devsudal.sdk.addon.factory.strategy.buzzvil.BuzzvilBaseStrategy
import com.devsudal.sdk.addon.factory.strategy.lockscreen.LockScreenBaseStrategy
import com.devsudal.sdk.log.LogTracer

object AddOnFactory {

    const val NAME: String = "AddOnFactory"

    private val strategies = mutableMapOf<String, IBaseStrategyListener?>()

    fun initialize(application: Application) {
        LogTracer.i { "$NAME -> initialize" }

        /** 초기화 */
        strategies[BuzzvilBaseStrategy.NAME] = BuzzvilBaseStrategy().apply {
            initialize(application)
        }
        strategies[LockScreenBaseStrategy.NAME] = LockScreenBaseStrategy().apply {
            initialize(application)
        }
    }

    private fun getStrategy(name: String): IBaseStrategyListener? {
        return strategies[name]
    }


    object Buzzvil {
        private val strategy by lazy {
            getStrategy(BuzzvilBaseStrategy.NAME) as? BuzzvilBaseStrategy
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
            getStrategy(LockScreenBaseStrategy.NAME) as? LockScreenBaseStrategy
                ?: throw Exception("strategy not initialized")
        }
    }

}