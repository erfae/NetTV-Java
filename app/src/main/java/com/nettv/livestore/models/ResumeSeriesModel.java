package com.nettv.livestore.models;

import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class ResumeSeriesModel implements Serializable {
    public int episode_pos;
    public String name;
    public int season_pos;

    public int getEpisode_pos() {
        return this.episode_pos;
    }

    public String getName() {
        return this.name;
    }

    public int getSeason_pos() {
        return this.season_pos;
    }

    public void setEpisode_pos(int i) {
        this.episode_pos = i;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setSeason_pos(int i) {
        this.season_pos = i;
    }
}
