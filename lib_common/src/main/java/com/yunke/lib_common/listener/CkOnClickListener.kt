package com.yunke.lib_common.listener

/**
 * @auth : linker
 * @date : 2020/8/10 11:08 AM
 * @description ：item点击监听回调
 */
interface CkOnClickListener<T> {
    fun onClick(data: T)
}