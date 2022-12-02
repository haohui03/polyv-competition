package com.example.msg_notice_broadcast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final int NOTIFICATION_ID = 0x123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 广播
        findViewById(R.id.broadcast_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("马云");
                sendBroadcast(intent); // 发送广播
                // 要在manifest注册广播接收器
            }
        });

        // 状态栏通知
        findViewById(R.id.notification_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(MainActivity.this);
                builder.setAutoCancel(true); // 点击打开后自动消失
                builder.setSmallIcon(R.drawable.img);
                builder.setContentTitle("点击有惊喜!");
                builder.setContentText("点击查看详情>>");
                builder.setWhen(System.currentTimeMillis()); // 即时发送
                builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE); // 设置声音和振动
                // 创建启动详细页面的Intent
                Intent intent = new Intent(MainActivity.this, DeatilActivity.class);
                // 可以由其他的应用程序在稍晚的时间触发的Intent机制
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, (new Random().nextInt(100)), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                // 设置点击后跳转
                builder.setContentIntent(pendingIntent);
                // 发通知
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }
        });

        // Toast
        findViewById(R.id.show_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "活着就是为了改变世界", Toast.LENGTH_SHORT).show();
            }
        });

        // 对话框
        findViewById(R.id.with_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setIcon(R.drawable.img);
                alertDialog.setTitle("乔布斯：");
                alertDialog.setMessage("活着就是为了改变世界");
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "不好评价", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "不好评价", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "说得好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "说得好", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });
        findViewById(R.id.with_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] items = new String[]{
                        "新","年","快","楽"
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.img);
                builder.setTitle("祝你");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "点击了 " + items[i], Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
            }
        });
        findViewById(R.id.with_single_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] items = new String[]{
                        "新","年","快","楽"
                };
                int default_selected = 0;
                final String[] selected = {items[default_selected]};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.img);
                builder.setTitle("选择");
                builder.setSingleChoiceItems(items,default_selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "点击了 " + items[i], Toast.LENGTH_SHORT).show();
                        selected[0] = items[i];
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "最终选择了"+ selected[0], Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
            }
        });
        findViewById(R.id.with_mult_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] checked = new boolean[] {false, true, false, true};
                String[] items = new String[]{
                        "新","年","快","楽"
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.img);
                builder.setTitle("祝你");
                builder.setMultiChoiceItems(items, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        checked[i] = b;
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String result = "";
                        for(int t = 0; t < checked.length; t++) {
                            if(checked[t]) {
                                result += items[t] + " ";
                            }
                        }
                        if(result != "") {
                            Toast.makeText(MainActivity.this, "选择了" + result, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.create().show();
            }
        });
    }
}