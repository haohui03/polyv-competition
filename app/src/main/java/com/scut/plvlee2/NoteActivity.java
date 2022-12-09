package com.scut.plvlee2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.easefun.polyv.livecommon.module.modules.note.INoteContact;
import com.easefun.polyv.livecommon.module.modules.note.NotePresenter;
import com.easefun.polyv.livecommon.module.modules.note.data.NoteData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteActivity extends AppCompatActivity implements INoteContact.INoteView {


    INoteContact.INotePresenter notePresenter;
    LinearLayout linearLayout;
    List<NoteData> noteDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        notePresenter = new NotePresenter(getApplicationContext());
        notePresenter.initUser("admin");
        notePresenter.registerView(this);
        notePresenter.requestNote("","");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getSupportActionBar().setTitle("我的笔记");
        // 判断父activity是否为空，不为空设置导航返回按钮
        if (NavUtils.getParentActivityName(NoteActivity.this) != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        linearLayout = findViewById(R.id.linearLayout);
//        NoteWord noteWord1 = new NoteWord(this);
//        noteWord1.setWord("help");
//        noteWord1.setView();
//        linearLayout.addView(noteWord1);
//
//        NoteWord noteWord2 = new NoteWord(this);
//        noteWord2.setWord("help");
//        linearLayout.addView(noteWord2);
//        NoteWord noteWord3 = new NoteWord(this);
//        noteWord3.setWord("help");
//        linearLayout.addView(noteWord3);
    }

    void RefreshView(){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (NoteData note:
                        noteDataList) {

                    if(note.getTranslateResults().isEmpty()){
                        continue;
                    }
                    NoteWord noteWord1 = new NoteWord(getApplicationContext());
                    noteWord1.setWord(note.getTranslateResults().get(0).getSrc());
                    noteWord1.setView(note.getTranslateResults().get(0));
                    linearLayout.addView(noteWord1);
                }
            }
        });



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

    @Override
    public void onPresenterInitComplete() {

    }

    @Override
    public void onRequestNoteComplete(List<NoteData> noteData) {
            this.noteDataList= noteData;
            RefreshView();
    }

    @Override
    public void onNewNoteAccept(NoteData noteData) {
        noteDataList.add(noteData);
        RefreshView();
    }

    @Override
    public void onHistoryNote(List<NoteData> noteData) {

    }
}