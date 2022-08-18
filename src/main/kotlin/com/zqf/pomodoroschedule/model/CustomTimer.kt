package com.zqf.pomodoroschedule.model

import java.util.*

class CustomTimer {

    fun scheduleWithTimer() {
        Timer("default timer").scheduleAtFixedRate(NewsletterTask(10), 500, 1000)
    }
}

private class NewsletterTask constructor(period: Long): TimerTask() {
    var index = period;

    override fun run() {
        if (index == 0L){
            this.cancel()
        }
        println("${index --}")
    }
}

fun main() {
    CustomTimer().scheduleWithTimer()
}