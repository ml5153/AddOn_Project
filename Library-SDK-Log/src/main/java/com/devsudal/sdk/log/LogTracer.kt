package com.devsudal.sdk.log

object LogTracer {

    private val logger = Logger(moduleName = "AddOn")

    fun print(
        throwable: Throwable? = null,
        moduleName: String? = null,
        viewName: String? = null,
        trace: () -> String
    ) {
        Logger.print(
            throwable = throwable,
            moduleName = moduleName,
            viewName = viewName,
            trace = trace
        )
    }

    val available: Boolean
        get() {
            return Logger.available
        }

    fun i(tag: String? = null, trace: () -> String) = logger.info(sourceName = tag, trace = trace)

    fun d(tag: String? = null, trace: () -> String) = logger.debug(sourceName = tag, trace = trace)

    fun v(tag: String? = null, trace: () -> String) = logger.verbose(sourceName = tag, trace = trace)

    fun w(tag: String? = null, trace: () -> String) = logger.warn(sourceName = tag, trace = trace)

    fun e(tag: String? = null, throwable: Throwable? = null, trace: () -> String) =
        logger.error(throwable = throwable, sourceName = tag, trace = trace)
}