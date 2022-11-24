package com.example.wechat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class msgFragment extends Fragment {
    private int[] imgArray = new int[] {
            R.drawable.img1, R.drawable.img2,R.drawable.img3,
            R.drawable.img4,R.drawable.img5,R.drawable.img6,
            R.drawable.img7,R.drawable.img8,R.drawable.img9,
            R.drawable.img10,R.drawable.img11,
            R.drawable.img1, R.drawable.img2,R.drawable.img3,
            R.drawable.img4,R.drawable.img5,R.drawable.img6,
            R.drawable.img7,R.drawable.img8,R.drawable.img9,
            R.drawable.img10,R.drawable.img11,
    };
    private String[] title = new String[]{
            "郭家鸿","黎根廷","叶浩辉","李秋科","鲍莉伟","保利威","芭蕾舞","宝莱坞","包里威","猫","一生的风景",
            "郭家鸿","黎根廷","叶浩辉","李秋科","鲍莉伟","保利威","芭蕾舞","宝莱坞","包里威","猫","一生的风景",
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        return view;
    }
}