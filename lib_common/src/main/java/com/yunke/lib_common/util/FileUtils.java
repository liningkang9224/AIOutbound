package com.yunke.lib_common.util;

import android.text.TextUtils;

import java.io.File;

/**
 * chengbiao
 * 2024/5/9
 * desc:
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

    /**
     * 检测文件是否存在，不存在则创建
     *
     * @param filePath
     */
    public static void fileCheck(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            LogUtils.d(TAG, "fileCheck -> filePath is empty");
            return;
        }

        String dir = filePath.substring(0, filePath.lastIndexOf('/'));
        LogUtils.d(TAG, "fileCheck -> " + dir);
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        if (fileDir.isFile()) {
            fileDir.delete();
            fileDir.mkdirs();
        }

        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteFileFilterByTime(String path, long currTimeMillis, long mills) {
        LogUtils.d(TAG, "deleteFileFilterByTime==> path =" + path);
        //log
        if (TextUtils.isEmpty(path)) return;
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles(file -> {
            long lastModified = file.lastModified();
            return (currTimeMillis - lastModified > mills);// && file.isFile();
        });
        if (files == null) {
            LogUtils.d(TAG, "deleteFileFilterByTime==> files is null");
            return;
        }
        if (files.length == 0) {
            LogUtils.d(TAG, "deleteFileFilterByTime==> files.length: " + files.length);
            try {
                if (dir.isDirectory()) {
                    dir.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return;
        }

        for (File f : files) {
            try {
                if (f.isDirectory() && f.listFiles().length != 0) {
                    deleteFileFilterByTime(f.getAbsolutePath(), currTimeMillis, mills);
                } else {
                    f.delete();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public static boolean deleteFile(String filename) {
        if (TextUtils.isEmpty(filename)) {
            return false;
        }
        boolean result = false;
        try {
            File file = new File(filename);
            if (file.exists()) {
                result = file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    public static boolean isDirExists(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            return true;
        }

        return false;
    }

}
