package com.example.actionbar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;



public class MyTabListener implements ActionBar.TabListener {
    private final Activity activity;  // 用于指定要加载Fragment的Activity
    private final Class aClass; // 用于指定要加载Fragment对应的类
    private Fragment fragment;

    public MyTabListener(Activity activity, Class aClass) {
        this.activity = activity;
        this.aClass = aClass;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if(fragment == null) {
            fragment = Fragment.instantiate(activity, aClass.getName()); // 反射
            fragmentTransaction.add(android.R.id.content, fragment, null);
        }
        fragmentTransaction.attach(fragment);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if(fragment != null) {
            fragmentTransaction.detach(fragment);
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
