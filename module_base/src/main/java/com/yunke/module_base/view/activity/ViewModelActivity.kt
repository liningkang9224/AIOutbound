package com.yunke.module_base.view.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import com.yunke.module_base.util.ToastUtils
import com.yunke.module_base.view.dialog.MessageDialog
import com.yunke.module_base.vm.BaseViewModel
import com.yunke.module_base.view.activity.BaseAppCompatActivity
import kotlinx.coroutines.cancel
import java.lang.reflect.ParameterizedType


/**
 * @author Linker
 * @description:绑定viewmodel,供其他activity继承
 * @date :2021/3/1
 */
abstract class ViewModelActivity<VB : ViewBinding, VM : BaseViewModel> : BaseAppCompatActivity<VB>() {

    private var mViewModel: VM? = null
    private var mMessageDialog: MessageDialog? = null
    protected open fun viewModel() = mViewModel
    protected open fun messageDialog() = mMessageDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        getTClass()?.let {
            mViewModel = ViewModelProvider(this).get(it)
        }
        super.onCreate(savedInstanceState)
        mMessageDialog = MessageDialog(getContext())
        setLiveDataListener()

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

    /**
     * 设置监听
     */
    protected open fun setLiveDataListener() {
        viewModel()?.showLoading?.observe(this) {
            showLoading(it ?: "Loading...")
        }
        viewModel()?.dismissLoading?.observe(this) {
            dismissLoading()
        }
        viewModel()?.msgLiveData?.observe(this) {
            it?.apply {
                ToastUtils.show(getContext(), it)
            }
        }
        viewModel()?.msgDialogLd?.observe(this) {
            it?.apply {
                if (mMessageDialog?.isShowing == false) {
                    mMessageDialog?.onShow()?.setContent(it)?.setCancel(null)
                        ?.setTitle(getContext().getString(com.yunke.lib_common.R.string.tips))
                        ?.setConfirm(getContext().getString(com.yunke.lib_common.R.string.ok))
                }
            }
        }
    }


    override fun onDestroy() {
        clearData()
        super.onDestroy()
    }

    /**
     * 清空数据
     */
    private fun clearData() {
        if (mMessageDialog?.isShowing == true) {
            mMessageDialog?.dismiss()
        }
        mMessageDialog = null
        mViewModel?.viewModelScope?.cancel()
        mViewModel = null
    }
}