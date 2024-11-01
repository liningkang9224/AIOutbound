package com.yunke.lib_http.http;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.yunke.lib_common.util.GSonUtil;
import com.yunke.lib_common.util.LogUtils;
import com.yunke.lib_http.bean.ResultBean;
import com.yunke.lib_http.enums.Status;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * chengbiao
 * 2024/5/9
 * desc:
 */
public class HttpLogInterceptor implements Interceptor {
    private static final String TAG = "HttpLogInterceptor";
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (LogUtils.isLogEnable()) {
//            String params = LogCacheManager.getInstance().printParams(request.body());
//            LogUtils.d(TAG, "-\n" +
//                    "request -> " + request.url()+ "\n" +
//                    "requestHeaders -> {" + request.headers().toString().replace("\n", " , ") + "}" + "\n" +
//                    "requestParams -> " + ""
//            );
        }
        //allInPay支付不做拦截
        if (request.url().toString().endsWith("/trans")) {
            return chain.proceed(request);
        }
        Response response = chain.proceed(request);
        // -----------> 返回信息打印
//        LogUtils.d(TAG, "response -> " + response.toString());
//        LogUtils.d(TAG, "response headers -> \n" + response.headers().toString());

        // okhttp中response.body().string()只能调用一次！！！否则会报java.lang.IllegalStateException: closed
//        LogUtils.d(TAG, "response -> " + response.body().toString());
        ResponseBody responseBody = response.body();

//        StringBuilder sResp = new StringBuilder();
//        long contentLength = responseBody.contentLength();
//        sResp.append("contentLength:" + responseBody.contentLength());
//        sResp.append("\n");
//        sResp.append("contentType:" + responseBody.contentType());
//        sResp.append("\n");
//
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
                return response;
            }
        }
//        if (LogUtils.isLogEnable()) {
//            LogUtils.d(TAG, "-\n" +
//                    "response -> " + response.toString() + "\n" +
//                    "responseBody -> " + buffer.clone().readString(charset));
//        }

        if (response.body() != null) {
            try {
                String result = buffer.clone().readString(charset);
                if (TextUtils.isEmpty(result) || !result.startsWith("{")) {
                    //记录返回错误
//                    LogUtils.d(TAG, "response返回错误： "+result);
                    NetLogManager.getInstance().recordExceptionApiInfo(request, "response返回错误：" + result, "");
                    return response;
                } else {

                    JSONObject object = new JSONObject(result);
                    Object mOb = object.get("data");
                    boolean isNeedUpdateResult = false;
                    if (null == mOb || "null".equals(mOb) || mOb.toString().equals("null")) {
                        isNeedUpdateResult = true;
                        object.put("data", new JSONObject());
                        result = object.toString();
                    }
                    //根据 “data”:null  "data":[]  //统一 data:{}
                    ResultBean resp = GSonUtil.fromJson(result, ResultBean.class);
                    if (resp == null) {
//                        LogUtils.d(TAG, "intercept:response.body() != null resp == null");
                        return response.newBuilder().body(ResponseBody.create(contentType, result)).build();
                    }
                    if (resp.getCode() != Status.SUCCESS.getType()) {
                        NetLogManager.getInstance().recordExceptionApiInfo(request, result, "");
                    }
                    if (isNeedUpdateResult) {
                        return response.newBuilder().body(ResponseBody.create(contentType, result)).build();
                    }
                    return response;
                }
            } catch (Exception e) {
                return response;
            }
        } else {
            NetLogManager.getInstance().recordExceptionApiInfo(request, "response=null", "");
            return response;
        }
    }
}
