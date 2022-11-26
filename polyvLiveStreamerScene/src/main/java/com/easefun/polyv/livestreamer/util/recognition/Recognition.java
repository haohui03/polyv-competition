package com.easefun.polyv.livestreamer.util.recognition;

import android.util.Log;

import java.net.URLEncoder;

public class Recognition {

    public static String accurateBasic(String imgStr) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
        try {
            // 本地文件路径
//            String filePath = "[本地文件路径]";
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
//            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = Token.getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            Log.i("recognition", result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
