package com.easefun.polyv.livestreamer.modules.note;

import static com.easefun.polyv.livecommon.module.modules.note.Utils.UtilRecognizer.accurateBasic;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.note.INoteContact;
import com.easefun.polyv.livecommon.module.modules.note.NoteData;
import com.easefun.polyv.livecommon.module.modules.note.NotePresenter;
import com.easefun.polyv.livecommon.module.modules.note.Utils.UtilRecognizer;
import com.easefun.polyv.livescenes.document.PLVSDocumentWebView;
import com.easefun.polyv.livestreamer.R;

import java.lang.ref.WeakReference;
import java.util.List;


public class NoteLayout extends ConstraintLayout implements INoteContact.INoteView {
    private PLVSDocumentWebView documentWebViewWeakReference;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private INoteContact.INotePresenter notePresenter;

    //控件
    Button testButton;
    private String TAG = "lee";
    //图片转文字的识别器

    public NoteLayout(@NonNull Context context) {
        this(context, null);
    }

    public NoteLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, null,0);
    }

    public NoteLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.note_ly, this);
        testButton = findViewById(R.id.recognize);
        testButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                documentWebViewWeakReference.buildDrawingCache();
                Bitmap bitmap = documentWebViewWeakReference.getDrawingCache();
                String res  =  accurateBasic(bitmap );
                Log.i(TAG, "recognize result:"+res);
            }
        });
    }


    public void init(IPLVLiveRoomDataManager liveRoomDataManager){
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

    }
}
