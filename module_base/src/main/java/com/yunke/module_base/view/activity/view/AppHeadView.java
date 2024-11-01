package com.yunke.module_base.view.activity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * chengbiao
 * 2024/5/6
 * desc:
 */
public class AppHeadView extends FrameLayout {

    private static final String TAG = "AppHeadView";

    public AppHeadView(@NonNull Context context) {
        this(context, null);
    }

    public AppHeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AppHeadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//R.layout.app_title_layout
    }


}
