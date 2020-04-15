package irina.activityreminder

import irina.activityreminder.utils.TimeUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class TimeUtilsTest {
    @Test
    fun timeIsBetweenMorningAndEveningTrue() {
        val time = 1586267100000 //7.04.2020 15.45
        assertEquals(true, TimeUtils.timeIsBetween(time, 9, 5, 18, 35))
    }

    @Test
    fun timeIsBetweenMorningAndEveningFalse() {
        val time = 1586267100000 //7.04.2020 15.45
        assertEquals(false, TimeUtils.timeIsBetween(time, 9, 5, 15, 35))
    }

    @Test
    fun timeIsBetweenEveningAndMorningTrue() {
        val time = 1586267100000 //7.04.2020 15.45
        assertEquals(true, TimeUtils.timeIsBetween(time, 15, 5, 9, 35))
    }

    @Test
    fun timeIsBetweenEveningAndMorningFalse() {
        val time = 1586267100000 //7.04.2020 15.45
        assertEquals(false, TimeUtils.timeIsBetween(time, 18, 5, 9, 35))
    }

    @Test
    fun defaultFalse() {
        val time = 1586267100000 //7.04.2020 15.45
        assertEquals(false, TimeUtils.timeIsBetween(time, 22, 0, 9, 0))
    }

    @Test
    fun defaultTrue() {
        val time = 1586295900000 //7.04.2020 15.45
        assertEquals(true, TimeUtils.timeIsBetween(time, 22, 0, 9, 0))
    }

    @Test
    fun defaultMorningTrue() {
        val time = 1586238300000 //7.04.2020 7.45
        assertEquals(true, TimeUtils.timeIsBetween(time, 22, 0, 9, 0))
    }

    @Test
    fun timeTillTodayLater() {
        val time = 1586267100000 //7.04.2020 15.45
        assertEquals(1586278200000 - time, TimeUtils.timeTill(time, 18, 50))
    }

    @Test
    fun timeTillTomorrowEarly() {
        val time = 1586267100000 //7.04.2020 15.45
        assertEquals(1586325000000 - time, TimeUtils.timeTill(time, 7, 50))
    }

}