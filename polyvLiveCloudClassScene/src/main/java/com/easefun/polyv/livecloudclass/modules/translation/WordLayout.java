package com.easefun.polyv.livecloudclass.modules.translation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.modules.note.data.CollinsSingle;
import com.easefun.polyv.livecommon.module.modules.note.data.Edict;
import com.easefun.polyv.livecommon.module.modules.note.data.Result;
import com.easefun.polyv.livecommon.module.modules.note.data.Single;

import java.util.ArrayList;
import java.util.List;


public class WordLayout extends LinearLayout {

    private LinearLayout word_layout;
    private Result translateResult;
    private OverviewOfWordLayout overviewOfWordLayout;

    public WordLayout(@NonNull Context context) {
        this(context, null);
    }

    public WordLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, null,0);
    }

    public WordLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.word_ly, this);
        word_layout = findViewById(R.id.word_layout);
    }

    // todo 根据翻译结果设置显示的内容,差写参数
    public void setViewByResult(Result result) {
        this.translateResult = result;
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
                    EnglishMeanings.add("  释义:"+s.getMeaning()+ "\n例句："+ sampleInOneMeaning+"\n");
                }
                String similarWordsforAdd = "";
                for (String similarWords:
                        s.getSimilarWord()) {
                    similarWordsforAdd+=similarWords;
                }
                if(!similarWordsforAdd.equals("")){
                    EnglishMeanings.add("近义词："+similarWordsforAdd+"\n");
                }

            }

        }
        overviewOfWordLayout = new OverviewOfWordLayout(getContext());

        overviewOfWordLayout.setOverview(result.getSrc(), result.getEnglishPhonetic(),result.getDst());
        word_layout.addView(overviewOfWordLayout);
        PartOfWordLayout partOfWordLayout = new PartOfWordLayout(getContext());
        partOfWordLayout.setTitle("双语例句");
        partOfWordLayout.addSingleItem(Collins);
        for (String s:
                EnglishMeanings) {
            partOfWordLayout.addSingleItem(s);
        }

        word_layout.addView(partOfWordLayout);
    }


    public void setAddTheWordOnClickListener(OnClickListener l){
        this.overviewOfWordLayout.setAddTheWord(l);
    }
    /*public void init(IPLVLiveRoomDataManager liveRoomDataManager){
        this.liveRoomDataManager = liveRoomDataManager;
        notePresenter = new NotePresenter();
        notePresenter.initLiveRoom(liveRoomDataManager);
    }

    public void SetDocumentViewRef(PLVSDocumentWebView documentWebView){
        this.documentWebViewWeakReference = documentWebView ;
    }


    //p层初始化完毕调用
    @Override
    public void onPresenterInitComplete() {

    }

    //请求笔记返回的数据
    @Override
    public void onRequestNoteComplete(List<NoteData> noteData) {

    }*/
}
