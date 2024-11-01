package com.yunke.lib_common.util

import android.content.Context
import android.content.SharedPreferences
import com.yunke.lib_common.BaseApplication

/**
 * @auth : linker
 * @date : 2020/7/27 3:38 PM
 * @description ：sharedpreferences
 */
object SharedManage {
    private val SHARED_NAME = "app_yd_shared"
    const val ALLINPAY_IP="allinpay_ip"

    private var sharedReadable: SharedPreferences? = null
    private var sharedWritable: SharedPreferences.Editor? = null


    init {
        sharedReadable = BaseApplication.getContext()
            .getSharedPreferences(SHARED_NAME, Context.MODE_MULTI_PROCESS)
        sharedWritable = sharedReadable?.edit()
    }

    fun getString(key: String?): String? {
        return getString(key, "")
    }

    fun getString(key: String?, defValue: String): String {
        return sharedReadable?.getString(key, defValue) ?: defValue
    }

    fun putString(key: String?, value: String?) {
        sharedWritable?.putString(key, value)
        sharedWritable?.commit()
    }

    fun putInt(key: String?, value: Int) {
        sharedWritable?.putInt(key, value)
        sharedWritable?.commit()
    }

    fun putLong(key: String?, value: Long) {
        sharedWritable?.putLong(key, value)
        sharedWritable?.commit()
    }

    fun putBoolean(key: String?, value: Boolean) {
        sharedWritable?.putBoolean(key, value)
        sharedWritable?.commit()
    }

    fun getInt(key: String?, def: Int): Int {
        return sharedReadable?.getInt(key, def) ?: def
    }

    fun getLong(key: String?, def: Long): Long {
        return sharedReadable?.getLong(key, def) ?: def
    }

    fun getBoolean(key: String?, def: Boolean): Boolean {
        return sharedReadable?.getBoolean(key, def) ?: def
    }
}
