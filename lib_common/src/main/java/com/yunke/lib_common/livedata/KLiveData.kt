package com.yunke.lib_common.livedata

import androidx.lifecycle.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.set


/**
 * @author Linker
 * @description:自定义全局的livedata
 *              接收粘性事件和非粘性事件
 * @date :2023/3/11
 */
object KLiveData {

    private val eventMap = ConcurrentHashMap<String, StickyLiveData<*>>()

    fun <T> with(eventName: String): StickyLiveData<T> {
        var liveData = eventMap[eventName]
        if (liveData == null) {
            liveData = StickyLiveData<T>(eventName)
            eventMap[eventName] = liveData
        }
        return liveData as StickyLiveData<T>
    }

    /**
     * 移除所有的livedata
     */
    fun removeAllLiveData() {
        eventMap.clear()
    }


    /**
     * 继承livedata，重写方法
     */
    class StickyLiveData<T>(private val eventName: String) : LiveData<T>() {
        var mStickyData: T? = null
        var mVersion = 0//与lastVersion版本作比较，判断是否发送过了

        /**
         * 发送livedata，在主线程中
         */
        fun setLiveData(stickyData: T) {
            mStickyData = stickyData
            setValue(stickyData)
        }

        /**
         * 发送livedata
         */
        fun postLiveData(stickyData: T) {
            mStickyData = stickyData
            postValue(stickyData)
        }

        override fun setValue(value: T) {
            mVersion++
            super.setValue(value)
        }

        override fun postValue(value: T) {
            mVersion++
            super.postValue(value)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, StickyObserver(this, false, observer))
        }

        /**
         * 粘性事件
         */
        fun observerSticky(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, StickyObserver(this, true, observer))
        }


        /**
         * 判断lifecycle生命周期
         * 销毁时移除宿主
         * 如果做成全局的，则不移除宿主（比如像eventbus那样）
         */
        fun observerSticky(owner: LifecycleOwner, sticky: Boolean, observer: Observer<in T>) {
            owner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                //监听宿主是否被销毁，销毁之后移除
                if (event == Lifecycle.Event.ON_DESTROY) {
//                    eventMap.remove(eventName)
                }
            })
            super.observe(owner, StickyObserver(this, sticky, observer))
        }
    }
}