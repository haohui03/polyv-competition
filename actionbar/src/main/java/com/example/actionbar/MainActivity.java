package com.example.actionbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActionBar actionBar = getSupportActionBar(); // 获取Action bar
        actionBar.setTitle("标题");
//        actionBar.setDisplayShowTitleEnabled(false); // 不显示标题
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

        Button show_btn = (Button) findViewById(R.id.show_action_bar);
        Button hide_btn = (Button) findViewById(R.id.hide_action_bar);
        Button moment = (Button) findViewById(R.id.moment);

        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionBar.show();
            }
        });
        hide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionBar.hide();;
            }
        });
        moment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MomentActivity.class);
                startActivity(intent);
            }
        });
    }

    public void toTab(ActionBar actionBar) {
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.addTab(actionBar.newTab().setText("Fragment1").
                setTabListener((ActionBar.TabListener) new MyTabListener(this, Fragment1.class)));
        actionBar.addTab(actionBar.newTab().setText("Fragment2").
                setTabListener((ActionBar.TabListener) new MyTabListener(this, Fragment2.class)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "点击了搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.switchScreen:
                if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
                }else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
                }
            default:Toast.makeText(this, "点击了Action Bar的东西", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}