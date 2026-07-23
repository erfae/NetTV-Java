package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class InfoSerie implements Serializable {
    public List<Season> seasons = new ArrayList();
    public ListEpisode episodes = new ListEpisode();

    public ListEpisode getEpisodes() {
        return this.episodes;
    }

    public List<Season> getSeasons() {
        return this.seasons;
    }

    public void setEpisodes(ListEpisode listEpisode) {
        this.episodes = listEpisode;
    }

    public void setSeasons(List<Season> list) {
        this.seasons = list;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("InfoSerie{seasons=");
        sbM.append(this.seasons);
        sbM.append("episodes=");
        sbM.append(this.episodes);
        sbM.append('}');
        return sbM.toString();
    }
}
