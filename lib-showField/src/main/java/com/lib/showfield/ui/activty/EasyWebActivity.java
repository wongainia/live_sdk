package com.lib.showfield.ui.activty;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.lib.showfield.R;
import com.lib.showfield.aop.CheckNet;
import com.lib.showfield.aop.DebugLog;
import com.lib.showfield.aop.SingleClick;
import com.lib.showfield.base.action.StatusAction;
import com.lib.showfield.common.Constants;
import com.lib.showfield.common.MyActivity;
import com.lib.showfield.http.model.Hosts;
import com.lib.showfield.other.IntentKey;
import com.lib.showfield.ui.view.widget.HintLayout;

/**
 * Created by cenxiaozhong on 2017/7/22.
 * <p>
 */
public class EasyWebActivity extends MyActivity implements StatusAction {

    protected AgentWeb mAgentWeb;
    private HintLayout mHintLayout;

    @CheckNet
    @DebugLog
    public static void start(Context context, String url) {
        if (url == null || "".equals(url)) {
            return;
        }
        Intent intent = new Intent(context, EasyWebActivity.class);
        intent.putExtra(IntentKey.URL, url);
        context.startActivity(intent);
    }

    @Override
    protected boolean isStatusBarDarkFont() {
        return !super.isStatusBarDarkFont();
    }

    @Override
    protected boolean isStatusBarEnabled() {
        return !super.isStatusBarEnabled();
    }

    @Override
    public boolean isSwipeEnable() {
        return true;
    }

    @Override
    protected void getLayoutId() {
        setContentView(R.layout.activity_browser);
        mHintLayout = findViewById(R.id.hl_browser_hint);
    }

    @Override
    protected void initView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mHintLayout, new LinearLayout.LayoutParams(-1, -1))
                .closeIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(getUrl());
    }

    @Override
    protected void initData() {
        mAgentWeb.getWebCreator().getWebView().setBackgroundColor(getResources().getColor(R.color.maincolorDark));
    }


    private final WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you work
            Log.i("Info", "BaseWebActivity onPageStarted");
            showLoading();
        }

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            if (request.getUrl().toString().contains("AppLogin")){
//                startActivity(LoginActivity.class);
//            }
//            return true;
//        }


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            //post(() -> showError(v -> reload()));
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            showComplete();
        }
    };

    private com.just.agentweb.WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (title != null) {
                setTitle(title);
            }
        }
    };

    public String getUrl() {
        String theme = "&theme=";//@"Night" : @"Whitey"
        theme = theme + "Night";
        String url =
                //"http://192.168.6.12:8080/activity/giftexchange";
                getString(IntentKey.URL);
        if (url.contains("?")) {
            if (!ObjectUtils.isEmpty(SPUtils.getInstance("token").getString(Constants.TOKEN))) {
                url = url + "&token=" + SPUtils.getInstance("token").getString(Constants.TOKEN) + "&platform=android"
                        + "&appId=" + Hosts.APPID + "&clientVersion=1.5.1" + theme;
            } else {
                url = url + "&platform=android"
                        + "&appId=" + Hosts.APPID + "&clientVersion=1.5.1" + theme;
            }

        } else {
            if (!ObjectUtils.isEmpty(SPUtils.getInstance("token").getString(Constants.TOKEN))) {
                url = url + "?token=" + SPUtils.getInstance("token").getString(Constants.TOKEN) + "&platform=android"
                        + "&appId=" + Hosts.APPID + "&clientVersion=1.5.1" + theme;
            } else {
                url = url + "?&platform=android"
                        + "&appId=" + Hosts.APPID + "&clientVersion=1.5.1" + theme;
            }

        }
        return url;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("Info", "onResult:" + requestCode + " onResult:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清除cache,防止再次传递token
        mAgentWeb.clearWebCache();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @SingleClick
    @CheckNet
    private void reload() {
        mAgentWeb.getUrlLoader().reload();
    }

}
