package com.yunke.module_base.view.activity

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.yunke.lib_common.listener.OnClickAnimatorListener
import com.yunke.lib_common.router.ARouterPath
import com.yunke.module_base.R
import com.yunke.module_base.view.activity.view.AppToolBarView
import com.yunke.module_base.view.dialog.LoadingDialog
import java.lang.reflect.ParameterizedType


/**
 * base类，供其他activity继承
 * VB不要传错
 * @date :2021/3/1
 */
abstract class BaseAppCompatActivity<VB : ViewBinding> : AppCompatActivity() {
    protected open val TAG = this.javaClass.simpleName

    private lateinit var mContext: Context
    private var mViewBinding: VB? = null
    private var mToolBarView: AppToolBarView? = null
    private var mLoading: LoadingDialog? = null


    protected open fun getContext() = mContext
    protected open fun viewBinding() = mViewBinding
    protected open fun getToolBarView() = mToolBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTransparent()
        setStatusBarLightOrDark(true)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        mContext = this
        mViewBinding = getViewBinding()
        setContentView()
        mLoading = LoadingDialog(this)
        initData()
        setListener()
    }

    /**
     * 设置内容布局
     */
    private fun setContentView() {
        if (isDefaultTitle()) {//是否使用默认的title
            mToolBarView = AppToolBarView(this)
            val lp = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            val titleHeight = resources.getDimensionPixelSize(com.yunke.lib_common.R.dimen.title_height)
            lp.topMargin = titleHeight
            val titleLp = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, titleHeight)
            setContentView(mViewBinding?.root, lp)
            addContentView(mToolBarView, titleLp)
            setToolBarListener()
        } else {

            setContentView(mViewBinding?.root)
        }
    }


    /**
     * 显示加载框
     */
    protected fun showLoading(text: String?) {
        dismissLoading()
        mLoading?.onShow()?.setText(text)
    }

    protected fun dismissLoading() {
        if (mLoading?.isShowing == true) {
            mLoading?.onDismiss()
        }
    }


    /**
     * 设置状态透明
     */
    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * 设置状态栏字体颜色
     * isLight 黑色 else 白色
     */
    open fun setStatusBarLightOrDark(isLight: Boolean) {
        /** Changes the System Bar Theme. */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val flags = window?.decorView?.systemUiVisibility ?: return
            window?.decorView?.systemUiVisibility = if (!isLight) flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            else flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }


    /**
     * 设置toolbar的点击事件
     */

    private fun setToolBarListener() {
        getToolBarView()?.setMenuListener(object : OnClickAnimatorListener() {
            override fun onClickListener(p0: View?) {
                onMenuClick(p0?.id)
            }

        })
        getToolBarView()?.setBackListener(object : OnClickAnimatorListener() {
            override fun onClickListener(p0: View?) {
                onBackPressed()
            }

        })
    }

    /**
     * toolbar右边菜单点击
     * 交给子类处理
     */
    protected open fun onMenuClick(viewId: Int?) {
        viewId?.let {
            jumpToPage(viewId)
        }
    }

    /**
     * 是否使用默认的title
     *
     *
     * @return isDefaultTitle
     */
    protected open fun isDefaultTitle(): Boolean {
        return false
    }

    /**
     */
    protected open fun isStatusBarTransparent(): Boolean {
        return true
    }

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 设置监听
     */
    protected abstract fun setListener()

    /**
     * 页面跳转
     * 右上角菜单按钮，根据id区分
     */
    protected abstract fun jumpToPage(viewId: Int)

    /**
     * 打开软键盘
     */
    fun openKeyBord(mEditText: View?) {
        mEditText?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: View?) {
        mEditText?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(mEditText?.windowToken, 0)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        exitTopToDown()
    }

    override fun onStop() {
        dismissLoading()
        super.onStop()
    }

    override fun onDestroy() {
        clearData()
        super.onDestroy()
    }


    private fun clearData() {
        dismissLoading()
        mLoading = null
        mToolBarView = null
        mViewBinding = null
    }

    /**
     * 退出动画
     */
    open fun exitAnimation() {
        exitAnimation(R.anim.activity_no_anim, R.anim.view_out_from_right)
    }

    open fun exitAnimation(noAnim: Int, outAnim: Int) {
        removeActivity()
        overridePendingTransition(noAnim, outAnim)
    }

    /**
     * 从上到下退出
     */
    open fun exitTopToDown() {
        removeActivity()
        finish()
        overridePendingTransition(R.anim.activity_no_anim, R.anim.view_out_from_down)
    }

    open fun exitAlpha() {
        removeActivity()
        finish()
        overridePendingTransition(R.anim.activity_no_anim, R.anim.view_out_alpha)
    }

    private fun removeActivity() {
//        ActivityUtils.INSTANCE.removeActivity(this)
    }

    /**
     * 获取viewbinding
     */
    private fun getViewBinding(): VB {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        val type = this.javaClass.genericSuperclass as ParameterizedType
        //返回表示此类型实际类型参数的 Type 对象的数组()，想要获取第二个泛型的Class，所以索引写1
        val mClass = type.actualTypeArguments[0] as Class<VB>
        val method = mClass.getMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }

}