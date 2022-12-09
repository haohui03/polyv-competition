package com.scut.plvlee2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class NoteTestActivity extends AppCompatActivity {

    TextView meaningTv;
    EditText inputEt;
    Button seeAns, submitAns, nextTest;
    ImageView imageView;
    String currWord, currMeaning;

    ArrayList<Pair<String,String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_test);
        getSupportActionBar().setTitle("自我测试");
        if (NavUtils.getParentActivityName(NoteTestActivity.this) != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        meaningTv = findViewById(R.id.meaningTv);
        inputEt = findViewById(R.id.inputEt);
        seeAns = findViewById(R.id.seeAns);
        imageView = findViewById(R.id.image);
        seeAns.setOnClickListener(l);;
        submitAns = findViewById(R.id.submitAns);
        submitAns.setOnClickListener(l);;
        nextTest = findViewById(R.id.nextTest);
        nextTest.setOnClickListener(l);;
        setTestData();
        getNextText();
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            imageView.setImageResource(0);
            switch (view.getId()) {
                case R.id.seeAns:
                    inputEt.setText(currWord);
                    inputEt.setEnabled(false);
                    submitAns.setEnabled(false);
                    break;
                case R.id.submitAns:
                    if(inputEt.getText().toString().equals(currWord)) {
                        imageView.setImageResource(R.drawable.right);
                    }
                    else {
                        imageView.setImageResource(R.drawable.error);
                    }
                    break;
                case R.id.nextTest:
                    getNextText();
            }
        }
    };
    private void getNextText() {
        int index = new Random().nextInt(data.size());
        currWord = data.get(index).first;
        currMeaning = data.get(index).second;
        meaningTv.setText(currMeaning);
        inputEt.setEnabled(true);
        submitAns.setEnabled(true);
        inputEt.setText("");
    }
    private void setTestData() {
        data = new ArrayList<>();
        data.add(new Pair<String, String>("key", "n." +
                "钥匙;关键;要诀;(计算机或打字机的)键;调;答案;符号说明，图例\n" +
                "vt." +
                "用键盘输入;键入;用钥匙划坏(汽车)\n" +
                "adj." +
                "关键的;最重要的;主要的"));
        data.add(new Pair<String, String>("basic", "adj." +
                "基本的，基础的，根本的;最简单的，初级的;必需的，基本需要的;（化）碱的，碱性的\n" +
                "n." +
                "基本原理，基本原则（常作basics）;（BASIC或Basic）<计>初学者通用符号指令码"));
        data.add(new Pair<String, String>("help", "v." +
                "帮助;协助;援助;改善状况;促进;促使;搀扶;带领;为取用;擅自拿走\n" +
                "n." +
                "帮助;协助;援助;有助益的东西(如忠告、钱等);有用;救助;有帮助的人（或事物）;仆人"));
    }
}