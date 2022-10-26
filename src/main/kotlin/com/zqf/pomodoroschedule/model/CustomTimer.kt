package com.zqf.pomodoroschedule.model

import java.util.*
import kotlin.system.exitProcess

class CustomTimer(val timer: Timer) {

    fun scheduleWithTimer() {
        Timer("default timer").scheduleAtFixedRate(NewsletterTask(10), 500, 1000)
    }
}

private class NewsletterTask constructor(period: Long): TimerTask() {
    var index = period;

    override fun run() {
        if (index == 0L){
            this.cancel()
            exitProcess(0)
        }
        println("${index --}")
    }
}
