package com.easefun.polyv.livecloudclass.modules.note;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.translation.OverviewOfWordLayout;
import com.easefun.polyv.livecloudclass.modules.translation.PartOfWordLayout;
import com.easefun.polyv.livecloudclass.modules.translation.SingleItem;
import com.easefun.polyv.livecommon.module.modules.note.data.CollinsSingle;
import com.easefun.polyv.livecommon.module.modules.note.data.Edict;
import com.easefun.polyv.livecommon.module.modules.note.data.Result;
import com.easefun.polyv.livecommon.module.modules.note.data.Single;

import java.util.ArrayList;
import java.util.List;


public class NoteWord extends LinearLayout {

    private Button show_hide_btn;
    private LinearLayout detailLayout;
    private TextView wordTv;
    public NoteWord(@NonNull Context context) {
        this(context, null);
    }

    public NoteWord(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NoteWord(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View _view = LayoutInflater.from(getContext()).inflate(R.layout.note_word, this);
        wordTv = _view.findViewById(R.id.word);
        show_hide_btn = _view.findViewById(R.id.show_hide_btn);
        detailLayout = _view.findViewById(R.id.detailLayout);
        detailLayout.setVisibility(View.GONE);
        show_hide_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(detailLayout != null) {
                    if(detailLayout.getVisibility() == View.GONE) {
                        detailLayout.setVisibility(View.VISIBLE);
                        show_hide_btn.setBackgroundResource(R.drawable.arrow_up);
                    }
                    else {
                        detailLayout.setVisibility(View.GONE);
                        show_hide_btn.setBackgroundResource(R.drawable.arrow_down);
                    }
                }
            }
        });
    }

    public void setWord(String w) {
        wordTv.setText(w);
    }

    public void setView(Result result) {
        String Collins = "";
        List<String > EnglishMeanings = new ArrayList<>();
        for (CollinsSingle c:
                result.getCollins()) {
            Collins+="    "+c.getEx().toString()+"  ["+c.getType()+"]"+"\n";
            Collins+="      "+c.getTran().toString()+"\n";
        }
        for (Edict e:
                result.getEnglishMeaning()) {
            for (Single s:
                    e.getGroups()) {
                for (String sampleInOneMeaning:
                        s.getExample()) {
                    EnglishMeanings.add("  ??????:"+s.getMeaning()+ "\n?????????"+ sampleInOneMeaning+"\n");
                }
                String similarWordsforAdd = "";
                for (String similarWords:
                        s.getSimilarWord()) {
                    similarWordsforAdd+=similarWords;
                }
                if(!similarWordsforAdd.equals("")){
                    EnglishMeanings.add("????????????"+similarWordsforAdd+"\n");
                }

            }

        }
        //???????????????1??????
        PhoneticLayout phoneticLayout = new PhoneticLayout(getContext());
        phoneticLayout.setPhonetic(result.getEnglishPhonetic());
        detailLayout.addView(phoneticLayout);
        //???????????????1??????
        MeaningLayout meaningLayout = new MeaningLayout(getContext());
        meaningLayout.setMeaning(result.getDst());
        detailLayout.addView(meaningLayout);
        //??????????????????????????????result???????????????????????????PartOfWordLayout
        PartOfWordLayout partOfWordLayout = new PartOfWordLayout(getContext());
        partOfWordLayout.addSingleItem(Collins, SingleItem.REMOVE_BUTTON);
        if(!EnglishMeanings.isEmpty()){
            partOfWordLayout.setTitle("????????????");
            for (String s:
                    EnglishMeanings) {
                partOfWordLayout.addSingleItem(s, SingleItem.REMOVE_BUTTON);
            }
        }


        detailLayout.addView(partOfWordLayout);
    }
}
