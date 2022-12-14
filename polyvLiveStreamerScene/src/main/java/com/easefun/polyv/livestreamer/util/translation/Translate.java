package com.easefun.polyv.livestreamer.util.translation;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.net.URLEncoder;

public class Translate implements Runnable {


    private Thread t;
    private String threadName="new";

    private static String token = "966fb6dca07a463ba56bc75a718533dd";

    private static String domain = "common";

    private static String UA = "5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
            "Chrome/107.0.0.0 Safari/537.36 Edg/107.0.1418.42";

    public static String translate(Activity activity, String content, String from, String to) {
        Long ts = System.currentTimeMillis();
        String data = "from=%s&to=%s&query=%s&simple_means_flag=3&sign=%s&token=966fb6dca07a463ba56bc75a718533dd&domain=common";
        String urlContent= content;
        try {
            urlContent = URLEncoder.encode( content, "UTF-8" );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sign = js.generateSign(activity,content);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("cookie", "REALTIME_TRANS_SWITCH=1; HISTORY_SWITCH=1; FANYI_WORD_SWITCH=1; SOUND_SPD_SWITCH=1; SOUND_PREFER_SWITCH=1; BIDUPSID=D26E07259B2D194C9D0D52A11C03D5BD; PSTM=1614998312; __yjs_duid=1_75324387c8bf9258935b623988dc5df41619581804229; MCITY=-:; " +
                "MBD_AT=0; BDUSS=TczdWlTWEF5cWd3cFVRSFNmczhnUHRSaWZkbUFpYkFla200UFdCcm9peGtoYUZpRVFBQUFBJCQAAAAAAAAAAAEAAAB5ZQGCxuUyNQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAGT4eWJk-Hlie; BDUSS_BFESS=TczdWlTWEF5cWd3cFVRSFNmczhnUHRSaWZkbUFpYkFla200UFdCcm9peGtoYUZpRVFBQUFBJCQAAAAAAAAAAAEAAAB5ZQGCxuUyNQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGT4eWJk-Hlie; " +
                "APPGUIDE_10_0_2=1; ZFY=ynQTxG:BbHpsE:Bvpf1uu5duYqdHEa7NtEIMLjWnp0mbA:C; BAIDU_WISE_UID=wapp_1657975581363_955; BAIDUID=FD495CD185CC24D839C7FD1682A2E26E:FG=1; BAIDUID_BFESS=FD495CD185CC24D839C7FD1682A2E26E:FG=1; __bid_n=183df24eeb04055e3c4207; Hm_lvt_64ecd8" +
                "2404c51e03dc91cb9e8c025574=1668596369,1668685375,1668843569,1668844149; Hm_lpvt_64ecd82404c51e03dc91cb9e8c025574=1668863503; ab_sr=1.0.1_MzkzODI5MGI4MzExNTI0NzRlNGFkMmIxMTU1NjNiYjUzYzc5YTE0MDc2M2I1MTI3NDliYmIzMGUzYTE2MDMzMWY1ODkzODYzZjFjYzE3NmY1NDMwMDU1NTIyZGE2NjdjNDk5NDkzZmI2MjM4NjYwYmYzZWQyY2ZlY2RlYmIyNThiYjhh" +
                "ZTZjYWUwNDRkOTM4ODdlNDcwNjczMWMxYTU2ZTE4MzZjMGU4OGEzOTIyMmFmY2JkM2I5ODAyNTdlNDU0");
        hashMap.put("User-Agent", UA);
        hashMap.put("Referer", "https://fanyi.baidu.com/?aldtype=16047");
        hashMap.put("Acs-Token","1668841410550_1668863503488_j1UQkL878lIvHCjLfk0l4JitttqB/MYiFTfaprWJ1tMI746KnONYG/7enCZDVRh0CmC+B4O5ccOdzHKQcTglSHrQwPBe0SynIrWzCNuCHnDMXV0CELZfQxwWrv7bBUpHYX+eqCzjEjhMg2OV7mm/kITaaMDZmnljxM+0vD+WFNxKYzYAep+p+vcPlqt4eh6B0ZKkkOBLn4w0BTmgVxk4snVEAOWHvg6kSGMnCsKtMFf1iGK2rQih6o9AhN1uBNy5qmlBQZHG8+1K4Z0PbkWDa1GwskC0xcgx41C63rgxa4PPwuMq3xbgbP4ES/yOVAweZztCppEfk8g6rNrt7jngxZ3+TPsxgFOzLHS7g4mKzBw=");
        return post(String.format( "https://fanyi.baidu.com/v2transapi?from=%s&to=%s",from,to),
                String.format(data,from, to, urlContent, sign), hashMap);
    }

    public static String post(String url, String data, HashMap<String, String> hashMap) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(6 * 1000);

            for (Map.Entry<String, String> m : hashMap.entrySet()) {
                httpURLConnection.setRequestProperty(m.getKey(), m.getValue());
            }

            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            printWriter.println(data);
            printWriter.flush();
            printWriter.close();

            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuilder.append(str).append("\n");
                }
                return stringBuilder.toString();

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMD5(byte[] src) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("md5");
            return byte2Hex(messageDigest.digest(src));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byte2Hex(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < src.length; i++) {
            stringBuilder.append(String.format("%02x", src[i] & 0xFF));
        }
        return stringBuilder.toString();
    }

    @Override
    public void run() {
    }

    public void start () {
        Log.i("baidu","start a translate thread");
        if (t == null) {
            t = new Thread (this, threadName);
            t.start();
        }
    }
}
