package com.yunke.aioutbound

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.yunke.lib_common.BaseApplication


/**
 * @author Linker
 * @description:
 * @date :2024/10/31
 */
class AIApplication:Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        BaseApplication.init(this)
    }
    override fun onCreate() {
        super.onCreate()
        initARouter()
    }

    /**
     * 初始化路由
     */
    private fun initARouter() {
        ARouter.openDebug()
        ARouter.openLog()
        ARouter.init(this)
    }
}