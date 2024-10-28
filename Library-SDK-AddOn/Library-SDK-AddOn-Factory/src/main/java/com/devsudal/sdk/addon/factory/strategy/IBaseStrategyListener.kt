package com.devsudal.sdk.addon.factory.strategy

import android.app.Application

internal interface IBaseStrategyListener {
    fun initialize(application:Application)
}

