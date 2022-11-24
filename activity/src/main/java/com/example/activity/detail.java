package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String content = bundle.getString("content");
        EditText editText = findViewById(R.id.editText2);
        editText.setText(content);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button refresh = (Button) findViewById(R.id.refresh);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreate(null);
            }
        });
    }
}