package com.scut.plvlee2.util.translation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getNowTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }
}