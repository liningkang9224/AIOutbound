package com.yunke.lib_common.log;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;

import com.yunke.lib_common.BaseApplication;
import com.yunke.lib_common.util.FileUtils;
import com.yunke.lib_common.util.LogUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ****************************************
 * author：chengbiao
 * time：2020/10/28
 * description：
 * ****************************************
 */
public class WriteLogUtils {
    private static final String TAG = "WriteLogUtils";
    public static final String logDirPath = Environment.getExternalStorageDirectory() + "/Android/data/" + BaseApplication.getContext().getPackageName() + "/log/";

    private static SimpleDateFormat formatMonth = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat formatMonthDay = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat formatTime = new SimpleDateFormat("MM-dd HH:mm:ss");

    private static final boolean debug = false;
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final long ONE_HOUR = 60 * 60 * 1000;
    private static final long ONE_DAY = 24 * ONE_HOUR;


    public static final String _UPLOAD = "upload";
    public static final String _Pay = "log_pay";
    public static final String _ACTION = "log_action";

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static String getCurrentDir() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(logDirPath);
        buffer.append(formatMonthDay.format(new Date()));
        buffer.append("/");
        return buffer.toString();
    }


    public static String getCurrentMonthDirPath() {
        return formatMonth.format(new Date());
    }

    /**
     * @param content
     */
    public static void writeLog(String fileName, String content, boolean isNeedTime) {
        boolean permissionGranted = ContextCompat.checkSelfPermission(BaseApplication.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        // 没有写入sd卡权限的时候不写入日志
        if (!permissionGranted) {
            return;
        }
        LogUtils.i(TAG, "fileName=" + fileName + "    " + content);
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
            executorService.execute(() -> writeLogInThread(fileName, content, isNeedTime));
        } else {
            writeLogInThread(fileName, content, isNeedTime);
        }
    }

    private static void writeCrash(String content) {

    }

    private static void writeLogInThread(String fileName, String content, boolean isNeedTime) {
        if (TextUtils.isEmpty(content)) return;
        String dirPath = getCurrentDir();
        File dir = new File(dirPath);
        long time = System.currentTimeMillis();
        if (debug) {
            System.out.println("begin-------------------" + time);
        }
        if (!dir.exists()) {
            boolean mkdirs = dir.mkdirs();
            if (!mkdirs) {
                return;
            }
        }
        File file = new File(dir, fileName);
        //文件夹创建完成
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.exists() && file.isDirectory()) {
                file.createNewFile();
            }
            if (!file.exists()) {
                return;
            }
            FileWriter fileWriter = new FileWriter(file, true);
            if (isNeedTime) {
                fileWriter.write(getCurrentTime());
            }
            fileWriter.write(content);
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (debug) {
            System.out.println("end-------------------" + (System.currentTimeMillis() - time));
        }
    }

    private static String getCurrentTime() {
        return formatTime.format(new Date());
    }

    public static void clearLogCache() {
        getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                //删除超过7天
                FileUtils.deleteFileFilterByTime(logDirPath, System.currentTimeMillis(), 7 * ONE_DAY);
            }
        });
    }

    public static void logUpload(String fileName) {
        //zip
        getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
//                zip
                String out = Environment.getExternalStorageDirectory() + "/Android/data/" + BaseApplication.getContext().getPackageName() + "/yf/" + System.currentTimeMillis() + ".zip";
                ZipUtils.toZip(logDirPath, out, true);
                //upload
                //上传

            }
        });
    }

}
