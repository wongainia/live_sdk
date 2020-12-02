package com.lib.showfield.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.showfield.ui.view.clearScreen.ClearScreenLayout;

/**
 * Created by upingu
 * Date  2020-06-01 13:59
 * <p>
 * 解决与viewpager滑动冲突的横向recycleView
 **/
public class HorizontalRecyclerViewV2 extends RecyclerView {

    public HorizontalRecyclerViewV2(@NonNull Context context) {
        super(context);
    }

    public HorizontalRecyclerViewV2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalRecyclerViewV2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // interceptTouch是自定义属性控制是否拦截事件
        ViewParent parent = this;
        // 循环查找ViewPager, 请求ViewPager不拦截触摸事件
        while (null != parent.getParent() && !((parent = parent.getParent()) instanceof ClearScreenLayout)) {
            // nop
        }

        parent.requestDisallowInterceptTouchEvent(true);

        return super.dispatchTouchEvent(ev);
    }
}
