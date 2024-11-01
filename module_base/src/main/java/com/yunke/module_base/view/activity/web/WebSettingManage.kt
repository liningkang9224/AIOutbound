package com.yunke.module_base.view.activity.web

import NativeJavascriptInterface
import android.content.Context
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView

object WebSettingManage {

    fun init(context: Context, mWebView: WebView, mListener: NativeJavascriptInterface.OnJavascriptInterface) {
        var webSettings = mWebView.settings
        //当WebView加载的链接为Https开头，图片为Http链接
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings?.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        }
        webSettings?.javaScriptEnabled = true
        webSettings?.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings?.loadWithOverviewMode = true // 缩放至屏幕的大小
        webSettings?.setSupportZoom(false) //支持缩放，默认为true。是下面那个的前提。
        webSettings?.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings?.displayZoomControls = false //隐藏原生的缩放控件
        webSettings?.allowFileAccess = true //设置可以访问文件
        webSettings?.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        webSettings?.loadsImagesAutomatically = true //支持自动加载图片
        webSettings?.defaultTextEncodingName = "utf-8" //设置编码格式
        webSettings?.domStorageEnabled = true//开启本地DOM存储
        webSettings?.cacheMode = WebSettings.LOAD_NO_CACHE

        /**
         * 添加js方法通信
         */
        NativeJavascriptInterface().init(context, mWebView, mListener)
    }
}