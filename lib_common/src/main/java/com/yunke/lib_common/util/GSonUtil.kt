package com.yunke.lib_common.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * @auth : linker
 * @date : 2020/8/4 5:28 PM
 * @description ：解析类
 */
object GSonUtil {
    var gson: Gson? = null

    init {
        gson = GsonBuilder().create()
    }


    @JvmStatic
    fun toJson(any: Any): String? {
        try {
            return gson?.toJson(any)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * gson解析
     * 字符串转实体类
     */
    @JvmStatic
    fun <T> fromJson(dataJson: String?, classOfT: Class<T>): T? {
        kotlin.runCatching {
            return gson?.fromJson(dataJson, classOfT)
        }.onFailure {
            Log.e("httpJSON", it.toString())
            throw it
        }
        return null
    }

    /**
     * gson解析
     * 字符串转实体类
     */
    @JvmStatic
    fun <T> fromJson(dataJson: JsonElement?, classOfT: Class<T>): T? {
        try {
            return gson?.fromJson(dataJson, classOfT)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("httpJSON", e.toString())
        }
        return null
    }

    /**
     * gson解析
     * 字符串转实体类
     */
    @JvmStatic
    fun <T> fromJson(dataJson: String?, type: Type): T? {
        kotlin.runCatching {
            return gson?.fromJson<T>(dataJson, type)
        }.onFailure {
            Log.e("httpJSON", it.toString())
            throw it
        }
        return null
    }


    /**
     * gson解析
     * 字符串转实体类
     */
    @JvmStatic
    fun <T> fromJson(dataJson: JsonElement?, type: Type): T? {
        try {
            return gson?.fromJson<T>(dataJson, type)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("httpJSON", e.toString())
        }
        return null
    }


}