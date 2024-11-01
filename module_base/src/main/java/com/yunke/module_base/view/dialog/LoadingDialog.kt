package com.yunke.module_base.view.dialog

import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.yunke.module_base.R

/**
 * 加载等待loading
 */
class LoadingDialog(context: Context) : AlertDialog(context, R.style.AppDialog) {

    private var loadingTv: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setCanceledOnTouchOutside(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
        loadingTv = findViewById(R.id.loading_tv)
        val rootView = findViewById<LinearLayout>(R.id.root_view)
//        ThemeManage.setShapeSolidColor64(rootView)
    }

    /**
     * 设置等待文案
     */
    fun setText(string: String?): LoadingDialog {
        string?.let {
            loadingTv?.text = it
        }
        return this
    }

    /**
     * 显示加载框
     */
    fun onShow(): LoadingDialog? {
        if (!isShowing) {
            if (null == context) {
                return null
            }
            super.show()
        }
        return this
    }

    fun onDismiss() {
        if (isShowing) {
            dismiss()
        }
    }
}