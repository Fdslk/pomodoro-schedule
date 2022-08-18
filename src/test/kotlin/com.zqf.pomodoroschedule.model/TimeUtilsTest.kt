package com.zqf.pomodoroschedule.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TimeUtilsTest {

    @Test
    fun shouldReturnSecondsAndMinutesWhenSecondMoreThen60() {
        assertEquals("01:01", TimeUtils.convertSecondsToMinuitString(61))
    }

    @Test
    fun shouldReturnSecondsWhenSecondLessThen60() {
        assertEquals("00:59", TimeUtils.convertSecondsToMinuitString(59))
    }
}