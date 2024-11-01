package com.yunke.lib_common.recycler

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller

/**
 * @author Linker
 * @description:指定滑动到某个位置，并且置顶
 * @date :2022/4/19
 */
class TopSmoothScroller(var context: Context) : LinearSmoothScroller(context) {

    override fun getHorizontalSnapPreference(): Int = SNAP_TO_START

    override fun getVerticalSnapPreference(): Int = SNAP_TO_START
}