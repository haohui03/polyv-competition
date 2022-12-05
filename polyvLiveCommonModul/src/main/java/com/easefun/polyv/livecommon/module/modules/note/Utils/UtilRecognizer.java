package com.easefun.polyv.livecommon.module.modules.note.Utils;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class UtilRecognizer {

    public static String accurateBasic(Bitmap bitmap){
        return accurateBasic(bitmapToBase64(bitmap));
    }
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

    //<editor-fold desc=" 内部工具类">
    private static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    private static class Token {


        /**
         * 获取token类
         */

        /**
         * 获取权限token
         *
         * @return 返回示例：
         * {
         * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
         * "expires_in": 2592000
         * }
         */
        public static String getAuth() {
            // 官网获取的 API Key 更新为你注册的
            String clientId = "t5jjozOUNyMszLfDSyfvoIGB";
            // 官网获取的 Secret Key 更新为你注册的
            String clientSecret = "QUiqs0GlI1HZZCDOKcHPA4m82Ghc2s5F";
            return getAuth(clientId, clientSecret);
        }

        /**
         * 获取API访问token
         * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
         *
         * @param ak - 百度云官网获取的 API Key
         * @param sk - 百度云官网获取的 Secret Key
         * @return assess_token 示例：
         * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
         */
        public static String getAuth(String ak, String sk) {
            // 获取token地址
            String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
            String getAccessTokenUrl = authHost
                    // 1. grant_type为固定参数
                    + "grant_type=client_credentials"
                    // 2. 官网获取的 API Key
                    + "&client_id=" + ak
                    // 3. 官网获取的 Secret Key
                    + "&client_secret=" + sk;
            try {
                URL realUrl = new URL(getAccessTokenUrl);
                // 打开和URL之间的连接
                HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                // 获取所有响应头字段
                Map<String, List<String>> map = connection.getHeaderFields();
                // 遍历所有的响应头字段
                for (String key : map.keySet()) {
                    System.err.println(key + "--->" + map.get(key));
                }
                // 定义 BufferedReader输入流来读取URL的响应
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String result = "";
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                /**
                 * 返回结果示例
                 */
                System.err.println("result:" + result);
                JSONObject jsonObject = new JSONObject(result);
                String access_token = jsonObject.getString("access_token");
                return access_token;
            } catch (Exception e) {
                System.err.printf("获取token失败！");
                e.printStackTrace(System.err);
            }
            return null;
        }

    }
    private static class HttpUtil {

        public static  String post(String requestUrl, String accessToken, String params)
                throws Exception {
            String contentType = "application/x-www-form-urlencoded";
            return HttpUtil.post(requestUrl, accessToken, contentType, params);
        }

        public static  String post(String requestUrl, String accessToken, String contentType, String params)
                throws Exception {
            String encoding = "UTF-8";
            if (requestUrl.contains("nlp")) {
                encoding = "GBK";
            }
            return HttpUtil.post(requestUrl, accessToken, contentType, params, encoding);
        }

        public static  String post(String requestUrl, String accessToken, String contentType, String params, String encoding)
                throws Exception {
            String url = requestUrl + "?access_token=" + accessToken;
            return postGeneralUrl(url, contentType, params, encoding);
        }

        public static  String postGeneralUrl(String generalUrl, String contentType, String params, String encoding)
                throws Exception {
            URL url = new URL(generalUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            // 设置通用的请求属性
            connection.setRequestProperty("Content-Type", contentType);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // 得到请求的输出流对象
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(params.getBytes(encoding));
            out.flush();
            out.close();

            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> headers = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : headers.keySet()) {
                System.err.println(key + "--->" + headers.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = null;
            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), encoding));
            String result = "";
            String getLine;
            while ((getLine = in.readLine()) != null) {
                result += getLine;
            }
            in.close();
            System.err.println("result:" + result);
            return result;
        }
    }
    //</editor-fold>
}
