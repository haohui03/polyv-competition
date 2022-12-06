package com.scut.plvlee2;

import static com.easefun.polyv.livecommon.module.config.PLVLiveScene.CLOUDCLASS;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfigFiller;
import com.easefun.polyv.livecommon.module.config.PLVLiveScene;
import com.easefun.polyv.livecommon.module.modules.player.floating.PLVFloatingPlayerManager;
import com.easefun.polyv.livecommon.module.utils.result.PLVLaunchResult;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVUtils;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.livescenes.feature.login.IPLVSceneLoginManager;
import com.plv.livescenes.feature.login.PLVLiveLoginResult;
import com.plv.livescenes.feature.login.PLVSceneLoginManager;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.scut.plvlee2.Streamlive.StreamTest;
import com.scut.plvlee2.util.translation.Translate;

public class liveFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        loginManager = new PLVSceneLoginManager();
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        Button startLive = (Button) view.findViewById(R.id.startLive);
        Button TestLive = (Button) view.findViewById(R.id.TestLive);
        Button watchLive = (Button) view.findViewById(R.id.watchLive);
        watchLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginLive();
            }
        });
        TestLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), StreamTest.class));
            }
        });
        startLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), mLoginActivity.class));
            }
        });
        return view;
    }

    private String getViewerId() {
        /**
         *  todo 请务必在这里替换为你的学员(用户)ID，设置学员(用户)ID的意义详细可以查看：https://github.com/polyv/polyv-android-cloudClass-sdk-demo/wiki/6-%E8%AE%BE%E7%BD%AE%E5%AD%A6%E5%91%98%E5%94%AF%E4%B8%80%E6%A0%87%E8%AF%86%E7%9A%84%E6%84%8F%E4%B9%89
         */
        return PLVUtils.getAndroidId(getContext()) + "";
    }

    private String getViewerName() {
        /**
         * todo 请务必在这里替换为你的学员(用户)昵称
         */
        return "观众" + getViewerId();
    }

    private String getViewerAvatar(){
        //todo 在这里可替换为你的学员(用户)头像地址
        return "";
    }
    private IPLVSceneLoginManager loginManager;
    private void loginLive() {
        PLVFloatingPlayerManager.getInstance().clear();

        //todo 更改获取秘钥的方法，动态设置channelId
        final String appId = "gesrlyj3fo";
        final String appSecret ="9a8b89be009f49fa836e36cb081a464a";
        final String userId = "ef727d9c48";
        final String channelId = "3513971";
        loginManager.loginLiveNew(appId, appSecret, userId, channelId, new IPLVSceneLoginManager.OnLoginListener<PLVLiveLoginResult>() {
            @Override
            public void onLoginSuccess(PLVLiveLoginResult plvLiveLoginResult) {
                PLVLiveChannelConfigFiller.setupAccount(userId, appId, appSecret);
                PLVLiveChannelType channelType = plvLiveLoginResult.getChannelTypeNew();
                //进入云课堂场景
                if (PLVLiveScene.isCloudClassSceneSupportType(channelType)) {
                    PLVLaunchResult launchResult = PLVLCCloudClassActivity.launchLive(getActivity(), channelId, channelType, getViewerId(), getViewerName(), getViewerAvatar());
                    if (!launchResult.isSuccess()) {
                        ToastUtils.showShort(launchResult.getErrorMessage());
                    }
                }  else {
                    ToastUtils.showShort(R.string.plv_scene_login_toast_cloudclass_no_support_type);
                }
            }

            @Override
            public void onLoginFailed(String msg, Throwable throwable) {
                ToastUtils.showShort(msg);
            }
        });
    }

}
