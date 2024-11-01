package com.yunke.lib_common.tablayout

import android.os.Build
import android.view.ViewTreeObserver
import com.google.android.material.tabs.TabLayout


/**
 * @author Linker
 * @description: 去掉tabLayout长按事件
 * @date :2022/9/19
 */
class OnGlobalLayoutListener(var mTabLayout: TabLayout) : ViewTreeObserver.OnGlobalLayoutListener {
    override fun onGlobalLayout() {
        mTabLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
        for (i in 0 until mTabLayout.tabCount) {
            mTabLayout.getTabAt(i)?.view?.isLongClickable = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mTabLayout.getTabAt(i)?.view?.tooltipText = null
            }
        }
    }
}