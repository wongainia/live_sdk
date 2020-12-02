package com.lib.showfield.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.lib.showfield.bean.MQTTBean;
import com.lib.showfield.http.respones.mqtt.NotifySecurityBean;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by upingu
 * Date  2020-07-23 15:30
 * <p>
 * Description
 **/
public class MqttService implements MqttCallback, IMqttActionListener {

    @SuppressLint("StaticFieldLeak")
    private static MqttService mMqttService = null;
    @SuppressLint("StaticFieldLeak")
    public static MqttAndroidClient mqttAndroidClient;
    private MqttConnectOptions mMqttConnectOptions;
    private IMqttActionListener mIMqttActionListener;

    public String HOST = "";//服务器地址（协议+地址+端口号）
    public String USERNAME = "";//用户名
    public String PASSWORD = "";//密码
    //public String RESPONSE_TOPIC = "";//响应主题
    public String CLIENTID = "";//设备唯一标识
    public Context mContent;
    private Intent intents;
    private OnListener mListener;
    private static OnMessageListener mMessageListener;
    private int size = 0;
    private static final String TAG = "MqttService-Log";
    private MQTTBean mqttBean;

    public static MqttService getInstance(Context context) {
        if (mMqttService == null) {
            mMqttService = new MqttService(context);
        }
        return mMqttService;
    }

    public MqttService(Context context) {
        this.mContent = context;

        mIMqttActionListener = this;
    }

    public void startMQ(NotifySecurityBean bean, OnListener mListener) {
        this.HOST = bean.getServerUri();
        this.USERNAME = bean.getUsername();
        this.PASSWORD = bean.getPassword();
        this.CLIENTID = bean.getClientId();
        Log.e(TAG, "--clientId---: " + CLIENTID);
        Log.e(TAG, "--USERNAME---: " + USERNAME);
        Log.e(TAG, "--PASSWORD---: " + PASSWORD);
        this.mListener = mListener;
        String serverURI = HOST; //服务器地址（协议+地址+端口号）
        Log.e(TAG, "初始化MQ" + serverURI);
        if (mqttAndroidClient == null) {
            mqttAndroidClient = new MqttAndroidClient(mContent.getApplicationContext(), serverURI, CLIENTID);
            mqttAndroidClient.setCallback(this); //设置监听订阅消息的回调
        }
        if (mMqttConnectOptions == null) {
            mMqttConnectOptions = new MqttConnectOptions();
            mMqttConnectOptions.setCleanSession(true); //设置是否清除缓存
            mMqttConnectOptions.setConnectionTimeout(30); //设置超时时间，单位：秒
            mMqttConnectOptions.setKeepAliveInterval(30); //设置心跳包发送间隔，单位：秒
            mMqttConnectOptions.setUserName(USERNAME); //设置用户名
            mMqttConnectOptions.setPassword(PASSWORD.toCharArray()); //设置密码
        }

        doClientConnection();

        // last will message
        //boolean doConnect = true;
//        String message = "{\"terminal_uid\":\"" + CLIENTID + "\"}";
//        String[] topic = PUBLISH_TOPIC;
//        Integer qos = 1;
//        Boolean retained = true;
//        if ((!message.equals("")) || (!topic.equals(""))) {
//            // 最后的遗嘱
//            try {
//                // mMqttConnectOptions.setWill(topic, message.getBytes(), qos.intValue(), retained.booleanValue());
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.i(TAG, "Exception Occured");
//                doConnect = false;
//                mIMqttActionListener.onFailure(null, e);
//            }
//        }
    }

    public static void subscribeTopic(String[] topicUrls, OnMessageListener mListener) {
        mMessageListener = mListener;
        try {
            int[] topics = new int[topicUrls.length];
            for (int i = 0; i < topicUrls.length; i++) {
                topics[i] = 1;
            }
            if (mqttAndroidClient != null) {
                int[] topic = new int[topicUrls.length];
                for (int i = 0; i < topicUrls.length; i++) {
                    topic[i] = 1;
                }
                mqttAndroidClient.subscribe(topicUrls, topic);//订阅消息的服务质量，topic值 可传0、1或2
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unSubscribeTopic(String[] topicUrls) {
        try {
            if (mqttAndroidClient != null && mqttAndroidClient.isConnected()) {
                mqttAndroidClient.unsubscribe(topicUrls);
                Log.d("zzy", "----unSubscribeTopic----");
                //mMessageListener = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接MQTT服务器
     */
    private void doClientConnection() {
        try {
            if (!mqttAndroidClient.isConnected() && isConnectIsNomarl()) {
                Log.e(TAG, "连接MQTT服务器" + HOST);
                mqttAndroidClient.connect(mMqttConnectOptions, null, mIMqttActionListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断网络是否连接
     */
    private boolean isConnectIsNomarl() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                mContent.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            Log.e(TAG, "当前网络名称：" + name);
            return true;
        } else {
            Log.e(TAG, "没有可用网络");
            /*没有可用网络的时候，延迟3秒再尝试重连*/
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "没有可用网络doClientConnection");
                    doClientConnection();
                    /*size++;
                    if(size>5) {
                        mListener.clientFailure();
                        return;
                    }*/
                }
            }, 5000);
            return false;
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        LogUtils.e(TAG, "连接断开 ");
        mListener.clientIng();
        doClientConnection();//连接断开，重连
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        try {
            String enCodeMsg = new String(message.getPayload());
            LogUtils.e(TAG, "---------mqtt---------: " + message);
            if (enCodeMsg.contains("cmd")) {
                mqttBean = new Gson().fromJson(enCodeMsg, MQTTBean.class);
                mMessageListener.message(mqttBean);
            }
            //收到消息，这里弹出Toast表示。如果需要更新UI，可以使用广播或者EventBus进行发送
            //收到其他客户端的消息后，响应给对方告知消息已到达或者消息有问题等
            //response("message arrived");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.e(TAG, "连接成功 " + HOST);
        mListener.clientSuccess(size);
        size = 0;
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        exception.printStackTrace();
        Log.e(TAG, "连接失败 ");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListener.clientIng();
                doClientConnection();//连接失败，重连（可关闭服务器进行模拟）

            }
        }, 5000);

        mListener.clientFailure();
    }

    public static void disconnect(Context context) {
        try {
            if (mqttAndroidClient != null && mqttAndroidClient.isConnected()) {
                mqttAndroidClient.disconnect(0); //断开连接
                mqttAndroidClient.unregisterResources();
                mqttAndroidClient = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnListener {

        void clientSuccess(int num);

        void clientIng();

        void clientFailure();

    }

    public interface OnMessageListener {
        void message(MQTTBean message);
    }
}
