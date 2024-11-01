package com.yunke.lib_common.listener

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yunke.lib_common.R
import com.yunke.lib_common.log.WriteLogUtils
import com.yunke.lib_common.util.LogUtils


/**
 * @author Linker
 * @description:事件点击,防止误触
 * @date :2023/3/1
 */
abstract class OnClickGapFilterListener : View.OnClickListener {
    private var lastTime = 0L

    override fun onClick(p0: View?) {
        var currTime = System.currentTimeMillis()
        if (currTime - lastTime > 800) {
            lastTime = currTime
            onClickGapFilter(p0)
            writeLog(p0)
        }
    }
    /**
     * 写入日志
     */
    private fun writeLog(view: View?) {
        kotlin.runCatching {
            WriteLogUtils.writeLog(WriteLogUtils._ACTION, getText(view), true)
        }
    }

    private fun getText(view: View?): String? {
        return when (view) {
            is TextView -> {
                "${getTextInfo(view)} text=${view?.text?.toString()}"
            }

            is ViewGroup -> {
                getViewGroupText(view)
            }

            else -> {
                getTextInfo(view)
            }
        }
    }


    private fun getViewGroupText(viewGroup: ViewGroup): String? {
        for (i in 0 until viewGroup.childCount) {
            if (viewGroup.getChildAt(i) is TextView) {
                if ((viewGroup.getChildAt(i) as TextView)?.text?.isNotEmpty() == true) {
                    if (viewGroup.context.getString(R.string.sold_out) != (viewGroup.getChildAt(i) as TextView)?.text?.toString()) {
                        return getText(viewGroup.getChildAt(i))
                    }
                }

            }
        }
        return getTextInfo(viewGroup)
    }

    private fun getTextInfo(view: View?): String? {
        view?.id?.let {
            if (it > 0) {
                return "{view=${view?.javaClass?.simpleName}  ${view?.resources?.getResourceName(it)}}"
            }
        }
        return "{view=${view?.javaClass?.simpleName}}"
    }


    protected abstract fun onClickGapFilter(p0: View?)
}