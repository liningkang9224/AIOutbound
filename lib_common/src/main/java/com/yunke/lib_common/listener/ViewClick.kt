package com.yunke.lib_common.listener

import android.view.View


/**
 * @author Linker
 * @description: 点击事件处理
 * @date :2024/9/10
 */
object ViewClick {


    /**
     * 设置Touch监听
     * 带有动画效果
     * 防止误触
     */
    fun setOnTouchListener(view: View?, tryBlock: () -> Unit) {
        view?.setOnTouchListener(object : OnClickAnimatorListener() {
            override fun onClickListener(p0: View?) {
                tryBlock()
            }
        })
    }

    /**
     * 设置Click监听
     * 防止误触
     */
    fun setOnClickListener(view: View?, tryBlock: () -> Unit) {
        view?.setOnClickListener(object : OnClickGapFilterListener() {
            override fun onClickGapFilter(p0: View?) {
                tryBlock()
            }
        })
    }
}