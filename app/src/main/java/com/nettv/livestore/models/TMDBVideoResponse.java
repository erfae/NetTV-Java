package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class TMDBVideoResponse {
    public int id;
    public List<TMDBVideoModel> results;

    public int getId() {
        return this.id;
    }

    public List<TMDBVideoModel> getResults() {
        return this.results;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("TMDBVideoResponse{id=");
        sbM.append(this.id);
        sbM.append(", results=");
        sbM.append(this.results);
        sbM.append('}');
        return sbM.toString();
    }
}
