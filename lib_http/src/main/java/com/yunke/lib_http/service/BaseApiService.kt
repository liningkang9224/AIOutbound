package com.yunke.lib_http.service

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * @author Linker
 * @description:
 * @date :2023/6/24
 */
open class BaseApiService {

    open fun getResponseBody(params: MutableMap<String, Any>?): RequestBody {
        return if (params == null) {
            RequestBody.create("application/json;charset=utf-8".toMediaTypeOrNull(), "")
        } else RequestBody.create(
            "application/json;charset=utf-8".toMediaTypeOrNull(), Gson().toJson(params)
        )
    }

    open fun getResponseBody(params: Any?): RequestBody {
        return if (params == null) {
            RequestBody.create("application/json;charset=utf-8".toMediaTypeOrNull(), "")
        } else RequestBody.create(
            "application/json;charset=utf-8".toMediaTypeOrNull(), Gson().toJson(params)
        )
    }


    open fun getResponseBody(params: String?): RequestBody {
        return RequestBody.create("application/json;charset=utf-8".toMediaTypeOrNull(), params ?: "")
    }

    open fun getResponseBody(file: File): RequestBody {
        return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
    }
}