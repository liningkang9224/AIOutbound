package com.yunke.lib_common.recycler

import androidx.recyclerview.widget.RecyclerView

/**
 * @author Linker
 * @description: 吸顶适配器
 * @date :2022/4/19
 */
abstract class CeilingAdapter<vh : RecyclerView.ViewHolder> : RecyclerView.Adapter<vh>() {

    abstract fun isItemHeader(position: Int): Boolean

    abstract fun getTitle(position: Int): String
}