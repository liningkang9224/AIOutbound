package com.yunke.lib_common.log;


import android.content.Context;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ****************************************
 * author：Eason A.K
 * time：2020/07/29
 * description：当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告
 * ****************************************
 */
public class CrashCatchHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    private static class CrashCatchHandlerHolder {
        private static final CrashCatchHandler INSTANCE = new CrashCatchHandler();
    }

    public static CrashCatchHandler instance() {
        return CrashCatchHandlerHolder.INSTANCE;
    }

    private Context context;
    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler defaultHandler;

    private CrashCatchHandler() {

    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        this.context = context;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的UncaughtException处理器
        Thread.setDefaultUncaughtExceptionHandler(this);// 设置当前CrashHandler为程序的默认处理器
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            if (!handleException(ex) && defaultHandler != null) {
                // 如果用户没有处理则让系统默认的异常处理器来处理
                defaultHandler.uncaughtException(thread, ex);
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "exception : ", e);
                    e.printStackTrace();
                }
                // 杀死进程
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return 如果处理了该异常信息, 返回true;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 使用Toast显示异常信息
//        new Thread() {
//            public void run() {
//                Looper.prepare();
//                Toast.makeText(context, "程序出现未捕获的异常，即将退出！", Toast.LENGTH_SHORT).show();
//                Looper.loop();
//            }
//        }.start();

        saveCrashInfoToFile(ex);// 保存日志文件
        return false;
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称
     */
    private void saveCrashInfoToFile(Throwable ex) {

        // 字符串流
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n");

        // 获得错误信息
        Writer writer = new StringWriter();// 这个writer下面还会用到，所以需要它的实例
        PrintWriter printWriter = new PrintWriter(writer);// 输出错误栈信息需要用到PrintWriter
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {// 循环，把所有的cause都输出到printWriter中
            cause.printStackTrace(printWriter);
            cause = ex.getCause();
        }
        printWriter.close();
        String result = writer.toString();
//        LogCacheManager.getInstance().recordUserOperationInfo(result, true);
        stringBuffer.append(result);

        // 写入文件
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
        String crashFileName = "crash_" + simpleDateFormat.format(new Date()) + ".log";
        WriteLogUtils.writeLog(crashFileName, stringBuffer.toString(), true);
    }

}
