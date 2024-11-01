package com.yunke.lib_common.util

import android.text.BidiFormatter
import android.text.TextDirectionHeuristics
import android.text.TextUtils
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

/**
 * @auth : linker
 * @date : 2020/7/30 4:32 PM
 * @description ：日期时间工具类
 */
object DateUtil {
    private var calendar = Calendar.getInstance()

    @JvmStatic
    fun getMonth(): Int {
        return calendar.get(Calendar.MONTH) + 1
    }

    @JvmStatic
    fun getDay(): Int {
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    /**
     * 时间差转成HH:mm:ss
     */
    fun timestampToString(time: Long): String {
        var h = time / (60 * 60)
        var m = (time % (60 * 60)) / 60
        var s = time % 60
        var hs = if (h < 10) "0${h}" else "$h"
        var ms = if (m < 10) "0${m}" else "$m"
        var ss = if (s < 10) "0${s}" else "$s"
        return "${hs}:${ms}:${ss}"
    }

//    /**
//     * 开始时间戳转日期
//     * 判断中英文
//     */
//    fun startTimeToDate(tsp: Long?): String {
//        tsp?.let {
//            val language = LanguageUtil.getLanguage()
//            return if ("zh-cn" == language || "zh-hant" == language) {
//                val dateStr = timeStampToDate(tsp, "MM月dd日 EEEE")
//                val today = timeStampToDate(System.currentTimeMillis(), "MM月dd日 EEEE")
//                if (dateStr == today) {
//                    "今天 $dateStr"
//                } else {
//                    dateStr
//                }
//            } else {
//                getDateAndWeek(timeStampToDate(tsp, "MM-dd-EEEE"))
//            }
//        }
//        return ""
//    }

    /**
     * 时间戳转日期
     */
    fun timeStampToDate(tsp: Long?, vararg format: String?): String {
        tsp?.let {
            val sdf: SimpleDateFormat = if (format.isEmpty()) {
                SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.getDefault())
            } else {
                SimpleDateFormat(format[0], Locale.getDefault())
            }
            val time = if (tsp < 1000000000000) {
                tsp * 1000
            } else {
                tsp
            }
            return sdf.format(time)
        }
        return ""
    }

    /**
     * 获取日和月
     * 格式 MM-dd
     */
    fun getDayAndMon(format: String): String {
        var splits = format.split("-")
        var sbStr = StringBuffer()
        if (splits.size >= 2) {
            sbStr.append(getMonthToEn(splits[0]))
            sbStr.append(" ")
            sbStr.append(getDayToString(splits[1]))
            return sbStr.toString()
        }
        return format
    }

    /**
     * 获取日期和星期
     * 格式 MM-dd-EEEE
     */
    fun getDateAndWeek(format: String): String {
        var splits = format.split("-")
        var sbStr = StringBuffer()
        if (splits.size >= 3) {
            sbStr.append(if (splits[2].length > 2) splits[2].substring(0, 3) else splits[2])
            sbStr.append(" ")
            sbStr.append(getMonthToEn(splits[0]))
            sbStr.append(" ")
            sbStr.append(getDayToString(splits[1]))
            if (format == timeStampToDate(System.currentTimeMillis(), "MM-dd-EEEE")) {
                sbStr.append(", ")
                sbStr.append("Today")
            }
            return sbStr.toString()
        }
        return format
    }

    /**
     * 获取月份并转换成字符串
     */
    @JvmStatic
    fun getMontToString(): String {
        when (getMonth()) {
            1 -> {
                return "一月"
            }
            2 -> {
                return "二月"
            }
            3 -> {
                return "三月"
            }
            4 -> {
                return "四月"
            }
            5 -> {
                return "五月"
            }
            6 -> {
                return "六月"
            }
            7 -> {
                return "七月"
            }
            8 -> {
                return "八月"
            }
            9 -> {
                return "九月"
            }
            10 -> {
                return "十月"
            }
            11 -> {
                return "十一月"
            }
            12 -> {
                return "十二月"
            }
        }
        return "一月"
    }

    /**
     * 获取月份并转换成字符串
     */
    @JvmStatic
    fun getMontToString(month: String): String {
        when (month) {
            "01" -> {
                return "一月"
            }
            "02" -> {
                return "二月"
            }
            "03" -> {
                return "三月"
            }
            "04" -> {
                return "四月"
            }
            "05" -> {
                return "五月"
            }
            "06" -> {
                return "六月"
            }
            "07" -> {
                return "七月"
            }
            "08" -> {
                return "八月"
            }
            "09" -> {
                return "九月"
            }
            "10" -> {
                return "十月"
            }
            "11" -> {
                return "十一月"
            }
            "12" -> {
                return "十二月"
            }
        }
        return "一月"
    }

    /**
     * 月份转英文缩写
     */
    fun getMonthToEn(month: String): String {
        when (month) {
            "01" -> {
                return "Jan"
            }
            "02" -> {
                return "Feb"
            }
            "03" -> {
                return "Mar"
            }
            "04" -> {
                return "Apr"
            }
            "05" -> {
                return "May"
            }
            "06" -> {
                return "Jun"
            }
            "07" -> {
                return "Jul"
            }
            "08" -> {
                return "Aug"
            }
            "09" -> {
                return "Sept"
            }
            "10" -> {
                return "Oct"
            }
            "11" -> {
                return "Nov"
            }
            "12" -> {
                return "Dec"
            }

        }
        return month
    }

    @JvmStatic
    fun getDayToString(day: String): String {
        when (day) {
            "01" -> {
                return "1"
            }
            "02" -> {
                return "2"
            }
            "03" -> {
                return "3"
            }
            "04" -> {
                return "4"
            }
            "05" -> {
                return "5"
            }
            "06" -> {
                return "6"
            }
            "07" -> {
                return "7"
            }
            "08" -> {
                return "8"
            }
            "09" -> {
                return "9"
            }
            else -> {
                return day
            }

        }
        return day
    }

    /**
     * 时间转成 9'24"的格式
     */
    fun timeToString(time: Long): String {
        try {
            var ls = time / 1000
            var m = ls / 60
            var s = (ls - m * 60)
            var mm = if (m < 10) {
                "0${m}"
            } else {
                "${m}"
            }
            var ss = if (s < 10) {
                "0${s}"
            } else {
                "${s}"
            }
            return "${mm}'${ss}\""
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 秒时间转成 9'24"的格式
     */
    fun basketballSecondsTimeToString(time: Long): String {
        try {
            val m = time / 60
            val s = (time - m * 60)
            val mm = if (m < 10) {
                "0${m}"
            } else {
                "$m"
            }
            val ss = if (s < 10) {
                "0${s}"
            } else {
                "$s"
            }
            return "${mm}'${ss}\""
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 秒时间转成 08:23"的格式
     */
    fun basketballSecondsTimeToString2(time: Long): String {
        try {
            val m = time / 60
            val s = (time - m * 60)
            val mm = if (m < 10) {
                "0${m}"
            } else {
                "$m"
            }
            val ss = if (s < 10) {
                "0${s}"
            } else {
                "$s"
            }
            return "${mm}:${ss}"
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 秒时间转成 24'的格式
     */
    fun footballSecondsTimeToString(time: Long): String {
        try {
            val m = time / 60
            return "${m}'"
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getTimeZone(): String? {
        val mDummyDate = Calendar.getInstance()
        val now = Calendar.getInstance()
        mDummyDate.timeZone = now.timeZone
        mDummyDate[now[Calendar.YEAR], 11, 31, 13, 0] = 0
        return getTimeZoneText(now.timeZone, true)
    }

    private fun getTimeZoneText(tz: TimeZone?, includeName: Boolean): String? {
        val now = Date()
        val gmtFormatter = SimpleDateFormat("ZZZZ")
        gmtFormatter.timeZone = tz
        var gmtString = gmtFormatter.format(now)
        val bidiFormatter: BidiFormatter = BidiFormatter.getInstance()
        val l = Locale.getDefault()
        val isRtl = TextUtils.getLayoutDirectionFromLocale(l) === View.LAYOUT_DIRECTION_RTL
        gmtString = bidiFormatter.unicodeWrap(
            gmtString,
            if (isRtl) TextDirectionHeuristics.RTL else TextDirectionHeuristics.LTR
        )
        return if (!includeName) {
            gmtString
        } else gmtString
    }
}