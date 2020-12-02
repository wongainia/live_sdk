package com.lib.showfield.helper;

import android.os.Handler;
import android.os.Message;
import com.lib.showfield.interfaces.BaseHandlerCallBack;

import java.lang.ref.WeakReference;

/**
 *
 * weak Handler
 * @param <T>
 */
public class WeakHandler<T extends BaseHandlerCallBack> extends Handler {
    private WeakReference<T> mOuter;

    public WeakHandler(T activity) {
        mOuter = new WeakReference<T>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        T t = mOuter.get();
        if (t == null) {
            removeCallBackMessages();
            return;
        }
        /*if (t instanceof Activity) {
            if (((Activity) t).isFinishing()) {
                removeCallBackMessages();
                return;
            }
        }
        if (t instanceof Fragment) {
            if (((Fragment) t).isDetached()) {
                removeCallBackMessages();
                return;
            }
        }*/
        t.callBack(msg);
    }

    /**
     * onStop or onDestory
     */
    public void removeCallBackMessages() {
        this.removeCallbacksAndMessages(null);
    }
}
