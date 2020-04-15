package irina.activityreminder.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    fun timeToDate(hours: Int, minutes: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        //calendar.add(Calendar.DATE, 1)
        return calendar.time
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formatter.format(date)
    }


    fun timeIsBetween(
        time: Long,
        hoursStart: Int,
        minutesStart: Int,
        hoursEnd: Int,
        minutesEnd: Int
    ): Boolean {
        val date = Date(time)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE) + hours * 60
        return timeIsBetween(
            minutes,
            minutesStart + hoursStart * 60,
            minutesEnd + hoursEnd * 60
        )
    }

    private fun timeIsBetween(minutes: Int, minutesStart: Int, minutesEnd: Int): Boolean {
        return if (minutes in (minutesStart + 1) until minutesEnd) {
            minutesStart <= minutesEnd
        } else {
            if (minutesStart > minutesEnd) minutes > minutesStart || minutes < minutesEnd else false
        }
    }

    fun timeTill(time: Long, hoursTill: Int, minutesTill: Int): Long {
        val date = Date(time)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE) + hours * 60
        calendar.set(Calendar.HOUR_OF_DAY, hoursTill)
        calendar.set(Calendar.MINUTE, minutesTill)
        if (minutes > minutesTill + hoursTill * 60) {
            calendar.add(Calendar.DATE, 1)
        }
        return calendar.timeInMillis - time
    }
}