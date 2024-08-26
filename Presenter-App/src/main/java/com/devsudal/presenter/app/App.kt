package com.devsudal.presenter.app

import android.app.Application
import com.devsudal.sdk.ph.PointHomeSDK

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        // 포인트홈 SDK 초기화
        PointHomeSDK.initialized(this@App)
    }


}