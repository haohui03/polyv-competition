package com.easefun.polyv.livecommon.module.modules.note.translation;

import android.app.Activity;
import android.util.Log;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


public class js {
    private static String  signjs = "var r =null\n" +
            "function n(r, o) {\n" +
            "    for (var t = 0; t < o.length - 2; t += 3) {\n" +
            "        var a = o.charAt(t + 2);\n" +
            "        a = a >= \"a\" ? a.charCodeAt(0) - 87 : Number(a),\n" +
            "            a = \"+\" === o.charAt(t + 1) ? r >>> a : r << a,\n" +
            "            r = \"+\" === o.charAt(t) ? r + a & 4294967295 : r ^ a\n" +
            "    }\n" +
            "    return r\n" +
            "}\n" +
            "function e(t) {\n" +
            "    var o, i = t.match(/[\\uD800-\\uDBFF][\\uDC00-\\uDFFF]/g);\n" +
            "    if (null === i) {\n" +
            "        var a = t.length;\n" +
            "        a > 30 && (t = \"\".concat(t.substr(0, 10)).concat(t.substr(Math.floor(a / 2) - 5, 10)).concat(t.substr(-10, 10)))\n" +
            "    } else {\n" +
            "        for (var s = t.split(/[\\uD800-\\uDBFF][\\uDC00-\\uDFFF]/), c = 0, u = s.length, l = []; c < u; c++)\n" +
            "            \"\" !== s[c] && l.push.apply(l, function (t) {\n" +
            "                if (Array.isArray(t))\n" +
            "                    return e(t)\n" +
            "            }(o = s[c].split(\"\")) || function (t) {\n" +
            "                if (\"undefined\" != typeof Symbol && null != t[Symbol.iterator] || null != t[\"@@iterator\"])\n" +
            "                    return Array.from(t)\n" +
            "            }(o) || function (t, n) {\n" +
            "                if (t) {\n" +
            "                    if (\"string\" == typeof t)\n" +
            "                        return e(t, n);\n" +
            "                    var r = Object.prototype.toString.call(t).slice(8, -1);\n" +
            "                    return \"Object\" === r && t.constructor && (r = t.constructor.name),\n" +
            "                        \"Map\" === r || \"Set\" === r ? Array.from(t) : \"Arguments\" === r || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r) ? e(t, n) : void 0\n" +
            "                }\n" +
            "            }(o) || function () {\n" +
            "                throw new TypeError(\"Invalid attempt to spread non-iterable instance.\\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.\")\n" +
            "            }()),\n" +
            "            c !== u - 1 && l.push(i[c]);\n" +
            "        var p = l.length;\n" +
            "        p > 30 && (t = l.slice(0, 10).join(\"\") + l.slice(Math.floor(p / 2) - 5, Math.floor(p / 2) + 5).join(\"\") + l.slice(-10).join(\"\"))\n" +
            "    }\n" +
            "    for (var d = \"\".concat(String.fromCharCode(103)).concat(String.fromCharCode(116)).concat(String.fromCharCode(107)), h = (null !== r ? r : (r = \"320305.131321201\" || \"\") || \"\").split(\".\"), f = Number(h[0]) || 0, m = Number(h[1]) || 0, g = [], y = 0, v = 0; v < t.length; v++) {\n" +
            "        var _ = t.charCodeAt(v);\n" +
            "        _ < 128 ? g[y++] = _ : (_ < 2048 ? g[y++] = _ >> 6 | 192 : (55296 == (64512 & _) && v + 1 < t.length && 56320 == (64512 & t.charCodeAt(v + 1)) ? (_ = 65536 + ((1023 & _) << 10) + (1023 & t.charCodeAt(++v)),\n" +
            "            g[y++] = _ >> 18 | 240,\n" +
            "            g[y++] = _ >> 12 & 63 | 128) : g[y++] = _ >> 12 | 224,\n" +
            "            g[y++] = _ >> 6 & 63 | 128),\n" +
            "            g[y++] = 63 & _ | 128)\n" +
            "    }\n" +
            "    for (var b = f, w = \"\".concat(String.fromCharCode(43)).concat(String.fromCharCode(45)).concat(String.fromCharCode(97)) + \"\".concat(String.fromCharCode(94)).concat(String.fromCharCode(43)).concat(String.fromCharCode(54)), k = \"\".concat(String.fromCharCode(43)).concat(String.fromCharCode(45)).concat(String.fromCharCode(51)) + \"\".concat(String.fromCharCode(94)).concat(String.fromCharCode(43)).concat(String.fromCharCode(98)) + \"\".concat(String.fromCharCode(43)).concat(String.fromCharCode(45)).concat(String.fromCharCode(102)), x = 0; x < g.length; x++)\n" +
            "        b = n(b += g[x], w);\n" +
            "    return b = n(b, k),\n" +
            "    (b ^= m) < 0 && (b = 2147483648 + (2147483647 & b)),\n" +
            "        \"\".concat((b %= 1e6).toString(), \".\").concat(b ^ f)\n" +
            "}\n";

    public static String generateSign(Activity application,String content){
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
    public static String generateSign(String content){
        try {
            InputStream inputStream = new ByteArrayInputStream(signjs.getBytes());

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
