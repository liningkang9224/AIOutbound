package com.yunke.lib_common.util

import java.text.DecimalFormat

object SettingUtil {


    fun nonEmpty(data: Float?) = data ?: 0.0f
    fun nonEmpty(data: Int?) = data ?: 0
    fun nonEmpty(data: Long?) = data ?: 0L
    fun nonEmpty(data: String?) = data ?: ""
    fun nonEmpty(data: Any?) = data ?: ""


    fun stringToFloat(data: String?): Float {
        try {
            data?.let {
                return data.toFloat()
            }
        } catch (e: Exception) {
            e.message
        }
        return 0.0f
    }

    fun stringToInt(data: String?): Int {
        data?.let {
            return it.toInt()
        }
        return 0
    }

    fun floatFormat(data: Float): String {
        val decimalFormat = DecimalFormat("#0.00")
        return decimalFormat.format(data)
    }
}