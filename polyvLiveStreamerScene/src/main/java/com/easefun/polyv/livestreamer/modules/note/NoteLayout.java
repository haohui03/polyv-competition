package com.easefun.polyv.livestreamer.modules.note;

import static com.easefun.polyv.livecommon.module.modules.note.Utils.UtilRecognizer.accurateBasic;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.note.INoteContact;
import com.easefun.polyv.livecommon.module.modules.note.data.NoteData;
import com.easefun.polyv.livecommon.module.modules.note.NotePresenter;
import com.easefun.polyv.livestreamer.R;
import com.easefun.polyv.livestreamer.modules.document.PLVLSDocumentLayout;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;


public class NoteLayout extends FrameLayout implements INoteContact.INoteView {
    private WeakReference<PLVLSDocumentLayout> DocumentLayoutRef;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private INoteContact.INotePresenter notePresenter;

    //控件
    Button testButton;
    private String TAG = "lgt";
    //图片转文字的识别器

    public NoteLayout(@NonNull Context context) {
        this(context, null);
    }

    public NoteLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NoteLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.note_layout, this);
        testButton = findViewById(R.id.recognize);
        testButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                recongnizetest();

            }
        });
    }


    public void init(IPLVLiveRoomDataManager liveRoomDataManager){
        this.liveRoomDataManager = liveRoomDataManager;
        notePresenter = new NotePresenter(getContext());
        notePresenter.initLiveRoom(liveRoomDataManager);

    }

    //识别test   从documentLayout 中获取一个图片
    void recongnizetest(){
        Bitmap bitmap =  DocumentLayoutRef.get().GetDocumentShot();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String res  =  accurateBasic(bitmap );
                //获取识别结果
                Log.i(TAG, "recognize result:"+res);
            }
        }).start();


    }
    //document View 的触摸事件  可以获取任意卫视
    public void SetDocumentLayoutRef(WeakReference<PLVLSDocumentLayout> documentLayoutRef){
        this.DocumentLayoutRef = documentLayoutRef ;
        this.DocumentLayoutRef.get().setmOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                Log.i(TAG, "onTouch: "+motionEvent.getX()+motionEvent.getY()+motionEvent.getAction());
                return true;
            }
        });
    }


    //p层初始化完毕调用
    @Override
    public void onPresenterInitComplete() {

    }

    //请求笔记返回的数据
    @Override
    public void onRequestNoteComplete(List<NoteData> noteData) {

    }

    @Override
    public void onNewNoteAccept(NoteData noteData) {

    }


    @Override
    public void onHistoryNote(List<NoteData> noteData) {

    }
}
