package com.example.progressbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {
    private ImageSwitcher imageSwitcher;
    private int[] imgArray = new int[] {
            R.drawable.img1, R.drawable.img2,R.drawable.img3,
            R.drawable.img4,R.drawable.img5,R.drawable.img6,
            R.drawable.img7,R.drawable.img8,R.drawable.img9,
    };
    private int index = 9;
    private ProgressBar progressBar;
    private static Handler mHandler;
    private int progress = 0;
    private float touchDownX, touchUpX;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0x111) {
                    progressBar.setProgress(progress);
                }
                else {
                    Toast.makeText(MainActivity.this, "加载完成", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    imageSwitcher.setImageResource(imgArray[index]);
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    progress = doWork();
                    Message m = new Message();
                    if(progress < 100) {
                        m.what = 0x111;
                        mHandler.sendMessage(m);
                    }
                    else {
                        m.what = 0x110;
                        mHandler.sendMessage(m);
                        break;
                    }
                }
            }
            private int doWork() {
                progress +=Math.random()*10;
                try {
                    Thread.sleep(200);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return progress;
            }
        }).start();
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(MainActivity.this);
                index = imgArray.length - 1;
                return imageView;
            }
        });
        imageSwitcher.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    touchDownX = motionEvent.getX();
                    return true;
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    touchUpX = motionEvent.getX();
                    if(touchUpX - touchDownX > 100) {  // 向右滑，后退一张
                        index = index == 0 ? imgArray.length - 1 : index - 1;
                        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_out_right));
                        imageSwitcher.setImageResource(imgArray[index]);
                    }
                    else if (touchDownX - touchUpX > 100) {
                        index = index == imgArray.length - 1 ? 0 : index + 1;
                        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
                        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
                        imageSwitcher.setImageResource(imgArray[index]);
                    }
                    return true;
                }
                return false;
            }
        });

        Button seeAll = (Button) findViewById(R.id.all);
        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, gridView.class);
                startActivity(intent);
            }
        });


    }
}