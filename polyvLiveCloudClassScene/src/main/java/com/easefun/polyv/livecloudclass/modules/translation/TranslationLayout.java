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
import com.easefun.polyv.livecloudclass.modules.note.NoteLayout;
import com.easefun.polyv.livecommon.module.modules.note.data.CollinsSingle;
import com.easefun.polyv.livecommon.module.modules.note.data.Edict;
import com.easefun.polyv.livecommon.module.modules.note.data.Result;
import com.easefun.polyv.livecommon.module.modules.note.data.Single;

import java.util.ArrayList;
import java.util.List;

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
    LinearLayout linearLayout;
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
        linearLayout = (LinearLayout)findViewById (R.id.linearLayout);
        ScreenShotButton = findViewById(R.id.screenshot);

        // 翻译结果
//        WordLayout wordLayout = new WordLayout(getContext());
//        wordLayout.setViewByResult(); // 还没写参数
//        linearLayout.addView(wordLayout);
        //动态添加
        /*NoteLayout noteLayout1 = new NoteLayout(getContext());
        NoteLayout noteLayout2 = new NoteLayout(getContext());

        linearLayout.addView(noteLayout1);
        linearLayout.addView(noteLayout2);*/
        //移除全部
//        linearLayout.removeAllViews();
    }


    public void addWordView(Result result){
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
        WordLayout wordLayout = new WordLayout(getContext());
        wordLayout.setViewByResult( result.getSrc(),result.getEnglishPhonetic(),result.getDst(),Collins,EnglishMeanings);
        linearLayout.addView(wordLayout);
    }
    public void setScreenShotButtonOnClickListener(@Nullable OnClickListener l) {
        this.ScreenShotButton.setOnClickListener(l);
    }

}