package com.yunke.lib_http.http

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import com.yunke.lib_http.bean.ApiException
import com.yunke.lib_http.bean.Error


/**
 * @author Linker
 * @description:根据异常返回相关的错误信息工具类
 * @date :2023/3/1
 */
object ExceptionHandle {

    fun handleException(e: Throwable?): ApiException {
        val ex: ApiException
        e?.let {
            when (it) {
                is HttpException -> {
                    ex = ApiException(Error.NETWORK_ERROR, e)
                    return ex
                }
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> {
                    ex = ApiException(Error.PARSE_ERROR, e)
                    return ex
                }
                is ConnectException -> {
                    ex = ApiException(Error.NETWORK_ERROR, e)
                    return ex
                }
                is javax.net.ssl.SSLException -> {
                    ex = ApiException(Error.SSL_ERROR, e)
                    return ex
                }
                is ConnectTimeoutException -> {
                    ex = ApiException(Error.TIMEOUT_ERROR, e)
                    return ex
                }
                is java.net.SocketTimeoutException -> {
                    ex = ApiException(Error.TIMEOUT_ERROR, e)
                    return ex
                }
                is java.net.UnknownHostException -> {
                    ex = ApiException(Error.TIMEOUT_ERROR, e)
                    return ex
                }
                is ApiException -> return it

                else -> {
                    ex = ApiException(Error.UNKNOWN, e)
                    return ex
                }
            }
        }
        ex = ApiException(Error.UNKNOWN, e)
        return ex
    }
}