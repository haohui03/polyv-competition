package com.scut.plvlee2;

import static com.easefun.polyv.livecommon.module.modules.note.Utils.UtilRecognizer.accurateBasic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.scut.plvlee2.Bean.Result;
import com.scut.plvlee2.util.translation.Translate;

import java.time.Instant;
import java.util.Date;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.scut.plvlee2", appContext.getPackageName());
    }
    @Test
    public void treTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Bitmap bitmap =  BitmapFactory.decodeResource(appContext.getResources(),R.drawable.translate);
        String res =  accurateBasic(bitmap);
        System.out.println(res);
    }
    @Test
    public void translateTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Result result =  Translate.translate(appContext,"kid","en","zh");
        Log.e("lgt", "translateTest: "+result.toString() );
    }

    @Test
    public void TimeTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Log.e("lgt", Date.from(Instant.now()).toString());
    }
}