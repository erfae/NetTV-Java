package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class TMDBResponse implements Serializable {
    public String backdrop_path;
    public int id;
    public String imdb_id;
    public String original_language;
    public String overview;
    public String poster_path;
    public String release_date;
    public int runtime;
    public float vote_average;

    public String getBackdrop_path() {
        return this.backdrop_path;
    }

    public int getId() {
        return this.id;
    }

    public String getImdb_id() {
        return this.imdb_id;
    }

    public String getOriginal_language() {
        return this.original_language;
    }

    public String getOverview() {
        return this.overview;
    }

    public String getPoster_path() {
        return this.poster_path;
    }

    public String getRelease_date() {
        return this.release_date;
    }

    public int getRuntime() {
        return this.runtime;
    }

    public float getVote_average() {
        return this.vote_average;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("TMDBResponse{backdrop_path='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.backdrop_path, '\'', ", id='");
        sbM.append(this.id);
        sbM.append('\'');
        sbM.append(", imdb_id='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.imdb_id, '\'', ", original_language='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.original_language, '\'', ", overview='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.overview, '\'', ", poster_path='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.poster_path, '\'', ", release_date='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.release_date, '\'', ", runtime=");
        sbM.append(this.runtime);
        sbM.append(", vote_average=");
        sbM.append(this.vote_average);
        sbM.append('}');
        return sbM.toString();
    }
}
