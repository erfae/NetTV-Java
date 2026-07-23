package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class CatchUpEpgResponse implements Serializable {
    public List<CatchUpEpg> epg_listings = new ArrayList();

    public List<CatchUpEpg> getEpg_listings() {
        return this.epg_listings;
    }

    public void setEpg_listings(List<CatchUpEpg> list) {
        this.epg_listings = list;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("CatchUpEpgResponse{epg_listings=");
        sbM.append(this.epg_listings);
        sbM.append('}');
        return sbM.toString();
    }
}
