package com.scut.plvlee2;

import android.app.Application;

import com.easefun.polyv.livecommon.module.config.PLVLiveSDKConfig;

//app运行单例
public class PLVapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PLVLiveSDKConfig.init(
                new PLVLiveSDKConfig.Parameter(this)//sdk初始化所需参数
                        .isOpenDebugLog(true)//是否打开调试日志
                        .isEnableHttpDns(false)//是否使用httpdns//
        );
    }
}
