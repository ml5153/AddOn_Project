package com.devsudal.sdk.addon.factory.strategy.buzzvil

import android.app.Activity
import android.app.Application
import com.devsudal.sdk.addon.connection.AddOnInitListener
import com.devsudal.sdk.addon.connection.buzzvil.BuzzAddOnConnectListener
import com.devsudal.sdk.addon.connection.buzzvil.BuzzProfile
import com.devsudal.sdk.addon.factory.strategy.IBaseStrategyListener
import com.devsudal.sdk.log.LogTracer

internal class BuzzvilBaseStrategy : IBaseStrategyListener {
    companion object {
        val NAME: String = BuzzvilBaseStrategy::class.java.simpleName
        const val ADDON_CLASS_NAME: String = "com.devsudal.sdk.addon.buzzvil.BuzzAddOn"
        const val SDK_CLASS_NAME: String = "com.buzzvil.sdk.BuzzvilSdk"
    }

    private var instance: BuzzAddOnConnectListener? = null


    override fun initialize(application: Application) {
        kotlin.runCatching {
            val addonClass = Class.forName(ADDON_CLASS_NAME)
            LogTracer.i { "$NAME -> { Buzzvil 클래스가 탑재되어 있습니다. }" }

            instance = (addonClass.getDeclaredConstructor().newInstance()) as BuzzAddOnConnectListener
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
                    try {
                        val sdkClass = Class.forName(SDK_CLASS_NAME)
                        LogTracer.e { "$NAME -> Buzzvil SDK를 앱사에서 직접 참조하고 있습니다." }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        LogTracer.e { "$NAME -> Buzzvil SDK가 참조되지 않았습니다. { code: ${e.printStackTrace()}, message: ${e.message}" }
                    }
                }

                else -> {
                    e.printStackTrace()
                    LogTracer.e { "$NAME -> Buzzvil 알 수 없는 오류 발생: ${e.message}" }
                }
            }
        }
    }

     fun setUserProfile(profile: BuzzProfile) {
        instance?.setUserProfile(
            BuzzProfile(
                userId = profile.userId,
                gender = profile.gender,
                birth = profile.birth
            )
        )
    }

     fun loadAD(activity: Activity) {
        instance?.loadAD(activity = activity)
    }

}