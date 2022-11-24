package com.example.event;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        RelativeLayout l = (RelativeLayout)findViewById (R.id.lo);
        Button longClickBtn = (Button) findViewById(R.id.longClickBtn);
        longClickBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                registerForContextMenu(view); // 把长按事件注册到菜单
                openContextMenu(view); // 打开菜单
                return true;
            }
        });

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
        gestureBtn.setOnClickListener(listener);


    }

    // 重写onCreateContextMenu菜单，为菜单添加选项值
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("收藏");
        menu.add("举报");
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
            switch (view.getId()) {
                case R.id.gestureBtn:
                    Intent intent = new Intent(MainActivity.this, Gesture.class);
                    startActivity(intent);
                    break;
                default:break;
            }
        }
    };
}