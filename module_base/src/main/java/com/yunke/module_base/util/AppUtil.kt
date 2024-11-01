package com.yunke.module_base.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.FileProvider
import java.io.File

//
//object AppUtil {
//
//    //安装apk
//    fun installApk(context: Activity, requestCode: Int, apkPath: String) {
//        val downloadFile = File(apkPath)
//        if (!downloadFile.exists()) {
//            return
//        }
//        val intent = Intent(Intent.ACTION_VIEW)
//        //Android7.0之后需要使用FileProvider创建Uri
//        val apkUri: Uri = getUri(context, downloadFile)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                //Android8.0开始需要获取应用内安装权限
//                val allowInstall = context.packageManager.canRequestPackageInstalls()
//                //如果还没有授权安装应用，去设置内开启应用内安装权限
//                if (!allowInstall) {
//                    //注意这个是8.0新API
//                    val packageUri = Uri.parse("package:" + context.packageName)
//                    val intentX = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri)
//                    context.startActivityForResult(intentX, requestCode)
//                    return
//                }
//            }
//            //Android N开始必须临时授权该Uri所代表的文件
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        }
//        intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        context.startActivity(intent)
//    }
//
//    //获取文件Uri
//    private fun getUri(context: Context, file: File?): Uri {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            //第二个参数为 包名.fileprovider
//            FileProvider.getUriForFile(context, context.packageName + ".provider", file!!)
//        } else {
//            Uri.fromFile(file)
//        }
//    }
//
//    /**
//     * 判断是否已经安装了
//     */
//    fun isAppInstalled(context: Context, packName: String): Boolean {
//        val pm: PackageManager = context.packageManager
//        var installed = false
//        installed = try {
//            pm.getPackageInfo(packName, PackageManager.GET_ACTIVITIES)
//            true
//        } catch (e: PackageManager.NameNotFoundException) {
//            false
//        }
//        return installed
//    }
//
//
//    fun getVersionName(context: Context): String? {
//        return context.packageManager?.getPackageInfo(context.packageName, 0)?.versionName
//    }
//}