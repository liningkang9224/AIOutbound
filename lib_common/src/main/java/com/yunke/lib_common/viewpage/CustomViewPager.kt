package com.yunke.lib_common.viewpage

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

/**
 * @auth : linker
 * @date : 2020/8/5 5:35 PM
 * @description ：解决嵌套在recycler中高度不显示问题
 *                重新测量高度
 */
class CustomViewPager : ViewPager {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //先测量获取子view的信息
        var heightMeasureSpec = heightMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (heightMeasureSpec <= 0) {
            var height = 0
            //下面遍历所有child的高度
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                child.measure(
                    widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                )
                val h = child.measuredHeight
                if (h > height) //采用最大的view的高度。
                    height = h
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                height,
                MeasureSpec.EXACTLY
            )
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }


}