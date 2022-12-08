package com.easefun.polyv.livecloudclass.modules.note;

import static com.easefun.polyv.livecommon.module.modules.note.Utils.UtilRecognizer.accurateBasic;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.easefun.polyv.livecloudclass.modules.ppt.PLVLCFloatingPPTLayout;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.note.INoteContact;
import com.easefun.polyv.livecommon.module.modules.note.data.NoteData;
import com.easefun.polyv.livecommon.module.modules.note.NotePresenter;
import com.easefun.polyv.livecloudclass.R;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

public class NoteLayout extends FrameLayout implements INoteContact.INoteView {
    private WeakReference<PLVLCFloatingPPTLayout> floatingPPTLayoutWeakReference;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private INoteContact.INotePresenter notePresenter;
    private Context context;

    private MyView myView;//绘画选择区域

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
        this.context = context;
        myView = new MyView(getContext());
        myView.setSign(true);
        LayoutInflater.from(getContext()).inflate(R.layout.note_layout, this);
        testButton = findViewById(R.id.recognize);
        testButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                myView.setSeat(0, 0, 0, 0);
                myView.setSign(false);
                myView.postInvalidate();

//                NoteData noteData = new NoteData("xx",new Date(System.currentTimeMillis()),"笔记","zhe shi wo ");
//                notePresenter.SetNote("","",noteData);
                    recongnizetest();
                //recongnizetest();
            }
        });
        // todo 添加后会影响父容器的位置
        // 绘图提示区域
        final LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        myView.setLayoutParams(params);
        addView(myView);
    }


    public void init(IPLVLiveRoomDataManager liveRoomDataManager){
        this.liveRoomDataManager = liveRoomDataManager;
        notePresenter = new NotePresenter(getContext());
        notePresenter.initLiveRoom(liveRoomDataManager);

    }
    // todo 关闭悬浮窗识别会闪退
    //todo 只有全屏模式才能翻译，而且是在开播状态，应该新增判断逻辑
    //识别test   从documentLayout 中获取一个图片
    void recongnizetest(){
        Bitmap bitmap =  floatingPPTLayoutWeakReference.get().getScreenShot();
        this.floatingPPTLayoutWeakReference.get().setOnTouchToTranslateListener(new OnTouchListener() {
            float startX ;
            float startY;
            float endX;
            float endY;
            int m = 0, n = 0; // 移动过程的中间坐标
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    startX = motionEvent.getX();
                    startY = motionEvent.getY();
                }
                // 移动的时候进行绘制框
                if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    m = (int) motionEvent.getX();
                    n = (int) motionEvent.getY();
                    myView.setSeat((int)startX, (int)startY, m, n);
                    myView.postInvalidate();
                }
                // 抬起，
                // todo end-start为负数会闪退！！
                if(motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    // 隐藏截图提示框
                    myView.setSign(true);
                    myView.postInvalidate();

                    endX = motionEvent.getX();
                    endY = motionEvent.getY();
                    Bitmap mCropBitmap = Bitmap.createBitmap(bitmap,
                            (int)startX ,(int) startY, (int)endX- (int)startX, (int)endY-(int) startY);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String res  = accurateBasic(mCropBitmap);
                            Log.i(TAG, "recognize result:"+res);
                        }
                    }).start();
                    floatingPPTLayoutWeakReference.get().setOnTouchToTranslateListener(new OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return false;
                        }
                    });
                }
                //为true时会阻隔点击事件的传递
                return true;
            }
        });



    }
    //document View 的触摸事件  可以获取任意卫视
    public void SetLiveMediaLayoutRef(WeakReference<PLVLCFloatingPPTLayout> liveMediaLayoutRef){
        this.floatingPPTLayoutWeakReference = liveMediaLayoutRef ;

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
