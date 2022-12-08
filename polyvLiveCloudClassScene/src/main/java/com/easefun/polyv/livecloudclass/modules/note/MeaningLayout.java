package com.easefun.polyv.livecloudclass.modules.note;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.easefun.polyv.livecloudclass.R;


public class MeaningLayout extends LinearLayout {

    private TextView meaning;

    public MeaningLayout(@NonNull Context context) {
        this(context, null);
    }

    public MeaningLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MeaningLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View _view = LayoutInflater.from(getContext()).inflate(R.layout.meaning_ly, this);
        meaning = _view.findViewById(R.id.meaning);
    }

    public void setMeaning(String m) {
        meaning.setText(m);
    }
}
