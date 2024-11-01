package com.yunke.lib_common.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Service
import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Context
import android.content.Context.POWER_SERVICE
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Point
import android.net.Uri
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import androidx.core.app.ActivityCompat
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.*
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @auth : linker
 * @date : 2020/7/22 1:58 PM
 * @description ：获取应用信息工具类
 */
object AppUtil {


    /**
     * 获取版本名称
     *
     * @param context 上下文
     *
     * @return 版本名称
     */
    fun getVersionName(context: Context): String {

        //获取包管理器
        val pm = context.packageManager
        //获取包信息
        try {
            val packageInfo: PackageInfo = pm.getPackageInfo(context.packageName, 0)
            //返回版本号
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 获取版本号
     *
     * @param context 上下文
     *
     * @return 版本号
     */
    fun getVersionCode(context: Context): Int {

        //获取包管理器
        val pm = context.packageManager
        //获取包信息
        try {
            val packageInfo: PackageInfo = pm.getPackageInfo(context.packageName, 0)
            //返回版本号
            return packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 获取App的名称
     *
     * @param context 上下文
     *
     * @return 名称
     */
    fun getAppName(context: Context): String? {
        val pm = context.packageManager
        //获取包信息
        try {
            val packageInfo: PackageInfo = pm.getPackageInfo(context.packageName, 0)
            //获取应用 信息
            val applicationInfo: ApplicationInfo = packageInfo.applicationInfo
            //获取albelRes
            val labelRes: Int = applicationInfo.labelRes
            //返回App的名称
            return context.resources.getString(labelRes)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    @JvmStatic
    fun getAndroidID(context: Context): String? {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    @JvmStatic
    fun getDeviceUUid(context: Context): String? {
        val androidId: String = getAndroidID(context) ?: ""
        val deviceUuid =
            UUID(androidId.hashCode().toLong(), androidId.hashCode().toLong() shl 32)
        return deviceUuid.toString()
    }

    /**
     * 获取手机IMEI
     * 8.0及以上用方法，以下用反射获取
     */
    @SuppressLint("MissingPermission")
    @JvmStatic
    fun getPhoneIMEI(context: Context): String? {
        try {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_PHONE_STATE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                var service = context.getSystemService(Service.TELEPHONY_SERVICE)
                if (service is TelephonyManager) {
                    if (Build.VERSION.SDK_INT >= 29) {
                        return Settings.System.getString(
                            context.contentResolver, Settings.Secure.ANDROID_ID
                        )
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val id = service.imei ?: service.deviceId ?: ""
                        if (!TextUtils.isEmpty(id)) return id
                    }
                }
            }
            val slotId = 0
            val clazz = Class.forName("android.os.SystemProperties")
            val method = clazz.getMethod("get", String::class.java, String::class.java)
            var imei = method.invoke(null, "ril.gsm.imei", "") as String
            return if (!TextUtils.isEmpty(imei)) {
                val split = imei.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (split.size > slotId) {
                    imei = split[slotId]
                }
                imei
            } else {
                Settings.System.getString(
                    context.contentResolver, Settings.Secure.ANDROID_ID
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
        return ""
    }

    /**
     * 获取WIFi的MAC地址
     **/
//    @JvmStatic
//    fun getWifiMacAddress(context: Context?): String? {
//        return fetchSSIDInfo(context)?.macAddress
//    }

    /**
     * 获取WIFI信息
     **/
    @SuppressLint("MissingPermission")
    fun fetchSSIDInfo(context: Context?): WifiInfo? {
        if (context == null) return null
        val manager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return manager.connectionInfo
    }

    /**
     * 无网络连接
     */
    fun getMacAddress(): String {
        /*获取mac地址有一点需要注意的就是android 6.0版本后，以下注释方法不再适用，不管任何手机都会返回"02:00:00:00:00:00"这个默认的mac地址，这是googel官方为了加强权限管理而禁用了getSYstemService(Context.WIFI_SERVICE)方法来获得mac地址。*/
        //        String macAddress= "";
//        WifiManager wifiManager = (WifiManager) MyApp.getContext().getSystemService(Context.WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        macAddress = wifiInfo.getMacAddress();
//        return macAddress;
        try {
            var macAddress: String? = null
            val buf = StringBuffer()
            var networkInterface: NetworkInterface? = null
            try {
                networkInterface = NetworkInterface.getByName("eth1")
                if (networkInterface == null) {
                    networkInterface = NetworkInterface.getByName("wlan0")
                }
                if (networkInterface == null) {
                    return "02:00:00:00:00:02"
                }
                val addr = networkInterface.hardwareAddress
                for (b in addr) {
                    buf.append(String.format("%02X:", b))
                }
                if (buf.isNotEmpty()) {
                    buf.deleteCharAt(buf.length - 1)
                }
                macAddress = buf.toString()
            } catch (e: SocketException) {
                e.printStackTrace()
                return "02:00:00:00:00:02"
            }
            return macAddress
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 获取外网的IP(要访问Url，要放到后台线程里处理)
     *
     * @param @return
     * @return String
     * @throws
     * @Title: GetNetIp
     * @Description:
     */
    @JvmStatic
    fun getNetIp(): String {
        var infoUrl: URL? = null
        var inStream: InputStream? = null
        var ipLine = ""
        var httpConnection: HttpURLConnection? = null
        try {
//            infoUrl = URL("http://ip168.com/");
//            infoUrl = URL("http://a.vlion.cn/ssp")
//            infoUrl = URL("http://www.baidu.com")
            infoUrl = URL("http://pv.sohu.com/cityjson?ie=utf-8")
            val connection: URLConnection = infoUrl.openConnection()
            httpConnection = connection as HttpURLConnection
            val responseCode = httpConnection!!.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.inputStream
                val reader = BufferedReader(
                    InputStreamReader(inStream, "utf-8")
                )
                val strber = java.lang.StringBuilder()
                var line: String? = null
                while (reader.readLine().also { line = it } != null) {
                    strber.append(
                        """
                            $line
                            
                            """.trimIndent()
                    )
                }
                val pattern: Pattern = Pattern
                    .compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))")
                val matcher: Matcher = pattern.matcher(strber.toString())
                if (matcher.find()) {
                    ipLine = matcher.group()
                }
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inStream?.close()
                httpConnection!!.disconnect()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
        }
        Log.e("getNetIp", ipLine)
        return ipLine
    }


    /**
     * 网络ip，需连接网络
     */
    @JvmStatic
    fun getIpAddressString(): String {
        try {
            for (enNetI in NetworkInterface.getNetworkInterfaces()) {
                for (inetAddress in enNetI.inetAddresses) {
                    if (!(inetAddress !is Inet4Address || inetAddress.isLoopbackAddress())) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "0.0.0.0"
    }

    /**
     * 获取厂商名
     */
    @JvmStatic
    fun getDeviceManufacturer(): String? {
        return Build.MANUFACTURER
    }

    /**
     * 获取产品名
     */
    @JvmStatic
    fun getDeviceProduct(): String? {
        return Build.PRODUCT
    }

    /**
     * 获取手机品牌
     */
    @JvmStatic
    fun getDeviceBrand(): String? {
        return Build.BRAND
    }

    /**
     * 获取手机型号
     */
    @JvmStatic
    fun getDeviceModel(): String? {
        return Build.MODEL
    }

    /**
     * 获取手机主板名
     */
    @JvmStatic
    fun getDeviceBoard(): String? {
        return Build.BOARD
    }

    /**
     * 设备名
     */
    @JvmStatic
    fun getDevice(): String? {
        return Build.DEVICE
    }

    /**
     * 获取手机Android 系统SDK
     *
     * @return
     */
    @JvmStatic
    fun getDeviceSDK(): Int {
        return Build.VERSION.SDK_INT
    }

    /**
     * 获取手机Android 版本
     *
     * @return
     */
    @JvmStatic
    fun getDeviceAndroidVersion(): String? {
        return Build.VERSION.RELEASE
    }

    /**
     * 获取状态栏高度
     */
    @JvmStatic
    fun getStatusBarHeight(): Int {
        val resources: Resources = Resources.getSystem()
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }


    /**
     * 获取虚拟按键的高度
     * @return
     */
    @JvmStatic
    fun getNavigationBarHeight(context: Context): Int {
        var navigationBarHeight = 0
        val rs: Resources = context.resources
        val id = rs.getIdentifier("navigation_bar_height", "dimen", "android")
        if (id > 0 && hasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id)
        }
        return navigationBarHeight
    }

    /**
     * 是否存在虚拟按键
     * @return
     */
    private fun hasNavigationBar(context: Context): Boolean {
        var hasNavigationBar = false
        val rs: Resources = context.resources
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: java.lang.Exception) {
        }
        return hasNavigationBar
    }


    //判断当前界面显示的是哪个Activity
    @JvmStatic
    fun getTopActivity(context: Context): String? {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val cn: ComponentName = am.getRunningTasks(1)[0].topActivity!!
        return cn.className
    }


    /**
     * 获取系统休眠时间
     */
    fun getDormant(context: Context): Int {
        var result = 0
        try {
            result = Settings.System.getInt(
                context.contentResolver,
                Settings.System.SCREEN_OFF_TIMEOUT
            )
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * 设置系统的休眠时间
     */
    fun setDormant(time: Int, context: Context) {
        Log.i("testTEst", "-----休眠时间 time  " + time)
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_OFF_TIMEOUT, time
        )

        val uri: Uri = Settings.System
            .getUriFor(Settings.System.SCREEN_OFF_TIMEOUT)
        context.contentResolver.notifyChange(uri, null)
    }


    @SuppressLint("InvalidWakeLockTag")
    fun setWakeLock(context: Context) {
        try {
            val pm = context.getSystemService(POWER_SERVICE) as PowerManager
            @SuppressLint("InvalidWakeLockTag") val wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "TAG")
            wakeLock.acquire()


            Handler().postDelayed({ wakeLock.release() }, 3 * 1000.toLong())

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("testTAGG", "$e")
        }

    }

    private var mPowerManager: PowerManager? = null
    private val mWakeLock: WakeLock? = null

    /**
     * @param view 熄屏并延时亮屏
     */
    fun checkScreenOffAndDelayOn(context: Context) {
        mPowerManager = context.getSystemService(POWER_SERVICE) as PowerManager
        var policyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        var adminReceiver = ComponentName(context, DeviceAdminReceiver::class.java)
        val admin: Boolean = policyManager.isAdminActive(adminReceiver)
        if (admin) {
            policyManager.lockNow()
            Handler().post {

            }
//            handler.sendEmptyMessageDelayed(1, 3000)
        } else {
            ToastUtils.show(context, "没有设备管理权限")
        }
    }

    fun copyText(text: String, context: Context) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // When setting the clip board text.
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", text))
        // Only show a toast for Android 12 and lower.
        ToastUtils.show(context, "复制成功")
    }


    fun displayMetrics(activity: Activity) {
        val localDisplayMetrics = DisplayMetrics()
        try {
            val localDisplay: Display = activity.window.windowManager.defaultDisplay
            localDisplay.getMetrics(localDisplayMetrics)
            val localPoint = Point()
            Display::class.java.getMethod("getRealSize", *arrayOf<Class<*>>(Point::class.java)).invoke(localDisplay, arrayOf<Any>(localPoint))
            val l: Int = localPoint.x
            val k: Int = localPoint.y
            val sss = "$l x $k"
            val dpi: String = java.lang.String.valueOf(localDisplayMetrics.densityDpi).toString() + " dpi"

            Log.i(
                "displayMetrics", "分辨率为:" + sss +
                        "-----像素密度为:-+-" + dpi
            )
        } catch (e: java.lang.Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

}