package com.example.actionbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;

public class MomentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment);
        getSupportActionBar().setTitle("朋友圈");
        // 判断父activity是否为空，不为空设置导航返回按钮
        if (NavUtils.getParentActivityName(MomentActivity.this) != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // 在manife配置父Activity
        }
    }
}