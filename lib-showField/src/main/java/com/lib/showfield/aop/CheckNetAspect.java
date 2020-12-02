package com.lib.showfield.aop;

import android.app.Application;

import com.blankj.utilcode.util.NetworkUtils;
import com.hjq.toast.ToastUtils;
import com.lib.showfield.R;
import com.lib.showfield.helper.ActivityStackManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2020/01/11
 * desc   : 网络检测
 */
@Aspect
public class CheckNetAspect {

    /**
     * 方法切入点
     */
    @Pointcut("execution(@com.dp.sdk.sportslive.aop.CheckNet * *(..))")
    public void method() {
    }

    /**
     * 在连接点进行方法替换
     */
    @Around("method() && @annotation(checkNet)")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, CheckNet checkNet) throws Throwable {
        Application application = ActivityStackManager.getInstance().getApplication();
        if (application != null) {
            if (!NetworkUtils.isConnected()) {
                ToastUtils.show(R.string.common_network);
                return;
            }
            //执行原方法
            joinPoint.proceed();
        }
    }
}