package com.scut.plvlee2.Bean;

import java.util.ArrayList;

public class Single {
    private String meaning;
    private ArrayList<String> example = new ArrayList<>();
    private ArrayList<String> similarWord = new ArrayList<>();


    public String getMeaning() {
        return meaning;
    }

    public ArrayList<String> getExample() {
        return example;
    }

    public ArrayList<String> getSimilarWord() {
        return similarWord;
    }


    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
