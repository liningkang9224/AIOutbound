package com.yunke.module_base.view.activity.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.viewbinding.ViewBinding
import com.yunke.module_base.view.activity.ViewModelActivity
import com.yunke.module_base.vm.BaseViewModel


/**
 * @auth : linker
 * @date : 2020/9/10 10:42 AM
 * @description ：web
 */
open abstract class BaseWebActivity<VB : ViewBinding, VM : BaseViewModel> : ViewModelActivity<VB, VM>(), NativeJavascriptInterface.OnJavascriptInterface {

    open var mUrl: String? = null

    protected abstract fun getWebView(): WebView?
    protected open fun getRootView(): ViewGroup? = null
    protected open fun getProgress(): ProgressBar? = null

    override fun initData() {
        getWebView()?.apply {
            WebSettingManage.init(getContext(), this, this@BaseWebActivity)
            mUrl?.let {
                loadUrl(it)
            }
        }
    }

    @SuppressLint("JavascriptInterface")
    override fun setListener() {
        getWebView()?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                getProgress()?.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                getProgress()?.visibility = View.GONE
            }

        }
        getWebView()?.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                getProgress()?.progress = newProgress
            }
        }


    }


//    //点击返回键，返回上一个页面，而不是退出程序
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK && getWebView().canGoBack()) {
//            getWebView().goBack() // 返回前一个页面
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }

    override fun onDestroy() {
        getWebView()?.clearCache(true)
        getWebView()?.removeAllViews()
        getRootView()?.removeView(getWebView())
        super.onDestroy()
    }
}