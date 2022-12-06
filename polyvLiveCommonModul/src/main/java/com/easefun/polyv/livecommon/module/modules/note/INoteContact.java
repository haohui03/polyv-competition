package com.easefun.polyv.livecommon.module.modules.note;

import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.note.data.NoteData;

import java.util.List;

/**
 * 笔记模块要实现的功能
 */
public interface INoteContact {


    interface INoteView{
        void onPresenterInitComplete();
        void onRequestNoteComplete(List<NoteData>noteData);
        void onHistoryNote(List<NoteData>noteData);
    }
    /**
     * 表示层接口
     */
    interface INotePresenter{
        void initLiveRoom(IPLVLiveRoomDataManager liveRoomDataManager);
        void initUser(String userId);
        void registerView(INoteView iNoteView);
        void requestNote(String userId,String classId);
        void requestNoteInChannel();
        void removeNote(String classId,String userId);
        void updateNote(String userId,String classId,NoteData newNote);
        void SetNote(String userId,String classId,NoteData  newNote);
        void SetNote(NoteData  newNote);
    }

}
