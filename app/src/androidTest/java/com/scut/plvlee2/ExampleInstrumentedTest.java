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
import com.easefun.polyv.livecommon.module.modules.note.data.Result;
import com.easefun.polyv.livecommon.module.modules.note.LocalNoteDataBaseSingleton;
import com.easefun.polyv.livecommon.module.modules.note.data.NoteData;
import com.easefun.polyv.livecommon.module.modules.note.translation.Translate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public void translateTest() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Result result = Translate.translate(appContext, "help", "en", "zh");

        Log.i("yhh","src\n"+ result.getSrc());
        Log.i("yhh","dst\n"+ result.getDst());
        Log.i("yhh","音标\n"+ result.getEnglishPhonetic());
        Log.i("yhh","拼音\n"+ result.getPhonetic().toString());
        Log.i("yhh","collins词典"+ result.getCollins().get(0).getTran());
        Log.i("yhh","英文解释"+ result.getEnglishMeaning().get(0).getGroups().get(0).getExample());

    }

    @Test
    public void TimeTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Log.e("lgt", Date.from(Instant.now()).toString());
    }
    @Test
    public void DataBaseTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        LocalNoteDataBaseSingleton.LocalNoteDataBase dataBase =  LocalNoteDataBaseSingleton.getInstance();
        Result result = Translate.translate(appContext, "help", "en", "zh");
        dataBase.initContext(appContext);
        NoteData noteData = new NoteData("tip","hello");
        List<Result> results= new ArrayList<>();
        results.add(result);
        noteData.setTranslateResults(results);
        dataBase.setNote(noteData);
        dataBase.SaveData();
        Log.e("lgt", Date.from(Instant.now()).toString());
    }
}