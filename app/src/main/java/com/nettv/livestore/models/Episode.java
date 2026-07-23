package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class Episode implements Serializable {
    private String container_extension;
    private String episode_num;
    private String id;
    private Object info;
    private int season;
    private String title;

    public String getContainer_extension() {
        return this.container_extension;
    }

    public String getEpisode_num() {
        return this.episode_num;
    }

    public String getId() {
        return this.id;
    }

    public Object getInfo() {
        return this.info;
    }

    public int getSeason() {
        return this.season;
    }

    public String getTitle() {
        return this.title;
    }

    public void setContainer_extension(String str) {
        this.container_extension = str;
    }

    public void setEpisode_num(String str) {
        this.episode_num = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setInfo(Object obj) {
        this.info = obj;
    }

    public void setSeason(int i) {
        this.season = i;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Episode{id='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.id, '\'', ", episode_num='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.episode_num, '\'', ", title='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.title, '\'', ", container_extension='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.container_extension, '\'', ", season=");
        sbM.append(this.season);
        sbM.append(", info=");
        sbM.append(this.info);
        sbM.append('}');
        return sbM.toString();
    }
}
