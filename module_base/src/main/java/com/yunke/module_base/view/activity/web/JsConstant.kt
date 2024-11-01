package com.yunke.module_base.view.activity.web


/**
 * @author Linker
 * @description:
 * @date :2023/7/8
 */
object JsConstant {
    const val INTERFACE_NAME = "posJs"

    //打印数据
    const val PRINT_DATA = "printData"

    //存储数据
    const val PUT_DATA = "putData"

    //打开钱箱
    const val OPEN_CASH_DRAWER = "openCashDrawer"

    //打开钱箱
    const val OPEN_CASH_BOX = "openCashBox"

    //是否显示副屏
    const val IS_SHOW_PRESENTATION = "isShowPresentation"

    //设置appId
    const val SET_APP_ID = "setAppId"

    //设置用户id
    const val SET_USER_NAME = "setUserName"

    //netsIp
    const val NETS_INIT_IP = "netsInitIp"

    //支付
    const val PURSE_AMOUNT = "purseAmount"

    //debug页面
    const val TO_DEBUG_PAGE = "toDebugPage"

    //重启服务
    const val RE_START_SERVICE = "reStartService"

    //下载apk
    const val DOWNLOAD_APK = "downloadApk"

    //全局函数
    const val GLOBAL_FUNCTION = "globalFunction"

    //pos机支付
    const val POS_PAY = "posPay"

    const val MODIFY_IP = "modifyIp"

    /**
     * 全局函数code
     */
    object GlobalCode {
        const val code_turnZero = "10001"//电子秤清零
        const val code_removePeel = "10002"//电子秤去皮称重
        const val code_manualPeel = "10003"//电子秤手工去皮
        const val code_closeElectronic = "10004"//关闭电子秤
    }
}