package com.yunke.lib_common

import android.content.Context


/**
 * @author Linker
 * @description:
 * @date :2023/6/28
 */
object BaseApplication {

    @JvmStatic    private lateinit var mContext: Context

    val payTime = 120


    fun init(context: Context) {
        mContext = context
    }

    @JvmStatic
    fun getContext(): Context = mContext


}