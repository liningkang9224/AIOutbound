package com.yunke.aioutbound.util

import android.content.Context
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter


/**
 * 路由跳转
 */
object ARouterJump {

    /**
     * 页面跳转
     */
    fun jumpPage(context: Context, path: String, bundle: Bundle? = null) {
        ARouter.getInstance().build(path).with(bundle).navigation(context)
    }

    /**
     * 页面跳转
     */
    fun jumpPage(context: Context, path: String, bundle: Bundle? = null, flags: Int) {
        ARouter.getInstance().build(path).with(bundle).addFlags(flags).navigation(context)
    }

}