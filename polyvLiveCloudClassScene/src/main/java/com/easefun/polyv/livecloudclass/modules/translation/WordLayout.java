package com.easefun.polyv.livecloudclass.modules.translation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.easefun.polyv.livecloudclass.R;


public class WordLayout extends ConstraintLayout {

    private LinearLayout word_layout;

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

    // 根据翻译结果设置显示的内容,差写参数
    public void setViewByResult() {
        OverviewOfWordLayout overviewOfWordLayout = new OverviewOfWordLayout(getContext());
        overviewOfWordLayout.setOverview("help", "发音", "n.帮助");
        word_layout.addView(overviewOfWordLayout);
        PartOfWordLayout partOfWordLayout = new PartOfWordLayout(getContext());
        partOfWordLayout.setTitle("双语例句");
        partOfWordLayout.addSingleItem("Please help me");
        partOfWordLayout.addSingleItem("项目2");
        partOfWordLayout.addSingleItem("项目3");
        word_layout.addView(partOfWordLayout);
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
