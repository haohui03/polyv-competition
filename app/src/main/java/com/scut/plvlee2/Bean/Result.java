package com.scut.plvlee2.Bean;

import java.util.ArrayList;
import java.util.Map;

public class Result {
    private String src;
    private String dst;

    public ArrayList<String> getEnglishMeaning() {
        return englishMeaning;
    }

    private ArrayList<String> englishMeaning;

    public String getEnglishPhonetic() {
        return englishPhonetic;
    }

    public void setEnglishPhonetic(String englishPhonetic) {
        this.englishPhonetic = englishPhonetic;
    }

    private String englishPhonetic;
    private ArrayList<Map<String,String>> phonetic;

    private ArrayList<Map<String,String>> similarWords;

    public ArrayList<Map<String, String>> getCollins() {
        return Collins;
    }

    private ArrayList<Map<String,String>> Collins;

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

    public ArrayList<Map<String, String>> getSimilarWords() {
        return similarWords;
    }
}

