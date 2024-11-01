package com.yunke.lib_http.enums

/**
 * 会员类型
 * //type  自定义 原价  积分 原价
//vip vipPrice
 */
enum class MemberType(val type:String) {
    VIP("vip"),
    POINTS("points"),
    CUSTOM("custom"),
}