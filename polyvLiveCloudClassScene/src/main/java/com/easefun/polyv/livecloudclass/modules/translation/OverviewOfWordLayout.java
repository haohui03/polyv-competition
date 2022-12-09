package com.easefun.polyv.livecloudclass.modules.translation;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.modules.note.data.Result;


public class OverviewOfWordLayout extends ConstraintLayout {

    private TextView wordTv;
    private TextView phTv;
    private TextView tslTv;
    private ImageView addTheWord;

    private Result translateResult;
    public OverviewOfWordLayout(@NonNull Context context) {
        this(context, null);
    }

    public OverviewOfWordLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, null,0);
    }

    public OverviewOfWordLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        addTheWord= findViewById(R.id.addTheWord);
    }

    public void setAddTheWord(OnClickListener l){
        addTheWord.setOnClickListener(l);
    }
    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.overview_of_word_ly, this);
        wordTv = findViewById(R.id.word);
        phTv = findViewById(R.id.phonetic);
        tslTv = findViewById(R.id.translation);
    }

    //设置 单词、音标、翻译
    public void setOverview(String word, String ph, String tsl) {
        wordTv.setText(word);
        phTv.setText(ph);
        tslTv.setText(tsl);
    }

}
