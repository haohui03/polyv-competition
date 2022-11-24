package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class location extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String loc = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(location.this, loc, Toast.LENGTH_LONG).show();
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("loc", loc);
                intent.putExtras(bundle);
                setResult(0x11, intent);
                finish();
            }
        });
    }


}