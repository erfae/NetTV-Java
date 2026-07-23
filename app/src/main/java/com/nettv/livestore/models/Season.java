package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class Season implements Serializable {
    private String air_date;
    private String category_name;
    private String cover;
    private String cover_big;
    private List<EpisodeModel> episodeModels;
    private String episode_count;
    private String id;
    private String name;
    private String overview;
    private int season_number;

    public Season() {
    }

    public String getAir_date() {
        return this.air_date;
    }

    public String getCategory_name() {
        return this.category_name;
    }

    public String getCover() {
        return this.cover;
    }

    public String getCover_big() {
        return this.cover_big;
    }

    public List<EpisodeModel> getEpisodeModels() {
        List<EpisodeModel> list = this.episodeModels;
        return list == null ? new ArrayList() : list;
    }

    public String getEpisode_count() {
        String str = this.episode_count;
        return (str == null || str.isEmpty()) ? "0" : this.episode_count;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getOverview() {
        return this.overview;
    }

    public int getSeason_number() {
        return this.season_number;
    }

    public void setAir_date(String str) {
        this.air_date = str;
    }

    public void setCategory_name(String str) {
        this.category_name = str;
    }

    public void setCover(String str) {
        this.cover = str;
    }

    public void setCover_big(String str) {
        this.cover_big = str;
    }

    public void setEpisodeModels(List<EpisodeModel> list) {
        this.episodeModels = list;
    }

    public void setEpisode_count(String str) {
        this.episode_count = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setOverview(String str) {
        this.overview = str;
    }

    public void setSeason_number(int i) {
        this.season_number = i;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Season{air_date='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.air_date, '\'', ", episode_count='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.episode_count, '\'', ", id='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.id, '\'', ", name='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.name, '\'', ", overview='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.overview, '\'', ", season_number=");
        sbM.append(this.season_number);
        sbM.append(", cover='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.cover, '\'', ", cover_big='");
        sbM.append(this.cover_big);
        sbM.append('\'');
        sbM.append('}');
        return sbM.toString();
    }

    public Season(String str, int i, String str2) {
        this.name = str;
        this.season_number = i;
        this.cover = str2;
    }
}
