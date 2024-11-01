import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.yunke.module_base.view.activity.web.JsConstant

/**
 * @auth : linker
 * @date : 2020/11/25 5:46 PM
 * @description ：web交互回调  H5调用的JS接口
 */
class NativeJavascriptInterface {
    private var context: Context? = null
    private var webView: WebView? = null
    private var mListener: OnJavascriptInterface? = null

    fun init(context: Context, webView: WebView, mListener: OnJavascriptInterface?) {
        this.context = context
        this.webView = webView
        this.mListener = mListener
        webView?.addJavascriptInterface(this, JsConstant.INTERFACE_NAME)
    }


    @JavascriptInterface
    fun modifyIp(json: String) {
        mListener?.onJsInterface(JsConstant.MODIFY_IP, json)
    }

    /**
     * 回调
     * type 函数
     * params 参数
     */
    interface OnJavascriptInterface {
        fun onJsInterface(type: String, vararg params: String)
    }
}