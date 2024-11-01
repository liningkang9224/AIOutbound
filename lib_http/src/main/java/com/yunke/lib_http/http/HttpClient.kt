package com.yunke.lib_http.http

import android.util.Log
import com.yunke.lib_common.BaseApplication
import com.yunke.lib_common.util.AppUtil
import com.yunke.lib_http.constant.ApiConstant
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.Interceptor.Companion.invoke
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @author Linker
 * @description:okhttp 网络请求
 * @date :2023/3/1
 */
object HttpClient {
    private val TAG = "HttpClient"
    private var mOkHttpClient: OkHttpClient? = null


    init {
        initOkHttpClient()
    }

    fun <T> create(service: Class<T>, baseUrl: String): T {
        return getRetrofit(baseUrl).create(service)
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    /**
     * 初始化
     */
    private fun initOkHttpClient() {
        try {
            if (mOkHttpClient == null) {
                var clientBuilder = OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .addInterceptor(headerInterceptor())
                    .addInterceptor(AllInPayInterceptor())
                    .addInterceptor(HttpLogInterceptor())
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
//                if (BuildConfig.DEBUG) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    clientBuilder.addInterceptor(interceptor) //日志拦截器
//                }
//                var mSSLSocketFactory = getSSLSocketFactory()
//                mSSLSocketFactory?.let {
//                    clientBuilder.sslSocketFactory(it, object : X509TrustManager {
//                        override fun checkClientTrusted(
//                            chain: Array<out X509Certificate>?,
//                            authType: String?
//                        ) {
//                        }
//
//                        override fun checkServerTrusted(
//                            chain: Array<out X509Certificate>?,
//                            authType: String?
//                        ) {
//                        }
//
//                        override fun getAcceptedIssuers(): Array<X509Certificate> {
//                            return arrayOf()
//                        }
//
//                    })
//                }

                mOkHttpClient = clientBuilder.build()
            }
        } catch (e: Exception) {
            Log.e("okhttp", "err: $e")
        }
    }

    /**
     * 头部信息
     */
    private fun headerInterceptor(): Interceptor {
        return invoke { chain ->
            val builder = chain.request()
                .newBuilder()
            builder
                .addHeader(ApiConstant.Params.PLATFORM, "android")
                .addHeader(ApiConstant.Params.VERSION, AppUtil.getVersionName(BaseApplication.getContext()))
            if (ApiConstant.token.isNotEmpty()) {
                builder.addHeader(ApiConstant.Params.TOKEN, ApiConstant.token)
            }
//            builder.build()
            chain.proceed(builder.build())
        }
    }

    //ssl证书管理
//    private fun getSSLSocketFactory(): SSLSocketFactory? {
//        //创建一个不验证证书链的证书信任管理器。
//        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
//            override fun checkClientTrusted(
//                chain: Array<java.security.cert.X509Certificate>,
//                authType: String,
//            ) {
//            }
//
//            override fun checkServerTrusted(
//                chain: Array<java.security.cert.X509Certificate>,
//                authType: String,
//            ) {
//            }
//
//            override fun getAcceptedIssuers(): Array<X509Certificate?> {
//                return arrayOfNulls(0)
//            }
//        })
//
//        // Install the all-trusting trust manager
//        var sslContext: SSLContext? = null
//        try {
//            sslContext = SSLContext.getInstance("TLS")
//            sslContext!!.init(
//                null, trustAllCerts,
//                java.security.SecureRandom()
//            )
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        // Create an ssl socket factory with our all-trusting manager
//        return sslContext?.socketFactory
//    }
}