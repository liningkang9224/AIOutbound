package com.yunke.module_base.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yunke.module_base.R;

public abstract class BaseDialog  extends Dialog {


    public BaseDialog(@NonNull Context context) {
        super(context, R.style.AppDialog);
        setContentView();
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView();
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setContentView();
    }

    private void setContentView() {
        LayoutInflater.from(getContext()).inflate(getLayoutId(), (ViewGroup) getWindow().getDecorView());
    }

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets whether this dialog is canceled when touched outside the window's
        //bounds. If setting to true, the dialog is set to be cancelable if not
        //already set.
        setCanceledOnTouchOutside(isCanceledOnTouchOutside());

        // window参数设置
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = getWindowWidth();
        lp.height = getWindowHeight();
        lp.gravity = getDialogGravity();
        lp.windowAnimations = getWindowAnimations();
        init(savedInstanceState);
    }

    protected abstract void init(Bundle savedInstanceState);


    protected int getDialogGravity() {
        return Gravity.BOTTOM;
    }

    /**
     * 获取 Dialog进出动画
     *
     * @return
     */
    protected int getWindowAnimations() {
        return R.style.dialogFragmentAnim;
    }

    /**
     * 界外点击
     *
     * @return
     */
    protected boolean isCanceledOnTouchOutside() {
        return true;
    }
    /**
     * 获取窗体宽度
     *
     * @return
     */
    protected int getWindowWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    /**
     * 获取窗体高度
     *
     * @return
     */
    protected int getWindowHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }
}
