package com.devsudal.sdk.addon.factory.strategy.buzzvil

import android.app.Activity
import android.app.Application
import com.devsudal.sdk.addon.connection.AddOnInitListener
import com.devsudal.sdk.addon.connection.buzzvil.BuzzAddOnConnectListener
import com.devsudal.sdk.addon.connection.buzzvil.BuzzProfile
import com.devsudal.sdk.addon.factory.strategy.IStrategyListener
import com.devsudal.sdk.log.LogTracer

internal class BuzzvilStrategy : IStrategyListener.IBuzzvilStrategyListener {
    companion object {
        val NAME: String = BuzzvilStrategy::class.java.simpleName
        const val ADDON_CLASS_NAME: String = "com.devsudal.sdk.addon.buzzvil.BuzzAddOn"
        const val SDK_CLASS_NAME: String = "com.buzzvil.sdk.BuzzvilSdk"
    }

    private var instance: BuzzAddOnConnectListener? = null


    private fun init(application: Application, clazz: Class<*>) {
        instance = (clazz.getDeclaredConstructor().newInstance()) as BuzzAddOnConnectListener
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
    }


    override fun initialize(application: Application) {
        kotlin.runCatching {
            val addonClass = Class.forName(ADDON_CLASS_NAME)
            LogTracer.i { "$NAME -> { Buzzvil 클래스가 탑재되어 있습니다. }" }
            init(application = application, clazz = addonClass)

        }.onFailure { e ->
            when (e) {
                is ClassNotFoundException -> {
                    try {
                        val sdkClass = Class.forName(SDK_CLASS_NAME)
                        LogTracer.i { "$NAME -> Buzzvil SDK를 앱사에서 직접 참조하고 있습니다." }

                        if (BuzzAddOnConnectListener::class.java.isAssignableFrom(sdkClass)) {
                            init(application = application, clazz = sdkClass)
                        } else {
                            LogTracer.e { "$NAME -> BuzzvilSdk 클래스는 BuzzAddOnConnectListener를 구현하지 않습니다." }
                        }


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

    override fun setUserProfile(profile: BuzzProfile) {
        instance?.setUserProfile(
            BuzzProfile(
                userId = profile.userId,
                gender = profile.gender,
                birth = profile.birth
            )
        )
    }

    override fun loadAD(activity: Activity) {
        instance?.loadAD(activity = activity)
    }

}