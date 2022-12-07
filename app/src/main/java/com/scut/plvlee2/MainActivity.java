package com.scut.plvlee2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfig;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract;



public class MainActivity extends AppCompatActivity {

    private int []bottomBox = {
            R.id.bottomBox1, R.id.bottomBox2, R.id.bottomBox3, R.id.bottomBox4,
    };

    private int []bottomBtn = {
            R.id.bottomBtn1, R.id.bottomBtn2, R.id.bottomBtn3, R.id.bottomBtn4
    };

    private PLVLiveChannelConfig ConFig;

    private IPLVLiveRoomDataManager LiveRoomManage ;

    private IPLVStreamerContract.IStreamerPresenter presnter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i = 0; i < bottomBtn.length; i++) {
            ImageView img = findViewById(bottomBtn[i]);
            img.setOnClickListener(listener);
        }

        // 初始页面 - liveFragment
        getFragmentManager().beginTransaction().add(R.id.frameLayout, new liveFragment()).commit();



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // 清空没选中的按钮的背景颜色
            for(int i = 0; i < bottomBox.length; i++) {
                LinearLayout layout = findViewById(bottomBox[i]);
                layout.setBackgroundColor(getResources().getColor(R.color.none));
            }
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment = null;
            switch (view.getId()) {
                case R.id.bottomBtn1:
                    fragment = new liveFragment();
                    findViewById(bottomBox[0]).setBackgroundColor(getResources().getColor(R.color.bottom_selected));
                    break;
                case R.id.bottomBtn2: case R.id.bottomBtn3:
                    Toast.makeText(MainActivity.this, "还没写", Toast.LENGTH_SHORT).show();
                    return;
                case R.id.bottomBtn4:
                    fragment = new myFragment();
                    findViewById(bottomBox[3]).setBackgroundColor(getResources().getColor(R.color.bottom_selected));
                    break;
                default:break;
            }
            ft.replace(R.id.frameLayout, fragment);
            ft.commit();
        }
    };
}