package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class CastResponse {
    public List<CastModel> cast;
    public int id;

    public List<CastModel> getCast() {
        return this.cast;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("CastResponse{id=");
        sbM.append(this.id);
        sbM.append(", cast=");
        sbM.append(this.cast);
        sbM.append('}');
        return sbM.toString();
    }
}
