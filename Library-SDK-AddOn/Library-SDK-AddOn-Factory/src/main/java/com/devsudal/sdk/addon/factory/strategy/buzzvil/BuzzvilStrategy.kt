package com.devsudal.sdk.addon.factory.strategy.buzzvil

import android.app.Activity
import android.app.Application
import android.util.Log
import com.devsudal.sdk.addon.support.AddOnInitListener
import java.lang.reflect.InvocationTargetException

internal class BuzzvilStrategy : IBuzzvilStrategyListener {
    companion object {
        const val NAME: String = "BuzzvilStrategy"
        const val ADDON_BUZZVIL_CLASS_NAME: String = "com.devsudal.sdk.addon.buzzvil.BuzzAddOn"
        const val BUZZVIL_SDK_CLASS_NAME: String = "com.buzzvil.sdk.BuzzvilSdk"
    }

    private var instance: Any? = null


    override fun initialize(application: Application) {
        kotlin.runCatching {
            val clazz = Class.forName(ADDON_BUZZVIL_CLASS_NAME)
            Log.e(NAME, "$NAME -> Buzzvil 클래스가 탑재되어 있습니다.")

            instance = clazz.getDeclaredConstructor().newInstance()
            val initMethod = clazz.getMethod("init", Application::class.java, AddOnInitListener::class.java)
            initMethod.invoke(
                instance,
                application,
                object : AddOnInitListener {
                    override fun onSuccess() {
                        Log.e(NAME, "$NAME -> initListener::onSuccess")
                    }

                    override fun onFailure() {
                        Log.e(NAME, "$NAME -> initListener::onFailure")
                    }
                }
            )
        }.onFailure { e ->
            e.printStackTrace()
            when (e) {
                is ClassNotFoundException -> {
                    Log.e(NAME, "$NAME -> Buzzvil 클래스를 찾을 수 없습니다.")

                    try {
                        Class.forName(BUZZVIL_SDK_CLASS_NAME)
                        Log.e(NAME, "$NAME -> Buzzvil SDK가 직접 참조되고 있습니다.")
                    } catch (e: Exception) {
                        Log.e(NAME, "$NAME -> Buzzvil SDK가 참조되지 않았습니다.")
                    }
                }

                is NoSuchMethodException -> {
                    Log.e(NAME, "$NAME -> Buzzvil 메서드를 찾을 수 없습니다.")
                }

                is InvocationTargetException -> {
                    Log.e(NAME, "$NAME -> Buzzvil 메서드 호출 중 예외가 발생했습니다: ${e.targetException.message}")
                }

                else -> {
                    Log.e(NAME, "$NAME -> Buzzvil 알 수 없는 오류 발생: ${e.message}")
                }
            }
        }
    }

    override fun setUserProfile(profile: BuzzProfile) {
        instance?.let {
            val method = it::class.java.getMethod(
                "setUserProfile",
                String::class.java,
                Int::class.java
            )
            method.invoke(
                instance,
                profile.userId,
                profile.birth
            )
        }
    }

    override fun loadAD(activity: Activity) {
        instance?.let {
            val method = it::class.java.getMethod(
                "loadAD",
                Activity::class.java
            )
            method.invoke(
                instance,
                method
            )
        }
    }

}