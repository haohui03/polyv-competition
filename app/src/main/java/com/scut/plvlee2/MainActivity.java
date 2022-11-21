package com.scut.plvlee2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfig;
import com.easefun.polyv.livecommon.module.config.PLVLiveSDKConfig;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract;
import com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter;
import com.plv.livescenes.config.PLVLiveChannelType;

public class MainActivity extends AppCompatActivity {

    private PLVLiveChannelConfig ConFig;

    private IPLVLiveRoomDataManager LiveRoomManage ;

    private IPLVStreamerContract.IStreamerPresenter presnter;

    void init(){
        ConFig = new PLVLiveChannelConfig();
        ConFig.setChannelType(PLVLiveChannelType.SEMINAR);
        LiveRoomManage = new  PLVLiveRoomDataManager(ConFig);
        presnter = new PLVStreamerPresenter(LiveRoomManage);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startLive = (Button)findViewById(R.id.startLive);
        startLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,mLoginActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}