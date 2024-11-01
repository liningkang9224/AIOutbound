package com.yunke.lib_common.animation

import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * @auth : linker
 * @date : 2020/10/22 11:55 AM
 * @description ：viewPager动画
 */
class ScalePageTransformer : ViewPager.PageTransformer {
    private val DEFAULT_MIN_SCALE = 0.85f
    private val mMinScale = DEFAULT_MIN_SCALE //view缩小值
    private val DEFAULT_CENTER = 0.5f
    private var pageWidth = 0
    private var pageHeight = 0
    override fun transformPage(view: View, position: Float) {
        pageWidth = view.width
        pageHeight = view.height

        view.pivotY = pageHeight / 2.toFloat()
        view.pivotX = pageWidth / 2.toFloat()
        when {
            position < -1.0f -> {
                // [-Infinity,-1)
                // view移动到最左边，在屏幕之外
                handleInvisiblePage(view, position)
            }
            position <= 0.0f -> {
                // [-1,0]
                // view移动到左边
                handleLeftPage(view, position)
            }
            position <= 1.0f -> {
                // view移动到右边
                handleRightPage(view, position)
            }
            else -> {
                // (1,+Infinity]
                //  view移动到右边，在屏幕之外
                view.pivotX = 0f
                view.scaleX = mMinScale
                view.scaleY = mMinScale
            }
        }
    }

    private fun handleInvisiblePage(view: View, position: Float) {
        view.scaleX = mMinScale
        view.scaleY = mMinScale
        view.pivotX = pageWidth.toFloat()
    }

    private fun handleLeftPage(view: View, position: Float) {
        val scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale
        view.scaleX = scaleFactor
        view.scaleY = scaleFactor
        view.pivotX = pageWidth * (DEFAULT_CENTER + DEFAULT_CENTER * -position)
    }

    private fun handleRightPage(view: View, position: Float) {
        val scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale
        view.scaleX = scaleFactor
        view.scaleY = scaleFactor
        view.pivotX = pageWidth * ((1 - position) * DEFAULT_CENTER)
    }
}