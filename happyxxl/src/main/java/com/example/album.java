package com.example;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.databinding.ActivityAlbumBinding;

public class album extends AppCompatActivity {
    private ImageView[] img = new ImageView[12];
    private int[] imagePath = new int[] {
        R.mipmap.img01,  R.mipmap.img02, R.mipmap.img03,
            R.mipmap.img04, R.mipmap.img05, R.mipmap.img06, R.mipmap.img07, R.mipmap.img08,
            R.mipmap.img09, R.mipmap.img10, R.mipmap.img11, R.mipmap.img12,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        GridLayout layout = (GridLayout)findViewById(R.id.layout);
        for(int i = 0; i < imagePath.length; i++) {
            img[i] = new ImageView(album.this);
            img[i].setImageResource(imagePath[i]);
            img[i].setPadding(2,2,2,2);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(300,300);
            img[i].setLayoutParams(params);
            layout.addView(img[i]);
        }
    }

}