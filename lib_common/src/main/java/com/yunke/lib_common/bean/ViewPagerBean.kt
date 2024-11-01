package com.yunke.lib_common.bean

import androidx.fragment.app.Fragment

/**
 * @author Linker
 * @description: viewpager适配器对象
 * @date :2022/3/30
 */
data class ViewPagerBean(
    var tabName: String = "",
    var fragment: Fragment
)