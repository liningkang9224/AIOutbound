package com.yunke.module_base.view.activity.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.yunke.lib_common.listener.OnClickAnimatorListener
import com.yunke.lib_common.util.GlideUtil
import com.yunke.lib_common.util.ThemeManage
import com.yunke.lib_common.view.IconFontTextView
import com.yunke.module_base.R


/**
 * @author Linker
 * @description:公共标题类
 * @date :2024/9/9
 */
class AppToolBarView : FrameLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(context, attr, defStyleAttr) {
        mContext = context
        init(context, attr, defStyleAttr)
    }

    private var mContext: Context
    private var tvMenu: TextView? = null
    private var ibBack: ImageButton? = null
    private var tvTitle: TextView? = null

    /**
     * 初始化
     */
    @SuppressLint("MissingInflatedId")
    private fun init(context: Context, attr: AttributeSet?, defStyleAttr: Int) {
        val mToolBarView = LayoutInflater.from(context).inflate(R.layout.app_title_layout, this)
        ibBack = mToolBarView.findViewById(R.id.ibBack)
        tvTitle = mToolBarView.findViewById(R.id.tvTitle)
    }


    /**
     * 设置返回按钮监听
     */
    @SuppressLint("ClickableViewAccessibility")
    fun setBackListener(mListener: OnClickAnimatorListener) {
        ibBack?.setOnTouchListener(mListener)
    }

    /**
     * 设置菜单按钮监听
     */
    @SuppressLint("ClickableViewAccessibility")
    fun setMenuListener(mListener: OnClickAnimatorListener) {
        tvMenu?.setOnTouchListener(mListener)
    }


    /**
     * 设置标题文本
     */
    fun setTitleText(text: String) {
        setTitleText(text, null)
    }


    fun setTitleText(text: String, color: Int?) {
        color?.let {
            tvTitle?.setTextColor(ContextCompat.getColor(mContext, color))
        }
        tvTitle?.text = text
    }

    /**
     * 设置菜单文本及字体颜色
     */
    fun setMenuText(text: String) {
        setMenuText(text, null)
    }

    /**
     * 设置菜单文本及字体颜色
     */
    fun setMenuText(text: String, color: Int?) {
        color?.let {
            tvMenu?.setTextColor(ContextCompat.getColor(mContext, color))
        }
        tvMenu?.text = text
    }


    fun setBackVisibility(visibility: Int) {
        ibBack?.visibility = visibility
    }

}