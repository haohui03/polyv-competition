package com.scut.plvlee2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.DialogInterface;
import android.content.Intent;
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
        super.onCreate(savedInstanceState);
        notePresenter = new NotePresenter(getApplicationContext());
        notePresenter.initViewLocalUser("admin");
        notePresenter.registerView(this);
        notePresenter.requestNote("","");
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
                String[] items = new String[]{
                        "按添加日期升序",
                        "按添加日期降序",
                        "按字典序升序",
                        "按字典序降序",
                };
                int default_selected = 0;
                final String[] selected = {items[default_selected]};
                AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
                builder.setTitle("选择排序方式");
                builder.setSingleChoiceItems(items,default_selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selected[0] = items[i];
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(NoteActivity.this, "选择了"+ selected[0], Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
                break;
            case R.id.testBtn:
                startActivity(new Intent(NoteActivity.this, NoteTestActivity.class));
                break;
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