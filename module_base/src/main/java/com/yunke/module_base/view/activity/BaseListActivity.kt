package com.yunke.module_base.view.activity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yunke.module_base.view.adapter.BaseAdapter
import com.yunke.module_base.vm.BaseViewModel


/**
 * @author Linker
 * @description: 父类列表界面，适用于一个recyclerView的界面
 * @date :2021/5/30
 */
abstract class BaseListActivity<VB : ViewBinding, VM : BaseViewModel, T> : ViewModelActivity<VB, VM>() {

    protected open var mAdapter: BaseAdapter<T>? = null

    private var mList = mutableListOf<T>()

    override fun initData() {
        getRecyclerView()?.layoutManager = getLayoutManager()
        mAdapter = getBaseAdapter()
        mAdapter?.setList(mList)
        getRecyclerView()?.adapter = mAdapter
    }

    override fun setListener() {
       getRefreshLayout()?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                viewModel()?.loadData()
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel()?.loadMoreData()
            }

        })
    }

    override fun setLiveDataListener() {
        super.setLiveDataListener()
        viewModel()?.refreshLiveData?.observe(this) {
            getRefreshLayout()?.finishRefresh(true)
        }

        viewModel()?.loadMoreLiveData?.observe(this) {
            getRefreshLayout()?.finishLoadMore(true)
        }
    }

    /**
     * 是否支持下拉刷新
     */
    protected open fun setEnableRefresh(enabled: Boolean) {
        getRefreshLayout()?.setEnableRefresh(enabled)
    }

    /**
     * 是否支持上拉加载
     */
    protected open fun setEnableLoadMore(enabled: Boolean) {
        getRefreshLayout()?.setEnableLoadMore(enabled)
    }


    /**
     * recyclerView 必传
     */
    abstract fun getRecyclerView(): RecyclerView?

    abstract fun getBaseAdapter(): BaseAdapter<T>

    /**
     * 下拉刷新控件，可不传
     */
    protected open fun getRefreshLayout(): SmartRefreshLayout? = null

    protected open fun getLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(this)

}