package com.yunke.lib_common.listener;

import android.view.View;

import com.yunke.lib_common.util.LogUtils;

public abstract class ClickTimesListener implements View.OnClickListener {

    private int clickTimes = 0;
    private long lastClickTimes = 0;

    private int targetTimes = 5;
    private static final String TAG = "ClickTimesLinstener";

    public ClickTimesListener(int targetTimes) {
        this.targetTimes = targetTimes < 3 ? 4 : targetTimes;
    }

    @Override
    public void onClick(View v) {
        if (lastClickTimes == 0 || System.currentTimeMillis() - lastClickTimes < 2000) {
            lastClickTimes = System.currentTimeMillis();
            clickTimes++;
            if (clickTimes < targetTimes && clickTimes > 3) {
                LogUtils.i(TAG, "onClick: clickTimes=" + clickTimes);
//                ToastUtils.INSTANCE.show(BaseApplication.getContext(),"点击" + clickTimes + "次了,再点一次");
            } else if (clickTimes >= targetTimes) {
                LogUtils.i(TAG, "点击" + clickTimes);
                clickTimes = 0;
                lastClickTimes = 0;
                targetClickDo(v);
            }
        } else {
            clickTimes = 0;
            LogUtils.i(TAG, "重置" + clickTimes);
            lastClickTimes = 0;
        }
    }

    public abstract void targetClickDo(View view);
}
