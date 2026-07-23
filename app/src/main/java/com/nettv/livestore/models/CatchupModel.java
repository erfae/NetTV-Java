package com.nettv.livestore.models;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class CatchupModel implements Serializable {
    private String dayofweek;
    private List<CatchUpEpg> epgEvents;
    private String name;

    public String getDayofweek() {
        return this.dayofweek;
    }

    public List<CatchUpEpg> getEpgEvents() {
        return this.epgEvents;
    }

    public String getName() {
        return this.name;
    }

    public void setDayofweek(String str) {
        this.dayofweek = str;
    }

    public void setEpgEvents(List<CatchUpEpg> list) {
        this.epgEvents = list;
    }

    public void setName(String str) {
        this.name = str;
    }
}
