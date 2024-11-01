package com.yunke.module_base.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.yunke.lib_common.util.DisplayUtil
import com.yunke.module_base.R


/**
 * @author Linker
 * @description:
 * @date :2023/6/30
 */
class EditDialog(context: Context) : AlertDialog(context) {

    private var mListener: OnClickListener? = null
    private var mTvTitle: TextView? = null
    private var mEtContent: EditText? = null
    private var mTvCancel: TextView? = null
    private var mTvConfirm: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.dialog_edit)
        init()
    }

    private fun init() {
        mTvTitle = findViewById(R.id.message_tv_title)
        mEtContent = findViewById(R.id.message_et)
        mTvCancel = findViewById(R.id.dialog_tv_cancel)
        mTvConfirm = findViewById(R.id.dialog_tv_confirm)
        mTvConfirm?.setOnClickListener {
            mListener?.onConfirm(mEtContent?.text.toString())
            dismiss()
        }
        mTvCancel?.setOnClickListener {
            mListener?.onCancel()
            dismiss()
        }
    }

    override fun dismiss() {
        super.dismiss()
    }


    fun setTitle(title: String): EditDialog {
        mTvTitle?.text = title
        return this
    }

    fun setContent(content: String?): EditDialog {
        mEtContent?.setText(content)
        return this
    }

    fun setCancel(cancel: String?): EditDialog {
        cancel?.let {
            mTvCancel?.visibility = View.VISIBLE
            mTvCancel?.text = cancel
            return this
        }
        mTvCancel?.visibility = View.GONE
        var params = mTvConfirm?.layoutParams as ConstraintLayout.LayoutParams
        var left = DisplayUtil.dip2px(context, 20f)
        var top = DisplayUtil.dip2px(context, 0f)
        params.setMargins(left, top, left, left)
        mTvConfirm?.layoutParams = params
        return this
    }

    fun setConfirm(confirm: String?): EditDialog {
        mTvConfirm?.text = confirm
        return this
    }

    fun setOnClickListener(listener: OnClickListener): EditDialog {
        mListener = listener
        return this
    }

    interface OnClickListener {
        fun onConfirm(content: String)
        fun onCancel()
    }

    fun onShow(): EditDialog {
        super.show()
        var params = window?.attributes
        params?.width = DisplayUtil.getWidth(context) * 4 / 5
        window?.attributes = params
        return this
    }

    override fun onBackPressed() {
    }
}