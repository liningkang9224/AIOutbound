package com.yunke.lib_common.viewpage

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager


/**
 * @author Linker
 * @description: 自己消费
 * @date :2022/9/8
 */
class ChildViewPager2 : ViewPager {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet) {
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        //自己处理
        parent?.requestDisallowInterceptTouchEvent(true)
        return super.onInterceptTouchEvent(ev)
    }

}