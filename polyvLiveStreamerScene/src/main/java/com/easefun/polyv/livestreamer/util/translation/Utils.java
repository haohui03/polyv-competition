package com.easefun.polyv.livestreamer.util.translation;

import android.content.Context;

public class Utils {
    public static int dip2px(Context context, float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale +0.5f);

    }
}
