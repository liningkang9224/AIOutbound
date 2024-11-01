package com.yunke.lib_common.recycler.manager

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class NonSwipeableLinearLayoutManager(context: Context?) : LinearLayoutManager(context) {
    override fun canScrollHorizontally(): Boolean {
        return false
    }

    override fun canScrollVertically(): Boolean {
        return false
    }
}