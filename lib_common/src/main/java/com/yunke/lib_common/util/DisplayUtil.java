package com.yunke.lib_common.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 手机屏幕
 * 像素密度转换
 */

public class DisplayUtil {

    private static int windowWidth = 0;
    private static int windowHeight = 0;

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static float dip2px_f(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return  (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    public static float px2dip_f(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }

    public static int getWidth(Context context) {
        if (windowWidth == 0) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            windowWidth = dm.widthPixels;
        }
        return windowWidth;
    }

    public static int getHeight(Context context) {
        if (windowHeight == 0) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            windowHeight = dm.heightPixels;
        }
        return windowHeight;
    }

    /**
     * 获取手机getDensityDpi
     *
     * @return
     */
    public static int getDensityDpi(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return metrics.densityDpi;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metrics = context
                .getResources()
                .getDisplayMetrics();
        return metrics;
    }

}
