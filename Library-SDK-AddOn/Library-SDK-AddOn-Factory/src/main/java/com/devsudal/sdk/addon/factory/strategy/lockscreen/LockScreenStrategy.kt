package com.devsudal.sdk.addon.factory.strategy.lockscreen

import android.app.Application
import com.decaffeine.common.sdk.log.LogTracer
import com.devsudal.sdk.addon.connection.AddOnInitListener
import com.devsudal.sdk.addon.connection.lockscreen.LockScreenAddOnConnectListener
import com.devsudal.sdk.addon.factory.strategy.IBaseStrategyListener

internal class LockScreenStrategy : IBaseStrategyListener {
    companion object {
        val NAME: String = LockScreenStrategy::class.java.simpleName
        const val ADDON_CLASS_NAME: String = "com.devsudal.sdk.addon.lockscreen.LockScreenAddOn"

    }

    var instance: LockScreenAddOnConnectListener? = null

    override fun initialize(application: Application) {
        kotlin.runCatching {
            val lockScreenClass = Class.forName(ADDON_CLASS_NAME)
            LogTracer.i { "$NAME -> checkAddOn { LockScreen 클래스가 탑재되어 있습니다. }" }

            instance = lockScreenClass.getDeclaredConstructor().newInstance() as LockScreenAddOnConnectListener?
            instance?.init(
                application = application,
                initLitener = object : AddOnInitListener {
                    override fun onSuccess() {
                        LogTracer.i { "$NAME -> initListener::onSuccess" }
                    }

                    override fun onFailure() {
                        LogTracer.e { "$NAME -> initListener::onFailure" }
                    }

                }
            )

        }.onFailure { e ->
            when (e) {
                is ClassNotFoundException -> {
                    LogTracer.e { "$NAME -> checkAddOn::onFailure {  클래스를 찾을 수 없습니다. }" }
                }

                else -> {
                    LogTracer.e { "$NAME -> checkAddOn::onFailure { 알 수 없는 오류 발생: ${e.message} }" }
                }
            }
        }
    }
}