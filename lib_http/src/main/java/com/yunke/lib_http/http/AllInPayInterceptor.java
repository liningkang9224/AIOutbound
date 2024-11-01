package com.yunke.lib_http.http;

import androidx.annotation.NonNull;

import com.yunke.lib_http.constant.ApiConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * chengbiao
 * 2024/5/8
 * desc:
 */
public class AllInPayInterceptor implements Interceptor {
    private static final String TAG = "AllInPayInterceptor";

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String encodedPath = request.url().encodedPath();
        if (null != encodedPath && encodedPath.endsWith("/trans")) {
            HttpUrl parse = HttpUrl.parse(ApiConstant.getIP_Allin_pay() + encodedPath);
            return chain.withConnectTimeout(30, TimeUnit.SECONDS)
                    .withWriteTimeout(30, TimeUnit.SECONDS)
                    .withReadTimeout(150, TimeUnit.SECONDS).proceed(request.newBuilder().url(parse).build());
        }
        return chain.proceed(request);
    }
}
