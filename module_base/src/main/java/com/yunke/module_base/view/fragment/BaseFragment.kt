import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.yunke.module_base.view.dialog.LoadingDialog
import java.lang.reflect.ParameterizedType

/**
 * 抽象父类
 * 继承Fragment的父类,所有Fragment均继承该类
 * auth linker
 * date 5.13
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected open lateinit var mContext: Context
    protected open lateinit var mActivity: Activity

    private var mLoading: LoadingDialog? = null
    private var mViewBinding: VB? = null

    protected open fun viewBinding() = mViewBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
        mContext = context
    }

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 设置监听
     */
    protected abstract fun setListener()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mViewBinding = getViewBinding()
        return viewBinding()?.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        setListener()
        mLoading = LoadingDialog(mActivity)
    }


    protected fun showLoading(text: String?) {
        mLoading?.onShow()?.setText(text)
    }

    protected fun dismissLoading() {
        if (mLoading?.isShowing == true) {
            mLoading?.onDismiss()
        }
    }

    override fun onStop() {
        dismissLoading()
        super.onStop()
    }

    /**
     * 传入recyclerView，运行动画
     */
    open fun runLayoutAnimation(recyclerView: RecyclerView) {
//        val controller =
//            AnimationUtils.loadLayoutAnimation(mContext, R.anim.recycler_slide_in_bottom)
//        recyclerView.layoutAnimation = controller
//        recyclerView.adapter!!.notifyDataSetChanged()
//        recyclerView.scheduleLayoutAnimation()
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

    /**
     * 触发返回
     */
    protected fun onBackPressed() {
        mActivity?.onBackPressed()
    }

    /**
     * 销毁当前activity
     */
    protected fun onFinish() {
        mActivity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mLoading = null
        mViewBinding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}