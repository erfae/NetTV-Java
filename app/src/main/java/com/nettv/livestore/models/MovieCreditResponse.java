package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class MovieCreditResponse {
    public List<MovieCreditModel> cast;

    public List<MovieCreditModel> getCast() {
        return this.cast;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("MovieCreditResponse{cast=");
        sbM.append(this.cast);
        sbM.append('}');
        return sbM.toString();
    }
}
