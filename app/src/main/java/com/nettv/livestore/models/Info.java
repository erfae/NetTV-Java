package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class Info implements Serializable {
    private String movie_image;
    private String plot;
    private String rating;
    private String releasedate;
    private String tmdb_id;

    public String getMovie_image() {
        return this.movie_image;
    }

    public String getPlot() {
        String str = this.plot;
        return str == null ? "" : str;
    }

    public String getRating() {
        return this.rating;
    }

    public String getReleasedate() {
        return this.releasedate;
    }

    public String getTmdb_id() {
        String str = this.tmdb_id;
        return str == null ? "" : str;
    }

    public void setMovie_image(String str) {
        this.movie_image = str;
    }

    public void setPlot(String str) {
        this.plot = str;
    }

    public void setRating(String str) {
        this.rating = str;
    }

    public void setReleasedate(String str) {
        this.releasedate = str;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Info{movie_image='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.movie_image, '\'', ", plot='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.plot, '\'', ", releasedate='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.releasedate, '\'', ", rating='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.rating, '\'', ", tmdb_id='");
        sbM.append(this.tmdb_id);
        sbM.append('\'');
        sbM.append('}');
        return sbM.toString();
    }
}
