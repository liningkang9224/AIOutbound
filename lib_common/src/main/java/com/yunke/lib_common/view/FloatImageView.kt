package com.yunke.lib_common.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yunke.lib_common.util.DisplayUtil
import kotlin.math.min


/**
 * @author Linker
 * @description: 浮窗图片
 * @date :2022/9/21
 */
class FloatImageView : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet) {
        init(context, attributeSet, defStyleAttr)
    }

    private var mPaint: Paint? = null
    private var mBitmapMap = HashMap<Long, Bitmap>()
    private var mRectList = ArrayList<Rect>()
    private var mRadius = 0


    private fun init(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint?.color = Color.WHITE
        mPaint?.isAntiAlias = true

    }

    fun removeBitmap(groupId: Long) {
        if (mBitmapMap[groupId] != null) {
            mBitmapMap.remove(groupId)
            drawBitmap()
        }
    }

    fun removeAllBitmap() {
        mBitmapMap.clear()
        invalidate()
    }


    fun addBitmap(groupId: Long, bitmap: Bitmap?) {
        if (mBitmapMap.size == 5)
            return
        bitmap?.let {
            mRadius = min(width, height) / 2
            mBitmapMap[groupId] = bitmap
            drawBitmap()
        }
    }


    /**
     * 绘制图片
     */
    private fun drawBitmap() {
        mRectList.clear()
        when (mBitmapMap.size) {
            1 -> {
                var size = DisplayUtil.dip2px(context, 36f)
                addRect(mRadius, mRadius, size / 2)
            }
            2 -> {
                var size = DisplayUtil.dip2px(context, 36f)
                var bitmapRadius = size / 2
                addRect(mRadius, mRadius, bitmapRadius)

                var center2 = mRadius + size / 2
                addRect(center2, mRadius, bitmapRadius)
            }
            3 -> {
                var size = DisplayUtil.dip2px(context, 24f)
                var bitmapRadius = size / 2
                var center1 = mRadius - size / 4
                addRect(mRadius, center1, bitmapRadius)

                var center2 = mRadius + size / 3
                addRect(center2, center2, bitmapRadius)

                var center3x = mRadius - size / 4
                var center3y = mRadius + size / 3
                addRect(center3x, center3y, bitmapRadius)
            }
            4 -> {
                var size = DisplayUtil.dip2px(context, 22f)
                var bitmapRadius = size / 2
                var center1 = mRadius - size / 2
                addRect(mRadius, center1, bitmapRadius)

                var center2 = mRadius + size / 2
                addRect(center2, mRadius, bitmapRadius)
                addRect(mRadius, center2, bitmapRadius)
                addRect(center1, mRadius, bitmapRadius)
            }
            5 -> {
                var size = DisplayUtil.dip2px(context, 20f)
                var bitmapRadius = size / 2
                var center1 = mRadius - size / 2
                addRect(mRadius, center1, bitmapRadius)

                var center2 = mRadius + size / 2
                addRect(center2, mRadius, bitmapRadius)

                var center3 = mRadius + size / 3
                addRect(center3, center2, bitmapRadius)

                var center4 = mRadius - size / 3
                addRect(center4, center2, bitmapRadius)

                var center5 = mRadius - size / 2
                addRect(center5, mRadius, bitmapRadius)

            }
        }
        invalidate()
    }

    /**
     * center 中心点
     * radius 半径
     */
    private fun addRect(centerX: Int, centerY: Int, radius: Int) {
        var mLeft = centerX - radius
        var mTop = centerY - radius
        var mRight = centerX + radius
        var mBottom = centerY + radius
        mRectList.add(Rect(mLeft, mTop, mRight, mBottom))
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        mBitmapMap.onEachIndexed { index, entry ->
            if (index < mRectList.size) {
                canvas.drawBitmap(entry.value, null, mRectList[index], null)
            }
        }
    }
}