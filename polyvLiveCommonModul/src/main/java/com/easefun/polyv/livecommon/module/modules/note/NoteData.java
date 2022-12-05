package com.easefun.polyv.livecommon.module.modules.note;

public class NoteData {
    private String userId;
    private String content;
    private String time;
    private String note;

    public NoteData(String userId,  String time,String content, String note) {
        this.userId = userId;
        this.content = content;
        this.time = time;
        this.note = note;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public NoteData() {
    }
}
