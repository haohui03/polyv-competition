package com.example.wechat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        msgFragment mf = new msgFragment();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.add(android.R.id.mid, mf);
//        ft.commit();

        ImageView msgBtn = (ImageView) findViewById(R.id.msgBtn);
        ImageView bookBtn = (ImageView) findViewById(R.id.bookBtn);
        ImageView discoveryBtn = (ImageView) findViewById(R.id.discoveryBtn);
        ImageView meBtn = (ImageView) findViewById(R.id.meBtn);
        msgBtn.setOnClickListener(l);
        bookBtn.setOnClickListener(l);
        discoveryBtn.setOnClickListener(l);
        meBtn.setOnClickListener(l);

    }
    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment = new Fragment();
            switch (view.getId()) {
                case R.id.msgBtn:
                    fragment = new msgFragment();

                    break;
                case R.id.bookBtn:
                    break;
                case R.id.discoveryBtn:
                    break;
                case R.id.meBtn:
                    break;
                default:break;
            }
            ft.replace(R.id.mid, fragment);
            ft.commit();
        }
    };
}