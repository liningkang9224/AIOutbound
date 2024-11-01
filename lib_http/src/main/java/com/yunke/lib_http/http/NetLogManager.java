package com.yunke.lib_http.http;

import android.os.Environment;
import android.util.Log;

import com.yunke.lib_common.log.WriteLogUtils;
import com.yunke.lib_common.util.FileUtils;
import com.yunke.lib_http.bean.ResultBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Response;

/**
 * chengbiao
 * 2024/5/9
 * desc:
 */
public class NetLogManager {

    private static NetLogManager ourInstance;


    public static NetLogManager getInstance() {
        if (ourInstance == null) {
            synchronized (NetLogManager.class) {
                if (ourInstance == null) {
                    ourInstance = new NetLogManager();
                }
            }
        }
        return ourInstance;
    }

    private NetLogManager() {
    }

    public String printParams(RequestBody body) {
        if (body == null) {
            return "RequestBody为空";
        }
        Buffer buffer = new Buffer();
        try {
            body.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }
            String params = buffer.readString(charset);
            return params;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "解析异常";
    }


    /**
     * 记录服务器错误信息， serverErrorMsg
     */
    public <T> void recordServerErrorMsg(Call<ResultBean<T>> call, Response<ResultBean<T>> response, String otherInfo) {
        Request request = call.request();
        if (isFilterLogCache(request)) {
            return;
        }

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("\n");
        // 记录接口请求信息
        stringBuffer.append("request -> " + request.toString() + "\n");
        stringBuffer.append("requestHeaders -> {" + request.headers().toString().replace("\n", " , ") + "}" + "\n");
        stringBuffer.append("requestParams -> " + printParams(request.body()));

        stringBuffer.append("\n");
        if (response == null) {
            stringBuffer.append("response -> null" + "\n");
        } else {
            stringBuffer.append("response -> " + response.toString() + "\n");
            ResultBean<T> responseBody = response.body();
            if (responseBody == null) {
                stringBuffer.append("responseBody is null");
            } else {
                stringBuffer.append("responseBody -> " + responseBody.toString() + "\n");
            }
        }
        WriteLogUtils.writeLog("service_error", stringBuffer.toString(), true);
    }


    private boolean isFilterLogCache(Request request) {
        return false;
    }


    /**
     * 记录异常网络信息
     */
    public <T> void recordExceptionApiInfo( Request request ,String result, String otherInfo) {

//        Request request = call.request();
        if (isFilterLogCache(request)) {
            return;
        }

//        Map<String, String> infosMap = AppUtils.getAppLogInfo(BaseApplication.getContext());

        // 字符串流
        StringBuffer stringBuffer = new StringBuffer();

        // 获得设备信息
//        for (Map.Entry<String, String> entry : infosMap.entrySet()) {// 遍历map中的值
//            String key = entry.getKey();
//            String value = entry.getValue();
//            stringBuffer.append(key + "=" + value + "\n");
//        }

//        stringBuffer.append("\n");
        // 记录接口请求信息
        stringBuffer.append("request -> " + request.url() + "\n");
        stringBuffer.append("requestHeaders -> {" + request.headers().toString().replace("\n", " , ") + "}" + "\n");
        stringBuffer.append("requestParams -> " + printParams(request.body()));

        stringBuffer.append("\nresult:").append(result);

        stringBuffer.append("\n");

        if (otherInfo == null) {
            stringBuffer.append("otherInfo -> null" + "\n");
        } else {
            stringBuffer.append("otherInfo -> " + otherInfo.toString() + "\n");
        }

        stringBuffer.append("\n");

        // 写入文件
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String crashFileName = getRootPath() + "/api_" + simpleDateFormat.format(new Date()) + ".log";

        FileUtils.fileCheck(crashFileName);

        try {
            FileOutputStream fos = new FileOutputStream(crashFileName);
            fos.write(stringBuffer.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "an FileNotFoundException occured when write crashfile to sdcard", e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "an IOException occured when write crashfile to sdcard", e);
            e.printStackTrace();
        }
    }

    private static final String TAG = "NetLogManager";

    /**
     * 用户操作信息
     */
    public void recordUserOperationInfo(String operationInfo) {
        recordUserOperationInfo(operationInfo, false);
    }

    /**
     * @param operationInfo
     * @param isDirectWrite 是否直接写入
     */
    public void recordUserOperationInfo(String operationInfo, boolean isDirectWrite) {
        userOprData.append(operationInfo);
        if (isDirectWrite) {
            WriteLogUtils.getExecutorService().execute(() -> {
                recordUserOperationInfo();
            });
        } else {
            if (isUserOperationRecordFileCanWrite()) {
                WriteLogUtils.getExecutorService().execute(() -> {
                    recordUserOperationInfo();
                });
            }
        }
    }

    public synchronized void recordUserOperationInfo() {
        String logFileName = getUserOprLogFileName();

        FileUtils.fileCheck(logFileName);

        try {
            FileOutputStream fos = new FileOutputStream(logFileName, true);
            fos.write(userOprData.toString().getBytes());
            fos.flush();
            fos.close();

            userOprData.delete(0, userOprData.length());
        } catch (FileNotFoundException e) {
            Log.e(TAG, "an FileNotFoundException occured when write crashfile to sdcard", e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "an IOException occured when write crashfile to sdcard", e);
            e.printStackTrace();
        }
    }

    /**
     * 是否可以写入
     *
     * @return
     */
    public boolean isUserOperationRecordFileCanWrite() {
        String logFileName = getUserOprLogFileName();

        File file = new File(logFileName);

        // 文件不存在可写入
        if (!file.exists()) {
            return true;
        }

        // 大于一分钟可写入
        if (System.currentTimeMillis() - file.lastModified() >= 60 * 1000) {
            return true;
        }

        return false;
    }

    /**
     * 用户操作
     *
     * @return
     */
    public static String getUserOprLogFileName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return getRootPath() + "/opr_" + simpleDateFormat.format(new Date()) + ".log";
    }


    public static String getRootPath() {
        return WriteLogUtils.logDirPath;
    }

    /**
     * 缓存记录前缀
     */
    public String getRecordTimePrefix() {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        return DATE_FORMAT.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 获取要缓存的记录
     *
     * @return
     */
    public String getCacheRecord(String record) {
        return getRecordTimePrefix() + record + "\n";
    }

    /**
     * 日志记录
     */
    public void cacheRecord(String record) {
        userOprData.append(getCacheRecord(record));
    }

    public static StringBuffer userOprData = new StringBuffer();
}
