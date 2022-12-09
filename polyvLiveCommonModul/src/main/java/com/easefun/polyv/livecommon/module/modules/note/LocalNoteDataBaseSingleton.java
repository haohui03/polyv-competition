package com.easefun.polyv.livecommon.module.modules.note;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.easefun.polyv.livecommon.module.modules.note.data.NoteData;
import com.easefun.polyv.livecommon.module.modules.note.NotePresenter.INoteDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LocalNoteDataBaseSingleton {
     public static class LocalNoteDataBase implements INoteDataSource, Serializable {
         public boolean  initContext(Context context){
             File fileDir = context.getDir("tempData", Context.MODE_PRIVATE);
             KeySetDatabase = new File(fileDir, "note_keySet.database");
             noteDataListDatabase = new File(fileDir, "note_noteDataList.database");
             ObjectInputStream obis;
             try {
                 if(KeySetDatabase.exists()){
                     obis = new ObjectInputStream(new FileInputStream(KeySetDatabase));
                     KeySet = (Set<Long>) obis.readObject();
                     obis.close();
                 }
                 if(noteDataListDatabase.exists()){
                     obis = new ObjectInputStream(new FileInputStream(noteDataListDatabase));
                     noteDataList = (List<NoteData>) obis.readObject();
                     obis.close();
                 }
             }

             catch (IOException | ClassNotFoundException e) {
                 e.printStackTrace();
                 return false;
             }
             return true;
         }

         public boolean SaveData(){
             ObjectOutputStream obos;
             try {
                 if(KeySetDatabase!=null){
                     obos = new ObjectOutputStream(new FileOutputStream(KeySetDatabase));

                         obos.writeObject(KeySet);

                     obos.close();
                 }
                 if(noteDataListDatabase!=null){
                     obos = new ObjectOutputStream(new FileOutputStream(noteDataListDatabase));
                     obos.writeObject(noteDataList);
                     obos.close();
                 }
             }
             catch (IOException e) {
                 e.printStackTrace();
                 return false;
             }
             return true;
         }

         @Override
         public boolean requestAll() {
            //理应是网络上的数据库，所以是异步操作，创建新的线程;
             NotePresenter.requestNoteListComplete(noteDataList);
             return true;
         }

         @Override
         protected void finalize() throws Throwable {
             super.finalize();
             ObjectOutputStream obos;
             if(KeySetDatabase!=null){
                 obos = new ObjectOutputStream(new FileOutputStream(KeySetDatabase));
                 obos.writeObject(KeySet);
                 obos.close();
             }
             if(noteDataListDatabase!=null){
                 obos = new ObjectOutputStream(new FileOutputStream(noteDataListDatabase));
                 obos.writeObject(noteDataList);
                 obos.close();
             }
         }
         private File noteDataListDatabase;
         private File KeySetDatabase;
         private NotePresenter NotePresenter;
         private List<NoteData> noteDataList = new ArrayList<>();
         private Set<Long> KeySet = new HashSet<>();
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
             List<NoteData> noteData = new ArrayList<>();
             for (NoteData n:
                  noteDataList) {
                 if(n.getUserId().equals(userId)){
                     noteData.add(n);
                 }
             }

             NotePresenter.requestNoteListComplete(noteData);

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

         @Override
         public boolean deleteNote(String classId, String userId) {
             for (int i = 0; i < noteDataList.size(); i++) {
                 if(userId.equals(noteDataList.get(i).getUserId())
                         &&classId.equals(noteDataList.get(i).getClassId())){
                     noteDataList.remove(i);
                     return true;
                 }
             }
             return false;
         }

         @Override
         public void SetPresenter(NotePresenter presenter) {
             this.NotePresenter = presenter;
         }

     }

     static   private final LocalNoteDataBase localNoteDataBase = new LocalNoteDataBase();

     public static LocalNoteDataBase getInstance(){
         return localNoteDataBase;
     }
}
