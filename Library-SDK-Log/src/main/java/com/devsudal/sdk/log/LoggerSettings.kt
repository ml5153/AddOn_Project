package com.devsudal.sdk.log

import android.app.Application
import android.content.pm.PackageManager

object LoggerSettings {

    private const val LogName: String = "avatye_log"
    internal const val SourceName: String = "Add-On-Log"
    internal const val LOGGABLE: String = "cashbutton.logger"

    var logVerifier: ILogVerifier? = null

    internal var allowLog: Boolean = false
        private set

    fun retrieveLog(application: Application) {
        allowLog = try {
            val bundle = application.packageManager.getApplicationInfo(application.packageName, PackageManager.GET_META_DATA).metaData
            val bundleValue = bundle.getBoolean(LogName, false)
            bundleValue
        } catch (e: Exception) {
            false
        }
        if (allowLog) {
            Logger.print(moduleName = SourceName, trace = { "CashButton Logger Initialized" })
        }
    }
}