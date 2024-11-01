package com.yunke.lib_common.viewpage

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller


/**
 * @author Linker
 * @description: viewpager滑动速度
 * @date :2022/9/9
 */
class FixedSpeedScroller : Scroller {

    private var mDuration = 550

    constructor(context: Context) : this(context, null)

    constructor(context: Context, interpolator: Interpolator?) : super(context, interpolator)

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    fun setDuration(mDuration: Int) {
        this.mDuration = mDuration
    }

}