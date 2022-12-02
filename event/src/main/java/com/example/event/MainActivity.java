package com.example.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
    private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout l = (RelativeLayout)findViewById (R.id.lo);
        Button longClickBtn = (Button) findViewById(R.id.longClickBtn);
        registerForContextMenu(longClickBtn);

        FaceView faceView = new FaceView(MainActivity.this);
        faceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                faceView.X = motionEvent.getX() - 64;
                faceView.Y = motionEvent.getY() - 64;
                faceView.invalidate(); // 重绘
                return true;
            }
        });
        l.addView(faceView);

        Button gestureBtn = (Button) findViewById(R.id.gestureBtn);
        Button secondActBtn = (Button) findViewById(R.id.secondActBtn);
        Button menuBtn = (Button) findViewById(R.id.menuBtn);
        gestureBtn.setOnClickListener(listener);
        secondActBtn.setOnClickListener(listener);
        menuBtn.setOnClickListener(listener);
    }

    // 上下文菜单
    // 重写onCreateContextMenu菜单，为菜单添加选项值
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.msg:
                Toast.makeText(MainActivity.this, "选择了消息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.help:
                Toast.makeText(MainActivity.this, "选择了帮助", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homepage:
                Toast.makeText(MainActivity.this, "选择了首页", Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
        return super.onContextItemSelected(item);
    }

    // 选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // 点击菜单项的处理
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.msg:
                Toast.makeText(MainActivity.this, "选择了消息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.help:
                Toast.makeText(MainActivity.this, "选择了帮助", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homepage:
                Toast.makeText(MainActivity.this, "选择了首页", Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if(System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()) {
                case R.id.gestureBtn:
                    intent = new Intent(MainActivity.this, Gesture.class);
                    startActivity(intent);
                    break;
                case R.id.secondActBtn:
                    intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                    break;
                case R.id.menuBtn:
                    openOptionsMenu();
                    break;
                default:break;
            }
        }
    };
}