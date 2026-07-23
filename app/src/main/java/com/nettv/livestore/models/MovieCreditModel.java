package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class MovieCreditModel implements Serializable {
    public String backdrop_path;
    public String character;
    public String credit_id;
    public int id;
    public String original_language;
    public String original_title;
    public String overview;
    public String poster_path;
    public String release_date;
    public String title;
    public float vote_average;
    public int vote_count;

    public String getBackdrop_path() {
        return this.backdrop_path;
    }

    public String getCharacter() {
        return this.character;
    }

    public String getCredit_id() {
        return this.credit_id;
    }

    public int getId() {
        return this.id;
    }

    public String getOriginal_language() {
        return this.original_language;
    }

    public String getOriginal_title() {
        return this.original_title;
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

    public String getTitle() {
        return this.title;
    }

    public float getVote_average() {
        return this.vote_average;
    }

    public int getVote_count() {
        return this.vote_count;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("MovieCreditModels{id='");
        sbM.append(this.id);
        sbM.append('\'');
        sbM.append("character='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.character, '\'', ", credit_id='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.credit_id, '\'', ", release_date='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.release_date, '\'', ", vote_count=");
        sbM.append(this.vote_count);
        sbM.append(", vote_average=");
        sbM.append(this.vote_average);
        sbM.append(", title='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.title, '\'', ", original_language='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.original_language, '\'', ", original_title='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.original_title, '\'', ", backdrop_path='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.backdrop_path, '\'', ", overview='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.overview, '\'', ", poster_path='");
        sbM.append(this.poster_path);
        sbM.append('\'');
        sbM.append('}');
        return sbM.toString();
    }
}
