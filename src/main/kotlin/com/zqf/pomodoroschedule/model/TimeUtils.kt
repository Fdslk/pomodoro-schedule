package com.zqf.pomodoroschedule.model

 class TimeUtils {
     companion object {
         @JvmStatic
         fun convertSecondsToMinuitString(originalSecond: Int): String {
             val minutes = originalSecond / 60
             return String.format("%02d:%02d", minutes, originalSecond - minutes * 60)
         }
     }
}