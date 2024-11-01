package com.yunke.module_base.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.yunke.lib_common.listener.ViewClick
import com.yunke.lib_common.util.DisplayUtil
import com.yunke.lib_common.util.ThemeManage
import com.yunke.module_base.R

/**
 * @author Linker
 * @description:
 * @date :2022/4/12
 */
class MessageDialog(context: Context) : AlertDialog(context) {

    private var mListener: OnClickListener? = null
    private var mTvTitle: TextView? = null
    private var mTvContent: TextView? = null
    private var mTvCancel: TextView? = null
    private var mTvConfirm: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.dialog_message)
        init()
    }

    private fun init() {
        mTvTitle = findViewById(R.id.message_tv_title)
        mTvContent = findViewById(R.id.message_tv_content)
        mTvCancel = findViewById(R.id.dialog_tv_cancel)
        mTvConfirm = findViewById(R.id.dialog_tv_confirm)

        ThemeManage.setShapeSolidColor(mTvConfirm)
        ThemeManage.setShapeStrokeColor(mTvCancel)
        ThemeManage.setTextColor(mTvCancel)
        setListener()
    }


    private fun setListener() {
        ViewClick.setOnTouchListener(mTvConfirm) {
            mListener?.onConfirm()
            dismiss()
        }
        ViewClick.setOnTouchListener(mTvCancel) {
            mListener?.onCancel()
            dismiss()
        }
    }

    override fun dismiss() {
        super.dismiss()
    }


    fun setTitle(title: String): MessageDialog {
        mTvTitle?.text = title
        return this
    }

    fun setContent(content: String?): MessageDialog {
        mTvContent?.text = content
        return this
    }

    fun setCancel(cancel: String?): MessageDialog {
        cancel?.let {
            mTvCancel?.visibility = View.VISIBLE
            mTvCancel?.text = cancel
            return this
        }
        mTvCancel?.visibility = View.GONE
        var params = mTvConfirm?.layoutParams as ConstraintLayout.LayoutParams
        var left = context.resources.getDimensionPixelSize(com.yunke.lib_common.R.dimen.activity_horizontal_margin)
//        var top = DisplayUtil.dip2px(context, 20f)
        params.setMargins(left, left, left, left)
        mTvConfirm?.layoutParams = params
        mTvConfirm?.background = context.getDrawable(R.drawable.shape_app_bt_bg_p)
        return this
    }

    fun setConfirm(confirm: String?): MessageDialog {
        mTvConfirm?.text = confirm
        return this
    }

    fun setOnClickListener(listener: OnClickListener): MessageDialog {
        mListener = listener
        return this
    }

    interface OnClickListener {
        fun onConfirm()
        fun onCancel()
    }

    fun onShow(): MessageDialog {
        super.show()
        var params = window?.attributes
        params?.width = DisplayUtil.getWidth(context) * 3 / 5
        params?.height = DisplayUtil.getHeight(context) * 2 / 5
        window?.attributes = params
        return this
    }

    override fun onBackPressed() {
    }
}