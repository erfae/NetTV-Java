package com.nettv.livestore.models;

import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class ResumeModel implements Serializable {
    public long last_position;
    public String name;
    public int pro;
    public String tmdb_id = "";

    public long getLast_position() {
        return this.last_position;
    }

    public String getName() {
        return this.name;
    }

    public int getPro() {
        return this.pro;
    }

    public String getTmdb_id() {
        return this.tmdb_id;
    }

    public void setLast_position(long j) {
        this.last_position = j;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setPro(int i) {
        this.pro = i;
    }

    public void setTmdb_id(String str) {
        this.tmdb_id = str;
    }
}
