package com.yunke.lib_common.text

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.yunke.lib_common.R

/**
 * @auth : linker
 * @date : 2020/11/2 2:05 PM
 * @description ：
 */
@SuppressLint("AppCompatCustomView")
class AutoLinkStyleTextView : TextView {
    private var clickCallBackListener: OnClickCallBackListener? = null
    private var textValue = "1"
    private var DEFAULT_TEXT_VALUE_COLOR: Int = 0xFF000000.toInt()
    private var textValueColor = DEFAULT_TEXT_VALUE_COLOR
    private var textBgValueColor: Int = 0x00000000

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        init(context, attributeSet, defStyleAttr);
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.AutoLinkStyleTextView,
            defStyleAttr,
            0
        )
        textValue = typedArray.getString(R.styleable.AutoLinkStyleTextView_text_value).toString()
        textValueColor = typedArray.getColor(
            R.styleable.AutoLinkStyleTextView_text_value_color,
            DEFAULT_TEXT_VALUE_COLOR
        )
        addStyle()
        typedArray.recycle()
    }

    private fun addStyle() {
        try {
            if (!TextUtils.isEmpty(textValue) && textValue.contains(", ")) {
                val values: List<String> = textValue.split(", ")
                val spannableString = SpannableString(text.toString().trim())
                if (spannableString.isNotEmpty()) {
                    for (i in values.indices) {
                        spannableString.setSpan(
                            object : ClickableSpan() {
                                override fun onClick(widget: View) {
                                    clickCallBackListener?.onClick(i)

                                }

                                override fun updateDrawState(ds: TextPaint) {
                                    super.updateDrawState(ds)
                                    ds.color = textValueColor
                                    ds.isUnderlineText = false
                                }
                            },
                            text.toString().trim().indexOf(values[i]),
                            text.toString().trim().indexOf(values[i]) + values[i].length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
                text = spannableString
                movementMethod = LinkMovementMethod.getInstance()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setOnClickCallBack(clickListener: OnClickCallBackListener) {
        clickCallBackListener = clickListener
    }

    interface OnClickCallBackListener {
        fun onClick(position: Int)
    }
}