package com.easefun.polyv.livecloudclass.modules.translation;

//import static com.easefun.polyv.livecommon.module.modules.note.Utils.UtilRecognizer.accurateBasic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.note.NoteLayout;
import com.easefun.polyv.livecommon.module.modules.note.INoteContact;
import com.easefun.polyv.livecommon.module.modules.note.NotePresenter;
import com.easefun.polyv.livecommon.module.modules.note.data.CollinsSingle;
import com.easefun.polyv.livecommon.module.modules.note.data.Edict;
import com.easefun.polyv.livecommon.module.modules.note.data.NoteData;
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
    EditText search_input;
    INoteContact.INotePresenter notePresenter;

    //控件
    Button testButton;
    private String TAG = "lee";
    //图片转文字的识别器
    List<Result> translateResults = new ArrayList<>();
    ImageButton ScreenShotButton;
    ImageButton searchButton;
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
        searchButton = findViewById(R.id.search);
        search_input =findViewById(R.id.search_input);
    }

    public void init(INoteContact.INotePresenter notePresenter){
        this.notePresenter = notePresenter;

    }
    public void addWordView(Result result){
        translateResults.add(result);
        WordLayout wordLayout = new WordLayout(getContext());
        wordLayout.setViewByResult(result);
        wordLayout.setAddTheWordOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteData noteData = new NoteData("auto Save","");
                List<Result> results = new ArrayList<>();
                results.add(result);
                noteData.setResults(results);
                notePresenter.SetNote(noteData);
                Toast.makeText(getContext(), "添加整个单词到笔记", Toast.LENGTH_SHORT).show();
            }
        });
        linearLayout.addView(wordLayout);
    }
    public void setScreenShotButtonOnClickListener(@Nullable OnClickListener l) {
        this.ScreenShotButton.setOnClickListener(l);
    }

    public void setSearchOnClickListener(@Nullable OnClickListener l) {
        this.searchButton.setOnClickListener(l);
    }
    public void setSearchInputContent(String content) {
        this.search_input.setText(content);
    }
    public String getSearchInputContent(){
        return this.search_input.getText().toString();
    }
    public void removeViewInLinerLayout(){
        this.linearLayout.removeAllViews();
        translateResults.clear();
    }

}