package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button b = findViewById(R.id.button2);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AlertDialog.Builder(MainActivity.this).setTitle("警告").setMessage("游戏有风险，确定要进入吗？")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Log.i("桌面台球", "进入游戏");
//                                Intent intent=new Intent(MainActivity.this, album.class);
//                                startActivity(intent);
//
//                            }
//                        })
//                        .setNegativeButton("退出", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Log.i("桌面台球", "退出游戏");
//                                finish();
//                            }
//                        }).show();
//            }
//        });
    }
}