package com.example.myview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.myLayout);
        CatView cat = new CatView(this);
        cat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                cat.bitmapX = motionEvent.getX();
                cat.bitmapY = motionEvent.getY();
                cat.invalidate();
                return true;
            }
        });
        frameLayout.addView(cat);
    }
}