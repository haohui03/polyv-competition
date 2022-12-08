package com.easefun.polyv.livecommon.module.modules.note.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Result implements Serializable {
    private String src;
    private String dst;

    public ArrayList<Edict> getEnglishMeaning() {
        return englishMeaning;
    }

    private ArrayList<Edict> englishMeaning = new ArrayList<>();

    public String getEnglishPhonetic() {
        return englishPhonetic;
    }

    public void setEnglishPhonetic(String englishPhonetic) {
        this.englishPhonetic = englishPhonetic;
    }
    private ArrayList<CollinsSingle> Collins = new ArrayList<>();

    public ArrayList<CollinsSingle> getCollins(){
        return Collins;
    }


    private String englishPhonetic;
    private ArrayList<Map<String, String>> phonetic = new ArrayList<>();




//    private ArrayList

    public void setSrc(String src) {
        this.src = src;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getSrc() {
        return src;
    }

    public String getDst() {
        return dst;
    }

    public ArrayList<Map<String, String>> getPhonetic() {
        return phonetic;
    }


}


