package com.yunke.lib_common.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import com.yunke.lib_common.BaseApplication;

/**
 * chengbiao
 * 2024/5/8
 * desc:
 */
public class DeviceUtils {
    private static final String TAG = "DeviceUtils";

    public static String getDeviceIp() {
        try {
            WifiManager wifiManager = (WifiManager) BaseApplication.getContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                String ipHostAddress = Formatter.formatIpAddress(ipAddress);
                return ipHostAddress;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
