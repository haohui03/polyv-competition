package com.example.progressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeChat extends AppCompatActivity {
    private int[] imgArray = new int[] {
            R.drawable.img1, R.drawable.img2,R.drawable.img3,
            R.drawable.img4,R.drawable.img5,R.drawable.img6,
            R.drawable.img7,R.drawable.img8,R.drawable.img9,
            R.drawable.img10,R.drawable.img11,
            R.drawable.img1, R.drawable.img2,R.drawable.img3,
            R.drawable.img4,R.drawable.img5,R.drawable.img6,
            R.drawable.img7,R.drawable.img8,R.drawable.img9,
            R.drawable.img10,R.drawable.img11,
    };
    private String[] title = new String[]{
            "郭家鸿","黎根廷","叶浩辉","李秋科","鲍莉伟","保利威","芭蕾舞","宝莱坞","包里威","猫","一生的风景",
            "郭家鸿","黎根廷","叶浩辉","李秋科","鲍莉伟","保利威","芭蕾舞","宝莱坞","包里威","猫","一生的风景",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < imgArray.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("head", imgArray[i]);
            map.put("title", title[i]);
            listitem.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listitem, R.layout.person, new String[] {"head", "title"}, new int[] {R.id.head, R.id.title});
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> map = (Map<String, Object>) adapterView.getItemAtPosition(i);
                Toast.makeText(WeChat.this, map.get("title").toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}