package com.devsudal.sdk.addon.factory.strategy.notification

import android.app.Application
import android.util.Log
import com.devsudal.sdk.addon.factory.AddOnFactory
import com.devsudal.sdk.addon.factory.strategy.IStrategyListener
import com.devsudal.sdk.addon.support.AddOnInitListener
import java.lang.reflect.InvocationTargetException

internal class NotificationStrategy : IStrategyListener {
    companion object {
        const val NAME: String = "NotificationStrategy"
    }

    override fun initialize(application: Application) {
        kotlin.runCatching {
            val notificationClass = Class.forName("com.devsudal.sdk.addon.NotificationAddOn")
            Log.e(AddOnFactory.NAME, "$NAME -> checkAddOn { Notification 클래스가 탑재되어 있습니다. }")

            val instance = notificationClass.getDeclaredConstructor().newInstance()
            val initMethod = notificationClass.getMethod("init", Application::class.java, AddOnInitListener::class.java)
            initMethod.invoke(
                instance,
                application,
                object : AddOnInitListener {
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

                is NoSuchMethodException -> {
                    Log.e(AddOnFactory.NAME, "$NAME -> checkAddOn::onFailure { 메서드를 찾을 수 없습니다. }")
                }

                is InvocationTargetException -> {
                    Log.e(AddOnFactory.NAME, "$NAME -> checkAddOn::onFailure { 메서드 호출 중 예외가 발생했습니다: ${e.targetException.message} }")
                }

                else -> {
                    Log.e(AddOnFactory.NAME, "$NAME -> checkAddOn::onFailure { 알 수 없는 오류 발생: ${e.message} }")
                }
            }
        }
    }
}