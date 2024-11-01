package com.yunke.lib_common.viewpage

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import com.yunke.lib_common.R

/**
 * @author Linker
 * @description: 禁止viewpager滑动
 * 用于viewpager外层嵌套
 * @date :2022/4/2
 */
open class ChildViewPager : ViewPager {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet) {
        init(context, attributeSet, defStyleAttr)
    }

    private var isScroll = false
    private var isAnimation = true

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.VariableViewPager, defStyleAttr, 0)
        isScroll = typedArray.getBoolean(R.styleable.VariableViewPager_is_scroll, false)
        isAnimation = typedArray.getBoolean(R.styleable.VariableViewPager_is_animation, true)
    }

    /**
     * 禁止滑动
     */
    override fun canScrollHorizontally(direction: Int): Boolean {
        return isScroll
    }

//    override fun setCurrentItem(item: Int) {
//        super.setCurrentItem(item, isAnimation)
//    }
}