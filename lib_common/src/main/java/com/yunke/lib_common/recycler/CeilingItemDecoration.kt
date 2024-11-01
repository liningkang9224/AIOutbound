package com.yunke.lib_common.recycler

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yunke.lib_common.R
import com.yunke.lib_common.util.DisplayUtil

/**
 * @author Linker
 * @description: 吸顶,配合适配器
 * @date :2022/4/19
 */
class CeilingItemDecoration(
    private val context: Context
) : RecyclerView.ItemDecoration() {
    //头部的高
    private var mItemHeaderHeight = 0
    private var mTextPaddingLeft = 0

    //画笔，绘制头部和分割线
    private var mItemHeaderPaint: Paint? = null
    private var mTextPaint: Paint? = null

    private var mTextRect: Rect? = null
//    private var mTitleText = ""

    init {
        mItemHeaderHeight = DisplayUtil.dip2px(context, 45f)
        mTextPaddingLeft = DisplayUtil.dip2px(context, 10f)

        mTextRect = Rect()

        mItemHeaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mItemHeaderPaint?.color = ContextCompat.getColor(context, R.color.color_ceiling_bg)

        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint?.textSize = 50f
        mTextPaint?.color = ContextCompat.getColor(context, R.color.color_ceiling_text)
        mTextPaint?.isFakeBoldText = true
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.adapter is CeilingAdapter) {
            val adapter = parent.adapter as CeilingAdapter?
            val count = parent.childCount //获取可见范围内Item的总数
            for (i in 0 until count) {
                val view = parent.getChildAt(i)
                val position = parent.getChildLayoutPosition(view)
                val isHeader = adapter!!.isItemHeader(position)
                val left = parent.paddingLeft
                val right = parent.width - parent.paddingRight
                if (isHeader) {
                    var title = adapter.getTitle(position)
                    c.drawRect(left.toFloat(), (view.top - mItemHeaderHeight).toFloat(), right.toFloat(), view.top.toFloat(), mItemHeaderPaint!!)
                    mTextPaint?.getTextBounds(title, 0, title.length, mTextRect)
                    c.drawText(
                        title,
                        (left + mTextPaddingLeft).toFloat(),
                        (view.top - mItemHeaderHeight + mItemHeaderHeight / 2 + mTextRect!!.height() / 2).toFloat(),
                        mTextPaint!!
                    )
                }
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.adapter is CeilingAdapter) {
            val adapter = parent.adapter as CeilingAdapter?
            if (adapter!!.itemCount > 0) {
                val position = (parent.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
                val view = parent.findViewHolderForAdapterPosition(position)!!.itemView
                val isHeader = adapter.isItemHeader(position + 1)
                val top = parent.paddingTop
                val left = parent.paddingLeft
                val right = parent.width - parent.paddingRight
                var title = adapter.getTitle(position)
                if (isHeader) {
                    val bottom = Math.min(mItemHeaderHeight, view.bottom)
                    c.drawRect(
                        left.toFloat(), (top + view.top - mItemHeaderHeight).toFloat(), right.toFloat(), (top + bottom).toFloat(),
                        mItemHeaderPaint!!
                    )

                    mTextPaint!!.getTextBounds(title, 0, title.length, mTextRect)
                    c.drawText(
                        title,
                        (left + mTextPaddingLeft).toFloat(),
                        (top + mItemHeaderHeight / 2 + mTextRect!!.height() / 2 - (mItemHeaderHeight - bottom)).toFloat(),
                        mTextPaint!!
                    )
                } else {
                    c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), (top + mItemHeaderHeight).toFloat(), mItemHeaderPaint!!)
                    mTextPaint!!.getTextBounds(title, 0, title.length, mTextRect)
                    c.drawText(
                        title,
                        (left + mTextPaddingLeft).toFloat(),
                        (top + mItemHeaderHeight / 2 + mTextRect!!.height() / 2).toFloat(),
                        mTextPaint!!
                    )
                }
                c.save()
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.adapter is CeilingAdapter) {
            val adapter = parent.adapter as CeilingAdapter?
            if (adapter!!.itemCount > 0) {
                val position = parent.getChildLayoutPosition(view)
                val isHeader = adapter.isItemHeader(position)
                if (isHeader) {
                    outRect.top = mItemHeaderHeight
                } else {
                    outRect.top = 0
                }
            }
        }
    }


}