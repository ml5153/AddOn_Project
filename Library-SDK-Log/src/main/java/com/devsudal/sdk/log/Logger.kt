package com.devsudal.sdk.log

import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

internal class Logger(private val moduleName: String) {

    companion object {
        fun print(throwable: Throwable? = null, moduleName: String? = null, viewName: String? = null, trace: () -> String) {
            println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
            println("${LoggerSettings.SourceName}:[$moduleName#$viewName] => ${trace()}")
            println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        }

        val available: Boolean
            get() {
                return LoggerSettings.allowLog || (LoggerSettings.logVerifier?.allowLogging == true)
            }
    }

    private enum class LEVEL { VERBOSE, DEBUG, INFO, WARN, ERROR }

    fun print(throwable: Throwable? = null, sourceName: String? = null, trace: () -> String) {
        print(throwable = throwable, moduleName = moduleName, viewName = sourceName, trace = trace)
    }

    fun info(throwable: Throwable? = null, sourceName: String? = null, args: Array<String>) = logWriter(
        logLevel = LEVEL.INFO, throwable = throwable, moduleName = moduleName, sourceName = sourceName, trace = makeTrace(*args)
    )

    fun debug(throwable: Throwable? = null, sourceName: String? = null, vararg args: String) = logWriter(
        logLevel = LEVEL.DEBUG, throwable = throwable, moduleName = moduleName, sourceName = sourceName, trace = makeTrace(*args)
    )

    fun verbose(throwable: Throwable? = null, sourceName: String? = null, vararg args: String) = logWriter(
        logLevel = LEVEL.VERBOSE, throwable = throwable, moduleName = moduleName, sourceName = sourceName, trace = makeTrace(*args)
    )

    fun warn(throwable: Throwable? = null, sourceName: String? = null, vararg args: String) = logWriter(
        logLevel = LEVEL.WARN, throwable = throwable, moduleName = moduleName, sourceName = sourceName, trace = makeTrace(*args)
    )

    fun error(throwable: Throwable? = null, sourceName: String? = null, vararg args: String) = logWriter(
        logLevel = LEVEL.ERROR, throwable = throwable, moduleName = moduleName, sourceName = sourceName, trace = makeTrace(*args)
    )

    private fun makeTrace(vararg trace: String): () -> String {
        return fun(): String {
            return trace.joinToString()
        }
    }

    fun info(throwable: Throwable? = null, sourceName: String? = null, trace: () -> String) = logWriter(
        logLevel = LEVEL.INFO, throwable = throwable, moduleName = moduleName, sourceName = sourceName, trace = trace
    )

    fun debug(throwable: Throwable? = null, sourceName: String? = null, trace: () -> String) = logWriter(
        logLevel = LEVEL.DEBUG, throwable = throwable, moduleName = moduleName, sourceName = sourceName, trace = trace
    )

    fun verbose(throwable: Throwable? = null, sourceName: String? = null, trace: () -> String) = logWriter(
        logLevel = LEVEL.VERBOSE, throwable = throwable, moduleName = moduleName, sourceName = sourceName, trace = trace
    )

    fun warn(throwable: Throwable? = null, sourceName: String? = null, trace: () -> String) = logWriter(
        logLevel = LEVEL.WARN, throwable = throwable, moduleName = moduleName, sourceName = sourceName, trace = trace
    )

    fun error(throwable: Throwable? = null, sourceName: String? = null, trace: () -> String) = logWriter(
        logLevel = LEVEL.ERROR, throwable = throwable, moduleName = moduleName, sourceName = sourceName, trace = trace
    )

    fun error(throwable: Throwable? = null, sourceName: String? = null) = logWriter(
        logLevel = LEVEL.ERROR, throwable = throwable, moduleName = moduleName, sourceName = sourceName, trace = { "" }
    )

    private fun logWriter(logLevel: LEVEL, throwable: Throwable? = null, moduleName: String, sourceName: String? = null, trace: () -> String) {
        if (available) {
            logMessage(logLevel, throwable, moduleName, sourceName, trace)
        } else {
            val isLoggable = Log.isLoggable(LoggerSettings.LOGGABLE, Log.VERBOSE)
            if (isLoggable) {
                logMessage(logLevel, throwable, moduleName, sourceName, trace)
            }
        }
    }

    private fun logMessage(
        logLevel: LEVEL,
        throwable: Throwable? = null,
        moduleName: String,
        sourceName: String? = null,
        trace: () -> String
    ) {
        val message = makeLog(throwable, moduleName, sourceName, trace)
        logLongMessage(LoggerSettings.SourceName, message, logLevel)
    }

    private fun makeLog(throwable: Throwable? = null, moduleName: String, sourceName: String? = null, trace: () -> String): String {
        return "[$moduleName/$sourceName] => ${trace()}" + if (throwable != null) " => ${getStackTraceString(throwable)}" else ""
    }

    private fun logLongMessage(tag: String, message: String, logLevel: LEVEL) {
        val maxLogSize = 1000
        val maxParts = 5
        val totalParts = (message.length / maxLogSize).coerceAtMost(maxParts)

        for (i in 0..totalParts) {
            val start = i * maxLogSize
            val end = (start + maxLogSize).coerceAtMost(message.length)
            val partMessage = message.substring(start, end)

            when (logLevel) {
                LEVEL.VERBOSE -> Log.v(tag, partMessage)
                LEVEL.DEBUG -> Log.d(tag, partMessage)
                LEVEL.INFO -> Log.i(tag, partMessage)
                LEVEL.WARN -> Log.w(tag, partMessage)
                LEVEL.ERROR -> Log.e(tag, partMessage)
            }
        }
    }

    private fun getStackTraceString(t: Throwable): String {
        // Don't replace this with Log.getStackTraceString() - it hides
        // UnknownHostException, which is not what we want.
        val sw = StringWriter(256)
        val pw = PrintWriter(sw, false)
        t.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

}