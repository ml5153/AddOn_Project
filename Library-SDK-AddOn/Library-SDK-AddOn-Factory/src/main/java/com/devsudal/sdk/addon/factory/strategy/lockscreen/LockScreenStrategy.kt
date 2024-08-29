package com.devsudal.sdk.addon.factory.strategy.lockscreen

import android.app.Application
import android.util.Log
import com.devsudal.sdk.addon.connection.AddOnInitListener
import com.devsudal.sdk.addon.connection.lockscreen.LockScreenAddOnConnectListener
import com.devsudal.sdk.addon.factory.AddOnFactory

internal class LockScreenStrategy : ILockScreenStrategyListener {
    companion object {
        const val NAME: String = "LockScreenStrategy"
        const val ADDON_BUZZVIL_CLASS_NAME: String = "com.devsudal.sdk.addon.LockScreenAddOn"

    }

    var instance: LockScreenAddOnConnectListener? = null

    override fun initialize(application: Application) {
        kotlin.runCatching {
            val lockScreenClass = Class.forName(ADDON_BUZZVIL_CLASS_NAME)
            Log.e(AddOnFactory.NAME, "$NAME -> checkAddOn { LockScreen 클래스가 탑재되어 있습니다. }")

            instance = lockScreenClass.getDeclaredConstructor().newInstance() as LockScreenAddOnConnectListener?
            instance?.init(
                application = application,
                initLitener = object : AddOnInitListener {
                    override fun onSuccess() {
                        Log.e(AddOnFactory.NAME, "$NAME -> initListener::onSuccess")
                    }

                    override fun onFailure() {
                        Log.e(AddOnFactory.NAME, "$NAME -> initListener::onFailure")
                    }

                }
            )

        }.onFailure { e ->
            e.printStackTrace()
            when (e) {
                is ClassNotFoundException -> {
                    Log.e(AddOnFactory.NAME, "$NAME -> checkAddOn::onFailure {  클래스를 찾을 수 없습니다. }")
                }

                else -> {
                    Log.e(AddOnFactory.NAME, "$NAME -> checkAddOn::onFailure { 알 수 없는 오류 발생: ${e.message} }")
                }
            }
        }
    }
}