package com.devsudal.sdk.addon.factory

import android.app.Application
import com.devsudal.sdk.addon.factory.strategy.IStrategyListener

internal object AddOnFactoryHelper {
    private val strategies = mutableListOf<IStrategyListener>()

    fun registerStrategy(strategy: IStrategyListener) {
        strategies.add(strategy)
    }

    fun initializeAll(application: Application) {
        strategies.forEach { it.initialize(application) }
    }
}