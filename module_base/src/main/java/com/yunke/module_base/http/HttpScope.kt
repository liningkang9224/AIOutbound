package com.yunke.module_base.http

import androidx.lifecycle.MutableLiveData
import com.yunke.lib_http.bean.ApiException
import com.yunke.lib_http.bean.ResultBean
import com.yunke.lib_http.enums.Status
import com.yunke.lib_http.http.ExceptionHandle
import kotlinx.coroutines.*


/**
 * @author Linker
 * @description: 全局的GlobalScope，通常情况不要使用，
 *               程序死亡才会销毁
 * @date :2023/3/1
 */
object HttpScope {


    /**
     * 请求数据，少使用，
     * 尽量使用 BaseViewModel 中的request
     * 因为GlobalScope是全局的
     */
    fun <T> httpRequest(
        tryBlock: suspend CoroutineScope.() -> ResultBean<T>,
        success: (T) -> Unit,
        failure: (ApiException) -> Unit = {},
        isShowDialog: Boolean = false,
        loadingMessage: String = "正在请求...",
        showLoading: MutableLiveData<String>,
        dismissLoading: MutableLiveData<String>,
        showMessage: MutableLiveData<String>,
    ) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            runCatching {
                if (isShowDialog) showLoading.postValue(loadingMessage)
                tryBlock()
            }.onSuccess {
                runCatching {
                    executeResponse(it) { t -> success(t) }
                }.onFailure { e ->
                    //请求code错误，手动抛异常
                    failure(ExceptionHandle.handleException(e))
                    showMessage.postValue(e.message)
                    scope.cancel()
                }
                if (isShowDialog) dismissLoading.postValue(loadingMessage)
            }.onFailure {
                //网络请求异常（自动抛出）
                if (isShowDialog) dismissLoading.postValue(loadingMessage)
                showMessage.postValue(it.message)
                scope.cancel()
            }
        }
    }

    fun <T> httpRequest(
        tryBlock: suspend CoroutineScope.() -> T,
        success: (T) -> Unit
    ) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            runCatching {
                tryBlock()
            }.onSuccess {
                runCatching {
                    success(it)
                }.onFailure { e ->
                    //请求code错误，手动抛异常
                    scope.cancel()
                }
            }.onFailure {
                scope.cancel()
            }
        }
    }

    /**
     * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
     */
    suspend fun <T> executeResponse(
        response: ResultBean<T>, success: suspend CoroutineScope.(T) -> Unit
    ) {
        coroutineScope {
            when (response.code) {
                Status.SUCCESS.type -> {
                    response?.data?.let {
                        success(it)
                    }
                }

                else -> {
                    throw ApiException(response.code, response.msg, null)
                }
            }
        }
    }

    fun cancel() {

    }

}