package com.scut.plvlee2.Bean;

public class CollinsSingle {
    private String tran;
    private String ex;

    public void setTran(String tran) {
        this.tran = tran;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTran() {
        return tran;
    }

    public String getEx() {
        return ex;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getType() {
        return type;
    }

    private String meaning;
    private String type;
}
