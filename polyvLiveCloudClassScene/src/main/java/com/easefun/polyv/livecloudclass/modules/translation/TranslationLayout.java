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
    PartOfWordLayout exampleSentence;
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

        // 翻译结果
        WordLayout wordLayout = new WordLayout(getContext());
        wordLayout.setViewByResult(); // 还没写参数
        linearLayout.addView(wordLayout);
        //动态添加
        /*NoteLayout noteLayout1 = new NoteLayout(getContext());
        NoteLayout noteLayout2 = new NoteLayout(getContext());

        linearLayout.addView(noteLayout1);
        linearLayout.addView(noteLayout2);*/
        //移除全部
//        linearLayout.removeAllViews();
    }


    public void setScreenShotButtonOnClickListener(@Nullable OnClickListener l) {
        this.ScreenShotButton.setOnClickListener(l);
    }

}