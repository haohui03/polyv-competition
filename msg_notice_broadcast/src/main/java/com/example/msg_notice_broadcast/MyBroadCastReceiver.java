package com.example.msg_notice_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadCastReceiver extends BroadcastReceiver {
    private static final String ACTION1 = "main";
    private static final String ACTION2 = "马云";
    @Override
    public void onReceive(Context context, Intent intent) {
        // 接收广播
        if(intent.getAction().equals(ACTION1)) {
            Toast.makeText(context, "MyBroadCastReceiver收到main的广播",Toast.LENGTH_SHORT).show();
        }
        else if(intent.getAction().equals(ACTION2)) {
            Toast.makeText(context, "MyBroadCastReceiver收到马云的广播",Toast.LENGTH_SHORT).show();
        }
    }
}
