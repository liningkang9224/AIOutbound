package com.yunke.module_base.util

import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat

object FilePathUtils {

    const val DOWNLOAD = "/download"

    /**
     * 获取根目录路径
     */
    fun getRootPath(context: Context): String {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)
            ContextCompat.getExternalFilesDirs(
                context, null
            )[0].absolutePath
        else
            Environment.getExternalStorageDirectory().absolutePath
    }
}