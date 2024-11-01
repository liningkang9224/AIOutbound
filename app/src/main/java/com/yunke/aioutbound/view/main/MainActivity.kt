package com.yunke.aioutbound.view.main

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.yunke.aioutbound.R
import com.yunke.aioutbound.databinding.ActivityMainBinding
import com.yunke.aioutbound.view.main.app.AppFragment
import com.yunke.aioutbound.view.main.home.HomeFragment
import com.yunke.aioutbound.view.main.mail.MailFragment
import com.yunke.aioutbound.view.main.mine.MineFragment
import com.yunke.lib_common.router.ARouterPath
import com.yunke.module_base.view.activity.ViewModelActivity

@Route(path = ARouterPath.APP.MainActivity)
class MainActivity : ViewModelActivity<ActivityMainBinding, MainViewModel>() {

    private var mFragmentMap = HashMap<Int, Fragment>()
    private var mTabList = ArrayList<View>()
    private var selectPosition = 0

    private var mHomeFragment: HomeFragment? = null
    private var mAppFragment: AppFragment? = null
    private var mMailFragment: MailFragment? = null
    private var mMineFragment: MineFragment? = null

    override fun initData() {
        mHomeFragment = HomeFragment().apply {
            mFragmentMap[0] = this
        }
        mMailFragment = MailFragment().apply {
            mFragmentMap[1] = this
        }
        mAppFragment = AppFragment().apply {
            mFragmentMap[2] = this
        }
        mMineFragment = MineFragment().apply {
            mFragmentMap[3] = this
        }

        mTabList.add(newTab(getString(R.string.home), R.drawable.selector_tab_home_icon))
        mTabList.add(newTab(getString(R.string.mail), R.drawable.selector_tab_home_icon))
        mTabList.add(newTab(getString(R.string.app), R.drawable.selector_tab_home_icon))
        mTabList.add(newTab(getString(R.string.mine), R.drawable.selector_tab_home_icon))

        mTabList.forEachIndexed { index, tab ->
            viewBinding()?.tabView?.newTab()?.setCustomView(tab)?.apply {
                viewBinding()?.tabView?.addTab(this, index == selectPosition)
            }
        }
        addFragment()
        tabSelected(selectPosition)
    }

    /**
     * 手动选择tab
     */
    private fun tabSelected(position: Int) {
        showFragment(mFragmentMap[position])
    }

    /**
     * 加载fragment
     */
    private fun addFragment() {
        mFragmentMap.forEach {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentView, it.value).commitAllowingStateLoss()
            hideFragment(it.value)
        }
    }

    /**
     * 显示fragment
     */
    private fun showFragment(fragment: Fragment?) {
        fragment?.let {
            supportFragmentManager.beginTransaction()
                .show(fragment).commitAllowingStateLoss()
        }
    }

    override fun setListener() {
        viewBinding()?.tabView?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    hideFragment(mFragmentMap[tab?.position])
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    selectPosition = tab?.position ?: 0
                    showFragment(mFragmentMap[tab?.position])
                }
            }

        })
    }


    override fun setLiveDataListener() {
        super.setLiveDataListener()
    }

    override fun jumpToPage(viewId: Int) {
    }

    /**
     * 隐藏fragment
     */
    private fun hideFragment(fragment: Fragment?) {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss()
        }
    }

    private fun newTab(text: String, icon: Int): View {
        val tabView = LayoutInflater.from(this).inflate(R.layout.item_tab_home, null)
        val mImageView = tabView.findViewById<ImageView>(R.id.home_tab_iv)
        val mTextView = tabView.findViewById<TextView>(R.id.home_tab_tv)
        mImageView.setImageResource(icon)
        mTextView.text = text
        mTextView.visibility = View.VISIBLE
        return tabView
    }
}
