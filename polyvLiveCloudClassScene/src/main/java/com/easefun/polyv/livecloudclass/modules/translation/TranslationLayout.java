package com.easefun.polyv.livecloudclass.modules.translation;

//import static com.easefun.polyv.livecommon.module.modules.note.Utils.UtilRecognizer.accurateBasic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.easefun.polyv.livecloudclass.R;

/*import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.note.INoteContact;
import com.easefun.polyv.livecommon.module.modules.note.NoteData;
import com.easefun.polyv.livecommon.module.modules.note.NotePresenter;
import com.easefun.polyv.livescenes.document.PLVSDocumentWebView;*/


public class TranslationLayout extends ConstraintLayout {
/*    private PLVSDocumentWebView documentWebViewWeakReference;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private INoteContact.INotePresenter notePresenter;*/

    //控件
    Button testButton;
    private String TAG = "lee";
    //图片转文字的识别器

    ImageButton ScreenShotButton;
    public TranslationLayout(@NonNull Context context) {
        this(context, null);
    }

    public TranslationLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, null,0);
    }

    public TranslationLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View _view = LayoutInflater.from(getContext()).inflate(R.layout.translation_ly, null);
        this.addView(_view);
        LinearLayout linearLayout = (LinearLayout)findViewById (R.id.linearLayout);
        ScreenShotButton = findViewById(R.id.screenshot);
        //动态添加
        /*NoteLayout noteLayout1 = new NoteLayout(getContext());
        NoteLayout noteLayout2 = new NoteLayout(getContext());

        linearLayout.addView(noteLayout1);
        linearLayout.addView(noteLayout2);*/
        //移除全部
//        linearLayout.removeAllViews();
        PartOfWordLayout exampleSentence = new PartOfWordLayout(getContext());
        exampleSentence.setTitle("双语例句");
        String content = "1\n" +
                "A basic sense of rhythm and pitch is essential in a music teacher. \n" +
                "\n" +
                "基本的韵律感和音高感是音乐教师的必备素质。\n" +
                "\n" +
                "《牛津高阶英汉双解词典》\n" +
                "\n" +
                "2\n" +
                "Many of the houses lacked even basic amenities. \n" +
                "\n" +
                "很多房屋甚至缺少基本的生活设施。\n" +
                "\n" +
                "《牛津高阶英汉双解词典》\n" +
                "\n" +
                "3\n" +
                "Contact with other people is a basic human need. \n" +
                "\n" +
                "和他人接触是人的基本需要。\n" +
                "\n" +
                "《牛津高阶英汉双解词典》\n" +
                "\n" +
                "4\n" +
                "The basic model is priced well within the reach of most people. \n" +
                "\n" +
                "基本款式的定价大多数人都完全负担得起。\n" +
                "\n" +
                "《牛津高阶英汉双解词典》\n" +
                "\n" +
                "5\n" +
                "The basic design of the car is very similar to that of earlier models. \n" +
                "\n" +
                "这种汽车的基本设计与早期的样式非常相似。\n" +
                "\n" +
                "《牛津高阶英汉双解词典》\n";
        exampleSentence.setContent(content);
        linearLayout.addView(exampleSentence);
    }

    public void setScreenShotButtonOnClickListener(@Nullable OnClickListener l) {
        this.ScreenShotButton.setOnClickListener(l);
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
