package com.yunke.lib_common.recycler.manager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class NonSwipeableGridLayoutManager (context: Context?, spanCount: Int) : GridLayoutManager(context, spanCount) {
    override fun canScrollHorizontally(): Boolean {
        return false
    }

    override fun canScrollVertically(): Boolean {
        return false
    }
}