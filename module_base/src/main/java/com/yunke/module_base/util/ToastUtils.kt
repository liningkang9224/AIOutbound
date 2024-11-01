package com.yunke.module_base.util

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.yunke.lib_common.util.DisplayUtil
import com.yunke.module_base.R

object ToastUtils {

    fun show(context: Context?, message: String?) {
        context?.let {
            if (message != null && message != "") {
                var toast = Toast(it)
                toast.duration = Toast.LENGTH_SHORT
                var mView = View.inflate(context, R.layout.view_toast, null)
                var mTvText = mView.findViewById<TextView>(R.id.toast_tv)
                mTvText.text = message
                toast.view = mView
                toast.setGravity(Gravity.CENTER, 0, DisplayUtil.dip2px(it, 50f))
                toast.show()
            }
        }
    }
}
