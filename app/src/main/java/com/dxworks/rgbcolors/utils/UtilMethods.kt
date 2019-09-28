package com.dxworks.rgbcolors.utils

import java.text.SimpleDateFormat
import java.util.*

object UtilMethods{

    public fun getDatesBetween(selectedDate: Date): List<Date> {
        val lsDates = ArrayList<Date>()
        try {
            val currentDate = Calendar.getInstance() //Get the current date
            currentDate.time = selectedDate
            currentDate.set(Calendar.HOUR_OF_DAY, 23)
            currentDate.set(Calendar.MINUTE, 59)
            currentDate.set(Calendar.SECOND, 59)

            //            Date startDate = MobiApp.addSubtractDays(currentDate.getTime(), -1);

            lsDates.add(addSubtractDays(currentDate.time, -1))
            lsDates.add(addSubtractDays(currentDate.time, 0))
        } catch (r: Exception) {
            r.printStackTrace()
        }

        return lsDates
    }

    public fun addSubtractDays(date: Date, days: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DATE, days) //minus number would decrement the days
        return cal.time
    }

    public fun getTwoDp(num: Float): String? {
        try {
            return String.format("%.02f", num)
        } catch (r: Exception) {
            r.printStackTrace()
            return null
        }
    }

    public fun getFloatString(num: String): Float {
        try {
            return java.lang.Float.parseFloat(num)
        } catch (r: Exception) {
            r.printStackTrace()
            return 0f
        }
    }

    public fun getDateTimeFromString(x: String): Date? {
        try {
            val dateformat = SimpleDateFormat("dd-MM-yyyy")
            return dateformat.parse(x)
        } catch (r: Exception) {
            r.printStackTrace()
            return null
        }
    }

    fun parseDateFromString(d: Date): String {
        try {
            val dateformat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            return SimpleDateFormat("EE-dd-MMM-yyyy").format(d)
        } catch (r: Exception) {
            r.printStackTrace()
            return ""
        }

    }

    fun getFormattedDate() : String?{
        try {
            val formatter = SimpleDateFormat("yyyy-MM-dd")

            return formatter.format(getCurrentDate())
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun getCurrentDate(): Date? {
        try {
            val currentDate = Calendar.getInstance() //Get the current date
            return currentDate.time
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun getDateMonth(date: Date): String? {
        try {
            val formatter = SimpleDateFormat("MMMM-yyyy")

            return formatter.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    fun getCurrentDateMonth(): String? {
        try {
            val currentDate = Calendar.getInstance() //Get the current date
            val formatter = SimpleDateFormat("MMMM-yyyy")

            return formatter.format(currentDate.time)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    fun getFirstAndLastDates(selectedDate: Date): List<Date> {
        val selectedDates = ArrayList<Date>()
        try {
            val start = Calendar.getInstance()
            val end = Calendar.getInstance()

            start.time = selectedDate
            start.set(Calendar.DAY_OF_MONTH, start.getActualMinimum(Calendar.DAY_OF_MONTH))
            start.set(Calendar.HOUR_OF_DAY, 0)
            start.set(Calendar.MINUTE, 0)
            start.set(Calendar.SECOND, 0)

            end.time = selectedDate
            end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH))
            end.set(Calendar.HOUR_OF_DAY, 23)
            end.set(Calendar.MINUTE, 59)
            end.set(Calendar.SECOND, 59)

            selectedDates.add(start.time)
            selectedDates.add(end.time)
        } catch (r: Exception) {
            r.printStackTrace()
        }

        return selectedDates
    }

//    fun getCurrentDateNoTime(): Date? {
//        try {
//            val currentDate = Calendar.getInstance() //Get the current date
//            currentDate.set(Calendar.HOUR_OF_DAY, 23)
//            currentDate.set(Calendar.MINUTE, 59)
//            currentDate.set(Calendar.SECOND, 59)
//            //            SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy");
//            return currentDate.time// formatter.parse(formatter.format(currentDate.getTime()));
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return null
//        }
//    }

    fun getCurrentDateNoTime(): Date? {
        try {
            val currentDate = Calendar.getInstance() //Get the current date
            currentDate.set(Calendar.HOUR_OF_DAY, 23)
            currentDate.set(Calendar.MINUTE, 59)
            currentDate.set(Calendar.SECOND, 59)
            //            SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy");
            return currentDate.time// formatter.parse(formatter.format(currentDate.getTime()));
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    fun parseTimeAndDateFromString(d: Date): String {
        try {
            val date = SimpleDateFormat("E-dd-MM-yyyy").format(d)
            val time = SimpleDateFormat("h:mm a").format(d)
            return "$date $time"
        } catch (r: Exception) {
            r.printStackTrace()
            return ""
        }

    }

}