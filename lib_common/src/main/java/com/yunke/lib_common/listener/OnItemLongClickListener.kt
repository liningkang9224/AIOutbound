package com.yunke.lib_common.listener

/**
 * @auth : linker
 * @date : 2020/11/5 2:44 PM
 * @description ：item长按监听回调
 */
interface OnItemLongClickListener<T> {
    fun onItemLongClick(position: Int, data: T)
}