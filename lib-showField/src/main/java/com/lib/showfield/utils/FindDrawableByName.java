package com.lib.showfield.utils;

import android.content.Context;

/**
 * Created by upingu
 * Date  2020/6/15 13:20
 * <p>
 * Description
 **/
public class FindDrawableByName {

    public static int getDrawableRes(Context context, String name) {
        if (null == context) return 1;
        String packageName = context.getPackageName();
        return context.getResources().getIdentifier(name, "drawable", packageName);
    }
}
