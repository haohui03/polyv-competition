package com.easefun.polyv.livecommon.module.modules.note.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteData {
    public static final  int SHARE_NOTE = 1;
    public static final  int CLASS_NOTE = 2;
    public static final  int FREE_NOTE = 3;
    public static final  int TYPE1 = 4;
    public static final  int TYPE2 = 5;
    //写笔记的用户
    private String userId;
    //哪门课的笔记
    private String ClassId;

    private long NoteId;

    public long getNoteId() {
        return NoteId;
    }

    public void setNoteId(long noteId) {
        NoteId = noteId;
    }

    //笔记类型
    private int NoteType;
    //笔记的内容（记录了什么），如英文例句
    private String content;
    //自己的笔记评论
    private String note;
    //自己的笔记评论
    private Date time;
    //翻译的结果
    public List<TranslateResult > translateResults=new ArrayList<>();


    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public int getNoteType() {
        return NoteType;
    }

    public void setNoteType(int noteType) {
        NoteType = noteType;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<TranslateResult> getTranslateResults() {
        return translateResults;
    }

    public void setTranslateResults(List<TranslateResult> translateResults) {
        this.translateResults = translateResults;
    }

    public List<String> getImageURl() {
        return ImageURl;
    }

    public void setImageURl(List<String> imageURl) {
        ImageURl = imageURl;
    }



    public List<String> ImageURl = new ArrayList<>();

    public NoteData(String userId,  Date time,String content, String note) {
        this(content,note);
        this.userId = userId;
        this.time = time;
    }
    public NoteData(String userId, String classId, Date time,String content, String note) {
        this(userId,time,content,note);
        this.ClassId = classId;
    }
    public NoteData(String content, String note) {
        this.content = content;
        this.note = note;
        this.NoteId = System.currentTimeMillis();
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


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
