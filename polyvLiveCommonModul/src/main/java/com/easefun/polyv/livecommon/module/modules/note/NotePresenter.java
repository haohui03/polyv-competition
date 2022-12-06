package com.easefun.polyv.livecommon.module.modules.note;

import android.util.Log;

import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.note.data.NoteData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotePresenter implements INoteContact.INotePresenter {
    static final String TAG = "lee";
    INoteDataSource noteDataSource;
    IPLVLiveRoomDataManager liveRoomDataManager;
    String userId;
    String nickName;
    //view弱引用列表
    List<WeakReference<INoteContact.INoteView>> iNoteViews = new ArrayList<>();


    @Override
    public void initLiveRoom(IPLVLiveRoomDataManager liveRoomDataManager) {
        this.liveRoomDataManager = liveRoomDataManager;
        initUser(liveRoomDataManager.getConfig().getUser().getViewerId()) ;
        Log.i(TAG, "initLiveRoom: init liveRoom  usrId :"+userId);
    }

    @Override
    public void initUser(String userId) {
        this.userId = userId;
        noteDataSource = new  LocalNoteDataBase();
        noteDataSource.SetPresenter(this);
    }

    @Override
    public void registerView(INoteContact.INoteView iNoteView) {
        iNoteViews.add(new WeakReference<>(iNoteView));
    }

    @Override
    public void requestNote(String userId, String classId) {
        noteDataSource.requestNoteList(userId);
    }

    @Override
    public void updateNote(String userId, String classId, NoteData newNote) {

    }

    @Override
    public void SetNote(String userId, String classId, NoteData newNote) {
        noteDataSource.setNote(newNote);
    }
    //请求获取笔记列表返回
    private void requestNoteListComplete(List<NoteData> noteData){
        for (WeakReference<INoteContact.INoteView>  view:
                iNoteViews) { view.get().onRequestNoteComplete(noteData);
        }
    }



    public interface INoteDataSource{
        void SetPresenter(NotePresenter presenter);
        String setNote(NoteData noteData);
        boolean requestNoteList(String userId);
    }



    //展示用的本地数据库
    private static class LocalNoteDataBase implements INoteDataSource{
        private NotePresenter NotePresenter;
        private Map<String ,NoteData> NoteDataMap = new HashMap<>();
        public String  setNote(NoteData noteData){
            String key = noteData.getTime()+noteData.getUserId();
            if (NoteDataMap.containsKey(key)){
                return "";
            }
            NoteDataMap.put(key,noteData);
            return key;
        }

        @Override
        public boolean requestNoteList(String userId) {
            //网络请求。。。。 异步操作
            NoteData demoData  = new NoteData("001","2022:12:5","English","芝士英语");
            List<NoteData> noteData = new ArrayList<>();
            noteData.add(demoData);
            {
                NotePresenter.requestNoteListComplete(noteData);
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
}
