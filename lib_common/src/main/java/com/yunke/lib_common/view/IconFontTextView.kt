package com.yunke.lib_common.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class IconFontTextView: AppCompatTextView {
    constructor(context:Context) :this(context,null)

    constructor(context: Context, attrs: AttributeSet?) :this(context,attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        val typeface = Typeface.createFromAsset(context.assets, "fonts/iconfont.ttf")
        setTypeface(typeface)
    }

    /**
     * 设置图标颜色
     * @param color 颜色值
     */
    fun setIconColor(color: Int) {
        setTextColor(color)
    }
}