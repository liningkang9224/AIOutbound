package com.yunke.lib_common.livedata

import androidx.lifecycle.Observer


/**
 * @author Linker
 * @description:继承Observer
 *              处理粘性事件
 * @date :2023/3/11
 */
class StickyObserver<T>(
    private val stickyLiveData: KLiveData.StickyLiveData<T>,
    private val sticky: Boolean,
    private val observer: Observer<in T>,
) :
    Observer<T> {
    //为了控制粘性事件的分发
    private var lastVersion = stickyLiveData.mVersion

    override fun onChanged(t: T) {
        //观察者接收的消息数大于等于livedata发送的消息数
        if (lastVersion >= stickyLiveData.mVersion) {
            //sticky 是否为粘性事件
            stickyLiveData.mStickyData?.let {
                if (sticky) {
                    observer.onChanged(it)
                }
            }

            return
        }
        lastVersion = stickyLiveData.mVersion
        observer.onChanged(t)
    }

}