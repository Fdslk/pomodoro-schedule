package com.zqf.pomodoroschedule.model

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.util.concurrency.AppExecutorUtil
import com.zqf.pomodoroschedule.model.time.Time
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class TimeSource(private val listener: (Time) -> Unit) {
    private var future: ScheduledFuture<*>? = null

    fun start(): TimeSource {
        val runnable = {
            ApplicationManager.getApplication().invokeLater(
                { listener(Time.now()) },
                ModalityState.any() // Use "any" so that timer is updated even while modal dialog like IDE Settings is open.
            )
        }
        future = AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(runnable, 0, 500L,
            TimeUnit.MILLISECONDS
        )
        return this
    }

    fun stop() {
        future?.cancel(true)
    }
}