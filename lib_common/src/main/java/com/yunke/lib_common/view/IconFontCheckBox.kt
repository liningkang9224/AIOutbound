package com.yunke.lib_common.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.CompoundButton

class IconFontCheckBox: CompoundButton {
    constructor(context: Context) :this(context,null)

    constructor(context: Context, attrs: AttributeSet?) :this(context,attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        val typeface = Typeface.createFromAsset(context.assets, "fonts/iconfont.ttf")
        setTypeface(typeface)
    }

}