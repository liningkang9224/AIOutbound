package com.yunke.module_base.util

import android.app.Activity


/**
 * @author Linker
 * @description:
 * @date :2022/7/7
 */
class ActivityUtils {
    private var activityList = ArrayList<Activity>()

    companion object {
        val INSTANCE: ActivityUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityUtils()
        }
    }

    fun addActivity(activity: Activity) {
        activityList.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activityList.remove(activity)
    }

    fun finishAllActivity() {
        activityList.forEach {
            it.finish()
        }
        activityList.clear()
    }

}