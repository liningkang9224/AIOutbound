package com.yunke.module_base.view.dialog

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.yunke.lib_common.util.DisplayUtil
import com.yunke.module_base.R


/**
 * @author Linker
 * @description:
 * @date :2023/7/7
 */
class AgreementDialog(context: Context) : AlertDialog(context) {

    private var mListener: OnClickListener? = null
    private var mTvTitle: TextView? = null
    private var mTvContent: TextView? = null
    private var mTvConfirm: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.dialog_agreement)
        init()
    }

    private fun init() {
        mTvTitle = findViewById(R.id.message_tv_title)
        mTvContent = findViewById(R.id.message_tv_content)
        mTvConfirm = findViewById(R.id.dialog_tv_confirm)
        mTvConfirm?.setOnClickListener {
            mListener?.onConfirm()
            dismiss()
        }
    }

    override fun dismiss() {
        super.dismiss()
    }


    fun setTitle(title: String): AgreementDialog {
        mTvTitle?.text = title
        return this
    }

    fun setContent(content: String?): AgreementDialog {
        if (content?.isNotEmpty() == true) {
            mTvContent?.text = Html.fromHtml(content)
        }
        return this
    }

    fun setConfirm(confirm: String?): AgreementDialog {
        mTvConfirm?.text = confirm
        return this
    }

    fun setOnClickListener(listener: OnClickListener): AgreementDialog {
        mListener = listener
        return this
    }

    interface OnClickListener {
        fun onConfirm()
        fun onCancel()
    }

    fun onShow(): AgreementDialog {
        super.show()
        var params = window?.attributes
        params?.width = DisplayUtil.getWidth(context) * 4 / 5 + DisplayUtil.dip2px(context, 10f)
        params?.height = DisplayUtil.getHeight(context) * 2 / 3
        window?.attributes = params
        return this
    }

    override fun onBackPressed() {
    }
}