package com.scut.plvlee2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.easefun.polyv.livecloudclass.modules.note.NoteWord;
import com.easefun.polyv.livecloudclass.modules.translation.WordLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getSupportActionBar().setTitle("我的笔记");
        // 判断父activity是否为空，不为空设置导航返回按钮
        if (NavUtils.getParentActivityName(NoteActivity.this) != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        NoteWord noteWord1 = new NoteWord(this);
        noteWord1.setWord("help");
        noteWord1.setView();
        linearLayout.addView(noteWord1);

        NoteWord noteWord2 = new NoteWord(this);
        noteWord2.setWord("help");
        linearLayout.addView(noteWord2);
        NoteWord noteWord3 = new NoteWord(this);
        noteWord3.setWord("help");
        linearLayout.addView(noteWord3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchBtn:
                Toast.makeText(this, "点击了搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sortBtn:
                Toast.makeText(this, "点击了排序", Toast.LENGTH_SHORT).show();
                break;
            case R.id.testBtn:
                Toast.makeText(this, "点击了自我测试", Toast.LENGTH_SHORT).show();
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}