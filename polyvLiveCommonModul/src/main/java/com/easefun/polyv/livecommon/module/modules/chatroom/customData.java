package com.easefun.polyv.livecommon.module.modules.chatroom;

import com.easefun.polyv.livecommon.module.modules.note.data.NoteData;

public class customData {
    public static final String EVENT = "customMessage";

    public static final String TYPE_NOTE = "note";
    public static final String TYPE_QUESTION = "question";
    String type;
    String dataname;
    //todo 测试数据正确性
    NoteData customObj;
    public String getDataname() {
        return dataname;
    }

    public void setDataname(String dataname) {
        this.dataname = dataname;
    }

    public customData(String dataname) {
        this.dataname = dataname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public NoteData getCustomObj() {
        return customObj;
    }

    public void setCustomObj(NoteData customObj) {
        this.customObj = customObj;
    }

    public customData(NoteData customObj,String type) {
        this.customObj = customObj;
        this.type = type;
    }
}
