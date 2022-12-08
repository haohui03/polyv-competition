package com.easefun.polyv.livecloudclass.modules.translation;

//import static com.easefun.polyv.livecommon.module.modules.note.Utils.UtilRecognizer.accurateBasic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.easefun.polyv.livecloudclass.R;

/*import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.note.INoteContact;
import com.easefun.polyv.livecommon.module.modules.note.NoteData;
import com.easefun.polyv.livecommon.module.modules.note.NotePresenter;
import com.easefun.polyv.livescenes.document.PLVSDocumentWebView;*/


public class PartOfWordLayout extends LinearLayout {

    private TextView titleTv;
    private LinearLayout part_of_word_layout;

    public PartOfWordLayout(@NonNull Context context) {
        this(context, null);
    }

    public PartOfWordLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, null,0);
    }

    public PartOfWordLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.part_of_word_ly, this);
        titleTv = findViewById(R.id.part_of_word_title);
        part_of_word_layout = findViewById(R.id.part_of_word_layout);
    }

    public void setTitle(String s) {
        titleTv.setText(s);
    }

    public void addSingleItem(String s) {
        SingleItem singleItem = new SingleItem(getContext());
        singleItem.setContent(s);
        part_of_word_layout.addView(singleItem);
    }

    public void addSingleItem(String s, int type) {
        SingleItem singleItem = new SingleItem(getContext(), type);
        singleItem.setContent(s);
        singleItem.setButtonType(type);
        part_of_word_layout.addView(singleItem);
    }
}
