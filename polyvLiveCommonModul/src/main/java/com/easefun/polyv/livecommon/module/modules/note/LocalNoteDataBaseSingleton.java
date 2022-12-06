package com.easefun.polyv.livecommon.module.modules.note;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.easefun.polyv.livecommon.module.modules.note.data.NoteData;
import com.easefun.polyv.livecommon.module.modules.note.NotePresenter.INoteDataSource;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LocalNoteDataBaseSingleton {
     private static class LocalNoteDataBase implements INoteDataSource {
         private NotePresenter NotePresenter;
         private Map<String ,NoteData> NoteDataMap = new HashMap<>();
         private List<NoteData> noteDataList = new ArrayList<>();
         private Set<Long> KeySet;
         public String  setNote(NoteData noteData){
             Long  id = noteData.getNoteId();
             if(KeySet.add(id)){
                 noteDataList.add(noteData);
                 return "insert success";
             }
            return "insert fail duplicate key";

         }
         @RequiresApi(api = Build.VERSION_CODES.O)
         @Override
         public boolean requestNoteList(String userId) {
             //网络请求。。。。 异步操作
             NoteData demoData  = new NoteData("001", Date.from(Instant.now()),"English","芝士英语");
             List<NoteData> noteData = new ArrayList<>();
             noteData.add(demoData);
             {
                 NotePresenter.requestNoteListComplete(noteData);
             }
             return false;
         }

         @Override
         public boolean deleteNote(long noteId) {
             int index =-1;
             for (int i = 0; i < noteDataList.size(); i++) {
                 if(noteId==noteDataList.get(i).getNoteId()){
                     index =i;
                     break;
                 }
             }
             if(index!=-1){
                 noteDataList.remove(index);
                 return true;
             }

             return false;
         }

         public boolean removeNote(String key){
             if (NoteDataMap.remove(key)!=null){
                 return true;
             }
             return false;
         }
         @Override
         public void SetPresenter(NotePresenter presenter) {
             this.NotePresenter = presenter;
         }

     }

     static   private LocalNoteDataBase localNoteDataBase = new LocalNoteDataBase();

     public static LocalNoteDataBase getInstance(){
         return localNoteDataBase;
     }
}
