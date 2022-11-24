package com.example.event;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Gesture extends Activity implements GestureDetector.OnGestureListener {
    final int distance = 50; // 手势两点间的最小距离
    GestureDetector gestureDetector;  // 全局手势检测器
    ViewFlipper viewFlipper;
    private int[] imgArray = new int[] {
            R.drawable.img1, R.drawable.img2,R.drawable.img3,
            R.drawable.img4,R.drawable.img5,R.drawable.img6,
            R.drawable.img7,R.drawable.img8,R.drawable.img9,
            R.drawable.img10,R.drawable.img11,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);

        gestureDetector = new GestureDetector(Gesture.this, this);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        // 将图片加载到ViewFlipper中
        for(int i = 0; i < imgArray.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imgArray[i]);
            viewFlipper.addView(imageView);
        }

    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Toast.makeText(this, "onDown", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Toast.makeText(this, "onShowPress", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Toast.makeText(this, "onSingleTapUp", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Toast.makeText(this, "onScroll", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Toast.makeText(this, "onLongPress", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float v, float v1) {
        // 从右向左滑
        if(motionEvent1.getX() - motionEvent2.getX() > distance) {
            Toast.makeText(this, "左滑", Toast.LENGTH_SHORT).show();
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            viewFlipper.showNext(); // 展示下一张图片
            return true;
        } else if(motionEvent2.getX() - motionEvent1.getX() > distance) {
            Toast.makeText(this, "右滑", Toast.LENGTH_SHORT).show();
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
            viewFlipper.showPrevious(); // 展示上一张图片
            return true;
        }
        return false;
    }

    // 将Activity上的触摸事件交给GestureDetector处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}