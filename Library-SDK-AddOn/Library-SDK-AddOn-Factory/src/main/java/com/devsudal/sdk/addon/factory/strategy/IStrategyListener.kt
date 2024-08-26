package com.devsudal.sdk.addon.factory.strategy

import android.app.Application

internal interface IStrategyListener {
    fun initialize(application:Application)
}