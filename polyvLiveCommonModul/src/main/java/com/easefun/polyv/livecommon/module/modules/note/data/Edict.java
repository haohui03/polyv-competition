package com.easefun.polyv.livecommon.module.modules.note.data;

import java.util.ArrayList;

public class Edict {
    private String pos;
    private ArrayList<Single> groups =new ArrayList<>();

    public String getPos() {
        return pos;
    }

    @Override
    public String toString() {
        return "Edict{" +
                "pos='" + pos + '\'' +
                ", groups=" + groups +
                '}';
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
