package com.example.progressbar;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.progressbar.databinding.ActivityGridViewBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class gridView extends AppCompatActivity {
    private int[] imgArray = new int[] {
            R.drawable.img1, R.drawable.img2,R.drawable.img3,
            R.drawable.img4,R.drawable.img5,R.drawable.img6,
            R.drawable.img7,R.drawable.img8,R.drawable.img9,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        GridView gridView = findViewById(R.id.gridView);
        List<Map<String,Object>> listitem = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < imgArray.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", imgArray[i]);
            listitem.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listitem, R.layout.cell, new String[]{"image"}, new int[] {R.id.image});
        gridView.setAdapter(simpleAdapter);

        Button to_wechat = (Button) findViewById(R.id.to_wechat);
        to_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(gridView.this, WeChat.class);
                startActivity(intent);
            }
        });
    }
}