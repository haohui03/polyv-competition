package com.example.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button detail = (Button) findViewById(R.id.detail);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, detail.class);
                EditText editText = findViewById(R.id.editText);
                String content = editText.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putCharSequence("content", content);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        Button select_location = (Button) findViewById(R.id.select_location);
        select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, location.class);
                startActivityForResult(intent, 0x11);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x11 && resultCode == 0x11) {
            Bundle bundle = data.getExtras();
            String loc = bundle.getString("loc");
            TextView textView = (TextView) findViewById(R.id.location);
            textView.setText(loc);
        }
    }
}