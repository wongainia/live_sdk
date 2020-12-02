package com.lib.showfield.ui.view.dialog;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hpplay.sdk.source.api.IConnectListener;
import com.hpplay.sdk.source.api.ILelinkPlayerListener;
import com.hpplay.sdk.source.api.LelinkSourceSDK;
import com.hpplay.sdk.source.browse.api.IBrowseListener;
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.lib.showfield.base.BaseDialog;
import com.lib.showfield.interfaces.BaseHandlerCallBack;
import com.lib.showfield.interfaces.OnPrectionListener;
import com.lib.showfield.livedata.LelinkSourceLiveData;
import com.lib.showfield.model.LelinkSourceViewModel;

import java.util.List;

/**
 * Created by
 * 处理投屏状态 不关注UI
 **/
public abstract class CommonProjectionDiaLog extends BaseDialog.Builder<BaseDialog.Builder> implements BaseHandlerCallBack {
    private String mUrl;
    protected LelinkServiceInfo mSelectInfo;
    private LelinkSourceViewModel mViewModel;
    protected OnPrectionListener mOnPrectionListener;
    //private WeakHandler handler = new WeakHandler(this::callBack);

    public CommonProjectionDiaLog(Context context, String url) {//ComponentActivity
        super(context);
        this.mUrl = url;
        mViewModel = new ViewModelProvider((ComponentActivity) context).get(LelinkSourceViewModel.class);
        LiveData<Boolean> leLinkLiveData = mViewModel.getLeLinkLiveData();
        // 如果连接的sdk生命周期是全局，此代码注销
        leLinkLiveData.removeObservers((ComponentActivity) context);
        leLinkLiveData.observe((ComponentActivity) context, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(LelinkSourceLiveData.TAG, "接受消息了: " + aBoolean);
                initSDKStatusListener();
            }
        });

        Log.d(LelinkSourceLiveData.TAG, "CommonProjectionDiaLog: " + mViewModel.hashCode());

    }

    //由实现控制搜索连接设备时机
    protected void starConnect() {
        mViewModel.connectLiveData(getContext());
    }

    //由实现类赋值 选中适配器回调 设备信息
    protected void setSelectInfo(LelinkServiceInfo selectInfo) {
        mOnPrectionListener.onStartConnect();
        mSelectInfo = selectInfo;
        mViewModel.connect(selectInfo, mUrl);
    }

    public void updateUrl(String url) {
        this.mUrl = url;
        mViewModel.connect(mSelectInfo, mUrl);
    }

    //连接sdk后 发出通知后监听各项状态
    void initSDKStatusListener() {
        //设置搜索监听 服务搜索监听
        LelinkSourceSDK.getInstance().setBrowseResultListener(iBrowseListener);
        //设置连接监听器
        LelinkSourceSDK.getInstance().setConnectListener(iConnectListener);
        //设置播控监听
        LelinkSourceSDK.getInstance().setPlayListener(lelinkPlayerListener);
    }

    //onBrowse是在子线程工作
    IBrowseListener iBrowseListener = new IBrowseListener() {

        //IBrowseListener.BROWSE_SUCCESS：搜索成功
        //IBrowseListener.BROWSE_ERROR_AUTH：搜索失败，**Auth错误，请检查您的网络设置或AppId和AppSecret**
        @Override
        public void onBrowse(int resultCode, List<LelinkServiceInfo> list) {
            // Log.d(LelinkSourceLiveData.TAG, "-------------->list size : " + list.size() + "   status: " + resultCode);
            if (resultCode == IBrowseListener.BROWSE_ERROR_AUTH) {
                getContentView().post(() -> {
                    Toast.makeText(getContext(), "授权失败", Toast.LENGTH_SHORT).show();
                });
                return;
            }
            getContentView().post(() -> {
                updateAdapter(list);
            });

        }
    };

    String text = "";
    IConnectListener iConnectListener = new IConnectListener() {
        @Override
        public void onConnect(LelinkServiceInfo lelinkServiceInfo, int extra) {
//            getContentView().post(() -> {
//                mOnPrectionListener.onConnectSuccess(lelinkServiceInfo);
//                ToastUtils.showShort("连接成功:" + lelinkServiceInfo.getName());
//            });
            Log.d(LelinkSourceLiveData.TAG, "onConnect:" + lelinkServiceInfo.getName());
        }

        @Override
        public void onDisconnect(LelinkServiceInfo lelinkServiceInfo, int what, int extra) {
            Log.d(LelinkSourceLiveData.TAG, "onDisconnect:" + lelinkServiceInfo.getName() + " disConnectType:" + what + " extra:" + extra);
            text = "";
            if (what == IConnectListener.CONNECT_INFO_DISCONNECT) {

                if (TextUtils.isEmpty(lelinkServiceInfo.getName())) {
                    text = "pin码连接断开";
                } else {
                    text = lelinkServiceInfo.getName() + "连接断开";
                }

            } else if (what == IConnectListener.CONNECT_ERROR_FAILED) {
                if (extra == IConnectListener.CONNECT_ERROR_IO) {
                    text = lelinkServiceInfo.getName() + "连接失败";
                } else if (extra == IConnectListener.CONNECT_ERROR_IM_WAITTING) {
                    text = lelinkServiceInfo.getName() + "等待确认";
                } else if (extra == IConnectListener.CONNECT_ERROR_IM_REJECT) {
                    text = lelinkServiceInfo.getName() + "连接拒绝";
                } else if (extra == IConnectListener.CONNECT_ERROR_IM_TIMEOUT) {
                    text = lelinkServiceInfo.getName() + "连接超时";
                } else if (extra == IConnectListener.CONNECT_ERROR_IM_BLACKLIST) {
                    text = lelinkServiceInfo.getName() + "连接黑名单";
                }
            }
            getContentView().post(() -> {
                mOnPrectionListener.onDisConnectFail();
                Toast.makeText(getContext(), "连接断开:" + text, Toast.LENGTH_SHORT).show();
            });

        }
    };

    ILelinkPlayerListener lelinkPlayerListener = new ILelinkPlayerListener() {


        @Override
        public void onLoading() {
            getContentView().post(new Runnable() {
                @Override
                public void run() {
                    mOnPrectionListener.onConnectSuccess(mSelectInfo);
                    Toast.makeText(getContext(), "开始加载", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStart() {
            getContentView().post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "开始播放", Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void onPause() {
            getContentView().post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "暂停播放", Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void onCompletion() {

            getContentView().post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "播放完成", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStop() {
            getContentView().post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "播放停止", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onSeekComplete(int i) {

        }

        @Override
        public void onInfo(int i, int i1) {

        }

        String text = null;

        //错误回调
        @Override
        public void onError(int what, int extra) {
//            Log.d(TAG, "onError what:" + what + " extra:" + extra);
            if (what == PUSH_ERROR_INIT) {
                if (extra == PUSH_ERRROR_FILE_NOT_EXISTED) {
                    text = "文件不存在";
                } else if (extra == PUSH_ERROR_IM_OFFLINE) {
                    text = "IM TV不在线";
                } else if (extra == PUSH_ERROR_IMAGE) {

                } else if (extra == PUSH_ERROR_IM_UNSUPPORTED_MIMETYPE) {
                    text = "IM不支持的媒体类型";
                } else {
                    text = "未知";
                }
            } else if (what == MIRROR_ERROR_INIT) {
                if (extra == MIRROR_ERROR_UNSUPPORTED) {
                    text = "不支持镜像";
                } else if (extra == MIRROR_ERROR_REJECT_PERMISSION) {
                    text = "镜像权限拒绝";
                } else if (extra == MIRROR_ERROR_DEVICE_UNSUPPORTED) {
                    text = "设备不支持镜像";
                } else if (extra == NEED_SCREENCODE) {
                    text = "请输入投屏码";
                }
            } else if (what == MIRROR_ERROR_PREPARE) {
                if (extra == MIRROR_ERROR_GET_INFO) {
                    text = "获取镜像信息出错";
                } else if (extra == MIRROR_ERROR_GET_PORT) {
                    text = "获取镜像端口出错";
                } else if (extra == NEED_SCREENCODE) {
                    text = "请输入投屏码";

                } else if (what == PUSH_ERROR_PLAY) {
                    if (extra == PUSH_ERROR_NOT_RESPONSED) {
                        text = "播放无响应";
                    } else if (extra == NEED_SCREENCODE) {
                        text = "请输入投屏码";
                    } else if (extra == RELEVANCE_DATA_UNSUPPORTED) {
                        text = "老乐联不支持数据透传,请升级接收端的版本！";
                    } else if (extra == ILelinkPlayerListener.PREEMPT_UNSUPPORTED) {
                        text = "投屏码模式不支持抢占";
                    }
                } else if (what == PUSH_ERROR_STOP) {
                    if (extra == ILelinkPlayerListener.PUSH_ERROR_NOT_RESPONSED) {
                        text = "退出 播放无响应";
                    }
                } else if (what == PUSH_ERROR_PAUSE) {
                    if (extra == ILelinkPlayerListener.PUSH_ERROR_NOT_RESPONSED) {
                        text = "暂停无响应";
                    }
                } else if (what == PUSH_ERROR_RESUME) {
                    if (extra == ILelinkPlayerListener.PUSH_ERROR_NOT_RESPONSED) {
                        text = "恢复无响应";
                    }
                }

            } else if (what == MIRROR_PLAY_ERROR) {
                if (extra == MIRROR_ERROR_FORCE_STOP) {
                    text = "接收端断开";
                } else if (extra == MIRROR_ERROR_PREEMPT_STOP) {
                    text = "镜像被抢占";
                }
            } else if (what == MIRROR_ERROR_CODEC) {
                if (extra == MIRROR_ERROR_NETWORK_BROKEN) {
                    text = "镜像网络断开";
                }
            }

            getContentView().post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();

                }
            });
        }

        @Override
        public void onVolumeChanged(float percent) {

        }

        /**
         * 播放进度信息回调
         * @param duration 总长度：单位秒
         * @param position 当前进度：单位秒
         */
        @Override
        public void onPositionUpdate(long duration, long position) {

        }
    };


    @Override
    public void callBack(Message msg) {

    }


    @Override
    public void dismiss() {
        getContentView().removeCallbacks(null);
        // 如果sdk生命周期全局，此代码注销
        mViewModel.getLeLinkLiveData().removeObservers((ComponentActivity) getContext());
        super.dismiss();
    }


    protected abstract void updateAdapter(List<LelinkServiceInfo> list);

    private static final int MSG_SEARCH_RESULT = 100;
    private static final int MSG_CONNECT_FAILURE = 101;
    private static final int MSG_CONNECT_SUCCESS = 102;
    private static final int MSG_UPDATE_PROGRESS = 103;
}
