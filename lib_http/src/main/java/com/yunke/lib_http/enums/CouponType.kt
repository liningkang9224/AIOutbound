package com.yunke.lib_http.enums


/**
 * 优惠券类型
 */
enum class CouponType(val type: Long, val value: Int) {
    FULL_REDUCTION(10, 1),
    OFF(20, 2),
    PRODUCT(30, 0),
}