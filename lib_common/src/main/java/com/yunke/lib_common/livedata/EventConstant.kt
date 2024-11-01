package com.yunke.lib_common.livedata


/**
 * @author Linker
 * @description:
 * @date :2023/6/22
 */
object EventConstant {
    const val EVENT_CHECK_LANGUAGE = "event_check_language"//改变语言
    const val EVENT_ADD_CART = "event_add_cart"//添加购物车
    const val EVENT_DELETE_CART = "event_delete_cart"//清空购物车
    const val EVENT_LOGIN = "event_login"//
    const val EVENT_COUPON_LIST = "event_coupon_list"//
    const val EVENT_COUPON_TYPE = "event_coupon_type"//

    const val EVENT_CLEAR_PRODUCT_LIST = "event_clear_product_list"//清空商品列表

    const val EVENT_ADD_CART_MARKUP = "event_add_cart_markup"//加价购添加购物车
    const val EVENT_CART_MARKUP_LIST = "event_cart_markup_list"//加价购列表更新
    const val EVENT_CART_INFO = "event_cart_info"//更新购物车信息

    const val EVENT_TOP_UP_SUCCESS = "event_top_up_success"//余额充值成功

    const val EVENT_UPDATE_PRODUCT_CART_LIST = "event_update_product_cart_list"//通知商品界面购物车信息更新，更新商品列表更新数量
    const val EVENT_UPDATE_CART_LIST = "event_update_cart_list"//通知购物车界面  更新购物车列表】
}