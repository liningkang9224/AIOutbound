package com.yunke.lib_http.bean

import java.io.Serializable


/**
 * @author Linker
 * @description:公共返回结果
 *              返回json数据类型
 *              data  result解析之后赋值
 * @date :2023/3/1
 */
class ResultBean<T>(
    var code: Int = -1,
    var msg: String? = null,
    var data: T? = null,
) : Serializable
