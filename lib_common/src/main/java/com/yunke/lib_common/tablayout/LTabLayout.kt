package com.yunke.lib_common.tablayout

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.tabs.TabLayout


/**
 * @author Linker
 * @description:
 * @date :2022/9/19
 */
class LTabLayout : TabLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        init()
    }

    private fun init() {
        viewTreeObserver?.addOnGlobalLayoutListener(OnGlobalLayoutListener(this))
    }


    fun cancelLongClickable() {
        viewTreeObserver?.addOnGlobalLayoutListener(OnGlobalLayoutListener(this))
    }
}