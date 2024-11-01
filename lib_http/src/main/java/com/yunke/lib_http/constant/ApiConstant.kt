package com.yunke.lib_http.constant

import com.yunke.lib_http.enums.CouponType
import com.yunke.lib_http.enums.DeliveryType


/**
 * @author Linker
 * @description: api常量
 * @date :2023/3/1
 */
object ApiConstant {

    const val INDEX_PHP = "/index.php"
    const val PAGE_SIZE = 20
    var app_id: String = "0"
    var shop_supplier_id: String = "0"

    const val GET_PRODUCT_CATE_SIZE = 1//第一次请求商品分类的数量

    const val DINING_TYPE_DELIVERY = 10
    const val DINING_TYPE_PICKUP = 20
    const val DINING_TYPE_IN_STORE = 30
    var mDiningType = DINING_TYPE_IN_STORE//dinner_type 10配送  20自提  30店内
    const val SPEC_TYPE = 20//多规格
    const val CART_TYPE = 1

    var DELIVERY = DeliveryType.DINE_IN.type
    var IS_BAG = DeliveryType.DINE_IN.value


    const val TYPE_DOWN = "down"
    const val TYPE_UP = "up"
    const val TYPE_LOGIN = "login"

    @JvmStatic
    var IP_Allin_pay: String = "http://0.0.0.0:9801"

    const val PAY_SOURCE = "machine"
    const val PAY_NEW_ID = 4L//积分支付

    var mCouponType = CouponType.PRODUCT.value

    object Params {
        const val NONCE = "nonce"
        const val TIMESTAMP = "timestamp"
        const val SOURCE = "Source"
        const val APP_ID = "appId"
        const val SHOP_SUPPLIER_ID = "shopSupplierId"
        const val VERSION = "version"
        const val PLATFORM = "platform"
        const val TOKEN = "token"
        const val SCAN_TOKEN = "scanToken"
        const val SN = "sn"
        const val ACCEPT_LANGUAGE = "Accept-Language"

    }

    var token = ""
    var scanToken = ""
}