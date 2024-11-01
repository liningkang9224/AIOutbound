package com.yunke.lib_common.text

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.yunke.lib_common.R
import com.yunke.lib_common.util.ThemeManage

/**
 * @auth : linker
 * @date : 2020/10/26 7:12 PM
 * @description ：textView 工具类
 */
object TextViewUtil {
    /**
     * 设置搜索文本左边图片
     */
    fun setTextLeftImage(mContext: Context, textView: TextView, drawableId: Int) {
        if (drawableId == 0) {
            textView.setCompoundDrawables(null, null, null, null)
            return
        }
        var drawable = mContext.getDrawable(drawableId)
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        textView.setCompoundDrawables(drawable, null, null, null)
    }

    /**
     * 设置搜索文本右边图片
     */
    fun setTextRightImage(mContext: Context, textView: TextView, drawableId: Int) {
        if (drawableId == 0) {
            textView.setCompoundDrawables(null, null, null, null)
            return
        }
        var drawable = mContext.getDrawable(drawableId)
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        textView.setCompoundDrawables(null, null, drawable, null)
    }

    /**
     * 设置搜索文本上边图片
     */
    fun setTextTopImage(mContext: Context, textView: TextView, drawableId: Int) {
        if (drawableId == 0) {
            textView.setCompoundDrawables(null, null, null, null)
            return
        }
        var drawable = mContext.getDrawable(drawableId)
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        textView.setCompoundDrawables(null, drawable, null, null)
    }


    /**
     * 设置文本样式
     */
    fun setTextStyle(mContext: Context, text: String, point: String): SpannableString {
        val spannableString = SpannableString(text)
        val start = text.indexOf(point)
        val end = start + point.length
        if (start > 0 && point.length < text.length) {
            val textColorSpan = ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.app_text_sub_color))
            val pointColorSpan = ForegroundColorSpan(ThemeManage.color)
            spannableString.setSpan(textColorSpan, 0, start - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(pointColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(textColorSpan, end + 1, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return spannableString
    }
}