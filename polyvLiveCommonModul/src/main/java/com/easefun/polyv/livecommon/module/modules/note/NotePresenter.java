package com.easefun.polyv.livecommon.module.modules.note;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVCustomGiftBean;
import com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract;
import com.easefun.polyv.livecommon.module.modules.chatroom.customData;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter;
import com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView;
import com.easefun.polyv.livecommon.module.modules.note.data.NoteData;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livescenes.chatroom.send.custom.PolyvCustomEvent;
import com.google.gson.internal.LinkedTreeMap;
import com.plv.socket.event.PLVBaseEvent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class NotePresenter implements INoteContact.INotePresenter {
    static final String TAG = "lee";
    INoteDataSource noteDataSource;
    Context context;
    IPLVLiveRoomDataManager liveRoomDataManager;
    IPLVChatroomContract.IChatroomPresenter chatroomPresenter;
    String userId;
    String nickName;
    String classID;
    List<NoteData> currentChannelNote;
    List<NoteData> currentUserNote;
    HashSet<String> cacheNoteKey = new HashSet<>();
    //view弱引用列表
    List<WeakReference<INoteContact.INoteView>> iNoteViews = new ArrayList<>();

    public NotePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initLiveRoom(IPLVLiveRoomDataManager liveRoomDataManager) {
        chatroomPresenter = new PLVChatroomPresenter(liveRoomDataManager);
        chatroomPresenter.init();

        chatroomPresenter.registerView(new PLVAbsChatroomView() {
            @Override
            public void onCustomEvent(@NonNull PolyvCustomEvent.UserBean userBean, @NonNull customData customBean) {
                String data = customBean.getDataname();
                String key = ( customBean.getCustomObj().getTime().toString());
                //todo 应该等几秒钟才返回一串数据
                if(cacheNoteKey.add(key)){
                    if(customBean.getType().equals(customData.TYPE_NOTE)){
                        NoteData noteData = (NoteData)(customBean.getCustomObj());
                        if(userId.equals(noteData.getUserId())){
                            currentUserNote.add(noteData);
                        }

                        for (WeakReference<INoteContact.INoteView> view :
                                iNoteViews) {
                            INoteContact.INoteView  view1=  view.get();
                            if(view1!=null){
                                view1.onNewNoteAccept(noteData);
                            }
                        }
                    }

                }
                Log.i(TAG, "onCustomEvent: "+data);
            }
        });
        this.liveRoomDataManager = liveRoomDataManager;
        this.classID = liveRoomDataManager.getConfig().getChannelId().toString();
        initUser(liveRoomDataManager.getConfig().getUser().getViewerId()) ;
        Log.i(TAG, "initLiveRoom: init liveRoom  usrId :"+userId);
    }

    @Override
    public void initUser(String userId) {
        this.userId = userId;
        noteDataSource = LocalNoteDataBaseSingleton.getInstance();
        noteDataSource.SetPresenter(this);
        noteDataSource.initContext(context);
    }

    @Override
    public void registerView(INoteContact.INoteView iNoteView) {
        iNoteViews.add(new WeakReference<>(iNoteView));
    }

    private NoteData LinkedTreeMap2NoteData(LinkedTreeMap<String ,Object> map){
        return null;
    }
    @Override
    public void requestNote(String userId, String classId) {
        if(this.userId.equals("admin")){
            noteDataSource.requestAll();
        }
        else{
            noteDataSource.requestNoteList(userId);
        }

    }

    @Override
    public void requestNoteInChannel() {
        noteDataSource.requestNoteList(userId);
    }

    @Override
    public void removeNote(String classId, String userId) {
        noteDataSource.deleteNote(classId,userId);
    }

    @Override
    public void removeClassNote() {
        removeNote(classID=liveRoomDataManager.getConfig().getChannelId(),liveRoomDataManager.getConfig().getUser().getViewerId());
    }

    @Override
    public void updateNote(String userId, String classId, NoteData newNote) {

    }

    /** @deprecated */
    @Override
    public void SetNote(String userId, String classId, NoteData newNote) {
        noteDataSource.setNote(newNote);
        //判断是否广播笔记
        if(true)
        {
            chatroomPresenter.sendCustomMessage(new customData(newNote,customData.TYPE_NOTE),"");
        }
        chatroomPresenter.requestChatHistory(0);
    }

    @Override
    public void SetNote(NoteData newNote) {

        noteDataSource.setNote(newNote);
        chatroomPresenter.sendCustomMessage(new customData(newNote,customData.TYPE_NOTE),"");
    }

    @Override
    public void destroy() {
        noteDataSource.SaveData();
    }

    //请求获取笔记列表返回
    public void requestNoteListComplete(List<NoteData> noteData){
        for (WeakReference<INoteContact.INoteView>  view:
                iNoteViews) { view.get().onRequestNoteComplete(noteData);
        }
    }

    public interface INoteDataSource{
        void SetPresenter(NotePresenter presenter);
        String setNote(NoteData noteData);
        boolean requestNoteList(String userId);
        boolean deleteNote(long noteId);
        boolean deleteNote(String classId, String userId);
        boolean initContext(Context context);
        boolean SaveData();
        boolean requestAll();
    }


}
