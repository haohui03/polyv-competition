package com.scut.plvlee2.Bean;

import java.util.ArrayList;

public class Edict {
    private String pos;
    private ArrayList<Single> groups =new ArrayList<>();

    public String getPos() {
        return pos;
    }


    public void setPos(String pos) {
        this.pos = pos;
    }


    public ArrayList<Single> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Single> groups) {
        this.groups = groups;
    }
}
