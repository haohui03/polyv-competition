package com.easefun.polyv.livecloudclass.modules.translation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.easefun.polyv.livecloudclass.R;


public class SingleItem extends ConstraintLayout {

    private TextView contentView;
    private ImageButton addSingleItemBtn;

    public SingleItem(@NonNull Context context) {
        this(context, null);
    }

    public SingleItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, null,0);
    }

    public SingleItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.single_item_ly, this);

        contentView = findViewById(R.id.single_item_content);
        addSingleItemBtn = findViewById(R.id.add_single_item);
        addSingleItemBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "添加单个项目", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setContent(String s) {
        contentView.setText(s);
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
