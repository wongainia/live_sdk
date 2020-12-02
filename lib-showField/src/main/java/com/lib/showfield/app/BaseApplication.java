package com.lib.showfield.app;

import android.app.Application;
import android.net.http.HttpResponseCache;
import android.util.Log;
import android.widget.Toast;

import com.hjq.toast.ToastInterceptor;
import com.hjq.toast.ToastUtils;
import com.lib.showfield.BuildConfig;
import com.lib.showfield.helper.ActivityStackManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import java.io.File;
import java.io.IOException;

import video.dkplayer_java.player.VideoViewConfig;
import video.dkplayer_java.player.VideoViewManager;
import video.dkplayer_players.ijk.IjkPlayerFactory;

/**
 * Created by JoeyChow
 * Date  2020-07-27 13:17
 * <p>
 * Description
 **/
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initSDK(this);
    }

    /**
     * 初始化一些第三方框架
     */
    public static void initSDK(Application application) {

        //播放器初始化
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setLogEnabled(BuildConfig.DEBUG)//调试的时候请打开日志，方便排错
                .setPlayerFactory(IjkPlayerFactory.create())
//                .setPlayerFactory(ExoMediaPlayerFactory.create())
//                .setRenderViewFactory(SurfaceRenderViewFactory.create())
//                .setEnableOrientation(true)
//                .setEnableAudioFocus(false)
//                .setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT)
//                .setAdaptCutout(false)
//                .setPlayOnMobileNetwork(true)
//                .setProgressManager(new ProgressManagerImpl())
                .build());

        // 吐司工具类
        ToastUtils.init(application);

        // 设置 Toast 拦截器
        ToastUtils.setToastInterceptor(new ToastInterceptor() {
            @Override
            public boolean intercept(Toast toast, CharSequence text) {
                boolean intercept = super.intercept(toast, text);
                if (intercept) {
                    Log.e("Toast", "空 Toast");
                } else {
                    Log.i("Toast", text.toString());
                }
                return intercept;
            }
        });

        // 设置全局的 Footer 构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) ->
                new ClassicsFooter(context).setDrawableSize(20));

        // Activity 栈管理初始化
        ActivityStackManager.getInstance().init(application);

        //文件缓存
        File cacheDir = new File(application.getApplicationContext().getCacheDir(), "https");
        try {
            HttpResponseCache.install(cacheDir, 1024 * 1024 * 512);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
