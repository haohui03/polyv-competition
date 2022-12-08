package com.easefun.polyv.livecloudclass.modules.note;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.easefun.polyv.livecloudclass.R;


public class PhoneticLayout extends LinearLayout {

    private TextView phonetic;

    public PhoneticLayout(@NonNull Context context) {
        this(context, null);
    }

    public PhoneticLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PhoneticLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View _view = LayoutInflater.from(getContext()).inflate(R.layout.phonetic_ly, this);
        phonetic = _view.findViewById(R.id.phonetic);
    }

    public void setPhonetic(String ph) {
        phonetic.setText(ph);
    }
}
