package com.lib.showfield.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 - @Description: 自定义TextView 重写isFocused()函数，让他返回回true也就是一直获取了焦点 实现跑马灯效果
 - @Author:   
 - @Time:  2020-05-13 09:47
 */
public class MarqueTextView extends AppCompatTextView {

    public MarqueTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MarqueTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueTextView(Context context) {
        super(context);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
