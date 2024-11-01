package com.yunke.lib_common.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yunke.lib_common.bean.ViewPagerBean
/**
 * @auth : linker
 * @date : 2020/7/28 3:11 PM
 * @description ：FragmentStatePagerAdapter 保留状态,回收fragment，适用较多fragment
 * viewpager适配器
 */
class ViewPagerAdapter(
    private val mList: List<ViewPagerBean>,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return mList[position].fragment
    }

    override fun getCount(): Int = mList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return mList[position].tabName
    }


}