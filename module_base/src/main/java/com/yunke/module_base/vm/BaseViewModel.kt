package com.yunke.module_base.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunke.lib_common.util.LogUtils
import com.yunke.lib_http.bean.ApiException
import com.yunke.lib_http.bean.ResultBean
import com.yunke.lib_http.enums.Status
import com.yunke.module_base.http.HttpScope
import kotlinx.coroutines.*
import java.net.UnknownHostException


/**
 * @author Linker
 * @description:供其他viewmodel继承
 *              封装了网络请求及Iot请求方法
 * @date :2023/3/1
 */
open class BaseViewModel : ViewModel() {
    open val TAG = this.javaClass.simpleName
    open var pageNum = 1//列表分页页码
    open var showLoading = MutableLiveData<String>()//loading加载框
    open var dismissLoading = MutableLiveData<String>()//loading加载框
    open var msgLiveData = MutableLiveData<String>()//toast消息
    open var httpLiveData = MutableLiveData<Boolean>()//网络请求
    open var refreshLiveData = MutableLiveData<Boolean>()//刷新完成  true表示有网络,false表示没有数据
    open var loadMoreLiveData = MutableLiveData<Boolean>()//加载更多完成,false表示没有更多数据了
    open var msgDialogLd = MutableLiveData<String>()//dialog消息

    /**
     * 初次加载或下拉刷新
     */
    open fun loadData() {
        pageNum = 1
    }

    /**
     * 加载更多，具体由子类实现
     */
    open fun loadMoreData() {
        pageNum++
    }

    /**
     * 判断是否处于第一页/是否刷新操作
     */
    open fun isRefresh(): Boolean {
        return pageNum == 1
    }


    /**
     * 请求http
     * 需要处理结果
     */
    open fun <T> httpRequest(
        tryBlock: suspend CoroutineScope.() -> ResultBean<T>,
        success: (T) -> Unit,
        failure: (ApiException) -> Unit = {},
        isShowDialog: Boolean = false,
        loadingMessage: String = "Loading...",
        isDialogTips: Boolean = false,
        isMessage: Boolean = true
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                if (isShowDialog) showLoading.postValue(loadingMessage)
                tryBlock()
            }.onSuccess {
                runCatching {
                    HttpScope.executeResponse(it) { t -> success(t) }
                }.onFailure { e ->
                    LogUtils.i("ApiException", "${e.toString()}")
                    //请求code错误，手动抛异常
                    failure(com.yunke.lib_http.http.ExceptionHandle.handleException(e))
                    if (isDialogTips) {
                        msgDialogLd.postValue(e.message)
                    } else if (isMessage) {
                        msgLiveData.postValue(e.message)
                    }
                }
                requestComplete(isShowDialog, loadingMessage)
            }.onFailure {
                //网络请求异常（自动抛出）
                if (it !is UnknownHostException) {
                    msgDialogLd.postValue(it.message)
                }
                failure(com.yunke.lib_http.http.ExceptionHandle.handleException(it))
                requestComplete(isShowDialog, loadingMessage)
            }
        }
    }

    open fun <T> httpRequestT(
        tryBlock: suspend CoroutineScope.() -> T,
        success: (T) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                tryBlock()
            }.onSuccess {
                runCatching {
                    success(it)
                }.onFailure { e ->
                    //请求code错误，手动抛异常
                }
            }.onFailure {
            }
        }
    }

    /**
     * http请求
     * 无需处理结果,只需知道成功或失败
     */
    open fun <T> httpRequest(
        tryBlock: suspend CoroutineScope.() -> ResultBean<T>,
        isShowDialog: Boolean = false,
        loadingMessage: String = "请求网络中..."
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                if (isShowDialog) showLoading.postValue(loadingMessage)
                tryBlock()
            }.onSuccess {
                if (isShowDialog) dismissLoading.postValue(loadingMessage)
                httpLiveData.postValue(it.code == Status.SUCCESS.type)
            }.onFailure {
                if (isShowDialog) dismissLoading.postValue(loadingMessage)
                httpLiveData.postValue(false)
            }
        }
    }

    /**
     * 请求完成
     */
    open suspend fun requestComplete(isShowDialog: Boolean, loadingMessage: String) {
        if (isRefresh()) {
            refreshLiveData.postValue(true)
        } else {
            loadMoreLiveData.postValue(true)
        }
        delay(100)
        if (isShowDialog && null != dismissLoading) {
            dismissLoading.postValue(loadingMessage)
        }

    }


    /**
     * 取消请求
     * 界面销毁时回调该方法
     */
    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}