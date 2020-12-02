package com.lib.showfield.ui.view.tiktalk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ChildRecyclerView extends RecyclerView {

    public ChildRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ChildRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // interceptTouch是自定义属性控制是否拦截事件
        ViewParent parent = this;
        // 循环查找ViewPager, 请求ViewPager不拦截触摸事件
        while (null != parent.getParent() && !((parent = parent.getParent()) instanceof RecyclerView)) {
            // nop
        }

        parent.requestDisallowInterceptTouchEvent(true);

        return super.dispatchTouchEvent(ev);
    }
}
