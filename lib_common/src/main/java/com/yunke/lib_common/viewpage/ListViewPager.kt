package com.yunke.lib_common.viewpage

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import java.lang.Exception
import java.lang.reflect.Field

/**
 * @author Linker
 * @description:
 * @date :2022/4/20
 */
class ListViewPager(context: Context, attributeSet: AttributeSet?) : ChildViewPager(context, attributeSet) {
    private var mIsDisallowIntercept = false

    @SuppressLint("SoonBlockedPrivateApi")
    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        val clazz: Class<*> = ViewGroup::class.java
        // FLAG_DISALLOW_INTERCEPT = 0x80000;
        //     1000 0000 0000 0000 0000       0x80000
        //10 1100 0100 0000  0101  0011     2900051
        //10 0010 0100 0100  0101  0011     2245715


        // FLAG_DISALLOW_INTERCEPT = 0x80000;
        //     1000 0000 0000 0000 0000       0x80000
        //10 1100 0100 0000  0101  0011     2900051
        //10 0010 0100 0100  0101  0011     2245715
        try {
            val mGroupFlagsField: Field = clazz.getDeclaredField("mGroupFlags")
            mGroupFlagsField.isAccessible = true
            val c = mGroupFlagsField.get(this) as Int
            if (disallowIntercept) {
                //2900051&FLAG_DISALLOW_INTERCEPT =true
                mGroupFlagsField.set(this, 2900051)
            } else {
                //2245715&FLAG_DISALLOW_INTERCEPT =fasle
                mGroupFlagsField.set(this, 2245715)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return if (ev!!.pointerCount > 1 && mIsDisallowIntercept) {
            requestDisallowInterceptTouchEvent(false)
            val handled = super.dispatchTouchEvent(ev)
            requestDisallowInterceptTouchEvent(true)
            handled
        } else {
            super.dispatchTouchEvent(ev)
        }
    }
}