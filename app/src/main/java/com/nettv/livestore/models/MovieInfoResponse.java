package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class MovieInfoResponse implements Serializable {
    public MovieInfo info;

    public MovieInfo getInfo() {
        return this.info;
    }

    public void setInfo(MovieInfo movieInfo) {
        this.info = movieInfo;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("MovieInfoResponse{info=");
        sbM.append(this.info);
        sbM.append('}');
        return sbM.toString();
    }
}
