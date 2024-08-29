package com.devsudal.sdk.addon.factory.strategy.buzzvil

import android.app.Activity
import android.app.Application
import android.util.Log
import com.devsudal.sdk.addon.connection.AddOnInitListener
import com.devsudal.sdk.addon.connection.buzzvil.BuzzAddOnConnectListener
import com.devsudal.sdk.addon.connection.buzzvil.BuzzProfile
import com.devsudal.sdk.addon.factory.strategy.IStrategyListener

internal class BuzzvilStrategy : IStrategyListener.IBuzzvilStrategyListener {
    companion object {
        val NAME: String = BuzzvilStrategy::class.java.simpleName
        const val ADDON_CLASS_NAME: String = "com.devsudal.sdk.addon.buzzvil.BuzzAddOn"
        const val SDK_CLASS_NAME: String = "com.buzzvil.sdk.BuzzvilSdk"
    }

    private var instance: BuzzAddOnConnectListener? = null


    override fun initialize(application: Application) {
        kotlin.runCatching {
            val clazz = Class.forName(ADDON_CLASS_NAME)
            Log.e(NAME, "$NAME -> Buzzvil 클래스가 탑재되어 있습니다.")


            instance = (clazz.getDeclaredConstructor().newInstance()) as BuzzAddOnConnectListener
            instance?.init(
                application = application,
                initLitener = object : AddOnInitListener {
                    override fun onSuccess() {
                        Log.e(NAME, "$NAME -> initListener::onSuccess")
                    }

                    override fun onFailure() {
                        Log.e(NAME, "$NAME -> initListener::onFailure")
                    }

                }
            )

        }.onFailure { e ->
            when (e) {
                is ClassNotFoundException -> {
                    try {
                        Class.forName(SDK_CLASS_NAME)
                        Log.e(NAME, "$NAME -> Buzzvil SDK가 앱사에서 직접 참조하고 있습니다.")
                    } catch (e: Exception) {
                        Log.e(NAME, "$NAME -> Buzzvil SDK가 참조되지 않았습니다.")
                    }
                }

                else -> {
                    Log.e(NAME, "$NAME -> Buzzvil 알 수 없는 오류 발생: ${e.message}")
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