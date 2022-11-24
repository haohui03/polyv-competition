package com.example.wechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msgFragment mf = new msgFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.add(android.R.id.content, mf);
//        ft.commit();
        Log.i("MainActivity", "smsb");
    }
}