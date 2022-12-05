package com.easefun.polyv.livecommon.module.modules.note;

import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;

import java.util.List;

/**
 * 笔记模块要实现的共能
 */
public interface INoteContact {


    interface INoteView{
        void onPresenterInitComplete();
        void onRequestNoteComplete(List< NoteData>noteData);
    }
    /**
     * 表示层接口
     */
    interface INotePresenter{
        void initLiveRoom(IPLVLiveRoomDataManager liveRoomDataManager);
        void initUser(String userId);
        void registerView(INoteView iNoteView);
        void requestNote(String userId,String classId);
        void updateNote(String userId,String classId,NoteData newNote);
        void SetNote(String userId,String classId,NoteData  newNote);
    }

}
