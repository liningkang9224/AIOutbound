package com.yunke.lib_common.listener

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.MotionEvent
import android.view.View


/**
 * @author Linker
 * @description: 点击事件+动画
 * @date :2024/8/8
 */
abstract class OnClickAnimatorListener : View.OnTouchListener {

    private var lastTime = 0L
    private val INTERVAL_TIME = 500//间隔时间
    private val DURATION_TIME = 100L//动画时间
    private val ANIMATED_VALUE = 0.96f//缩放大小

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startDownAnimator(v)
            }

            MotionEvent.ACTION_UP -> {
                startUpAnimator(v)
            }
        }
        return true
    }


    /**
     * 开始按下动画
     */
    private fun startDownAnimator(view: View?) {
        ObjectAnimator.ofFloat(1f, ANIMATED_VALUE).apply {
            duration = DURATION_TIME
            addUpdateListener {
                view?.scaleX = animatedValue as Float
                view?.scaleY = animatedValue as Float
            }
            start()
        }
    }

    /**
     * 开始松开动画
     */
    private fun startUpAnimator(view: View?) {
        ObjectAnimator.ofFloat(ANIMATED_VALUE, 1f).apply {
            duration = DURATION_TIME
            addUpdateListener {
                view?.scaleX = animatedValue as Float
                view?.scaleY = animatedValue as Float
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    var currTime = System.currentTimeMillis()
                    if (currTime - lastTime > INTERVAL_TIME) {
                        lastTime = currTime
                        onClickListener(view)
                    }
                }
            })
            start()
        }
    }

    protected abstract fun onClickListener(p0: View?)
}