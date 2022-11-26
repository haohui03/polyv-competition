package com.easefun.polyv.livestreamer.util.translation;

import android.app.Activity;
import android.util.Log;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.io.InputStream;
import java.io.InputStreamReader;

public class js {
    public static String generateSign(Activity application, String content){
        try {
            InputStream inputStream = application.getApplicationContext().getResources().getAssets().open("sign.js");
            InputStreamReader reader = new InputStreamReader(inputStream);
            org.mozilla.javascript.Context ctx = org.mozilla.javascript.Context.enter();
            //添加这一行的目的是禁止优化，因为优化后可能报错
            ctx.setOptimizationLevel(-1);
            Scriptable scope = ctx.initStandardObjects();
//            scope.put("param1", scope, "value1");
//            String jsStr = "var testFunc = function(param){ return 'testFunc...'+'param='+param }; testFunc(param1);";
            Object result = ctx.evaluateReader(scope, reader, null, 0, null);
            Object fObj = scope.get("e", scope);
            Function f = (Function)fObj;
//最后一个参数是传给函数的参数
            String ret = (String) f.call(ctx, scope, scope, new String[]{content});
            reader.close();
            Log.d("","ret:"+ret);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
