package com.easefun.polyv.livecloudclass.modules.translation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.easefun.polyv.livecloudclass.R;


public class SingleItem extends ConstraintLayout {
    public static final int ADD_BUTTON = 1;
    public static final int REMOVE_BUTTON = 2;


    private TextView contentView;
    private Button add_remove_btn;

    public SingleItem(@NonNull Context context) {
        this(context, null);
    }
    public SingleItem(@NonNull Context context, int type) {
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
        add_remove_btn = findViewById(R.id.add_remove_btn);
        add_remove_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "添加/删除单个项目", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setContent(String s) {
        contentView.setText(s);
    }


    public void setButtonType(int t) {
        if(t == SingleItem.ADD_BUTTON) {
            add_remove_btn.setBackgroundResource(R.drawable.small_add);
        }
        else if(t == SingleItem.REMOVE_BUTTON) {
            add_remove_btn.setBackgroundResource(R.drawable.small_remove);
        }
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
