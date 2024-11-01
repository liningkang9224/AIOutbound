package com.yunke.module_base.view.fragment

import BaseFragment
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.yunke.module_base.util.ToastUtils
import com.yunke.module_base.vm.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * viewmodel 父类
 */
abstract class ViewModelFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFragment<VB>() {

    private var mViewModel: VM? = null

    open var isLoadData = true//是否加载数据

    open var isVisibleToUser = false

    protected open fun viewModel() = mViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getTClass()?.let {
            mViewModel = ViewModelProvider(this).get(it)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setLiveDataListener()
    }

    override fun onResume() {
        super.onResume()
        onLoadData()//防止fragment嵌套时，view还没加载出来，导致无数据
    }

    /**
     * 加载数据
     */
    private fun onLoadData() {
        if (isVisibleToUser) {
            onLoadData2()
        }
    }

    /**
     * 加载数据
     * 供子类调用，部分情况isVisibleToUser不调用
     * 所以子类主动调用这个方法
     */
    open fun onLoadData2() {
        if (isLoadData) {
            viewModel()?.let {
                isLoadData = false
                loadData()
            }
        }
    }


    /**
     * 后期改方案，不用延迟
     * 判断当前view是否为空
     * time 2021-12-28
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        viewBinding()?.let {
            onLoadData()
        }
    }


    /**
     * 刷新网络，重新请求数据
     */
    open fun refreshNetwork() {
        loadData()
    }

    protected open abstract fun loadData()


    /**
     * 设置监听
     */
    @SuppressLint("FragmentLiveDataObserve")
    protected open fun setLiveDataListener() {
        viewModel()?.showLoading?.observe(this) {
            showLoading(it)
        }
        viewModel()?.dismissLoading?.observe(this) {
            dismissLoading()
        }
        viewModel()?.msgLiveData?.observe(this) {
            it?.apply {
                ToastUtils.show(mContext, it)
            }
        }
    }

    /**
     * 获取泛型对相应的Class对象
     * @return
     */
    private fun getTClass(): Class<VM>? {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        val type = this.javaClass.genericSuperclass as ParameterizedType
        //返回表示此类型实际类型参数的 Type 对象的数组()，想要获取第二个泛型的Class，所以索引写1
        return if (type.actualTypeArguments.size > 1) {
            type.actualTypeArguments[1] as Class<VM> //<T>}
        } else
            null
    }

}