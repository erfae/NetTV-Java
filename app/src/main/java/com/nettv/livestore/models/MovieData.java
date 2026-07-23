package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class MovieData implements Serializable {
    private String added;
    private String category_id;
    private String container_extension;
    private String custom_sid;
    private String direct_source;
    private String name;
    private String stream_id;

    public String getAdded() {
        return this.added;
    }

    public String getCategory_id() {
        return this.category_id;
    }

    public String getContainer_extension() {
        return this.container_extension;
    }

    public String getCustom_sid() {
        return this.custom_sid;
    }

    public String getDirect_source() {
        return this.direct_source;
    }

    public String getName() {
        return this.name;
    }

    public String getStream_id() {
        return this.stream_id;
    }

    public void setAdded(String str) {
        this.added = str;
    }

    public void setCategory_id(String str) {
        this.category_id = str;
    }

    public void setContainer_extension(String str) {
        this.container_extension = str;
    }

    public void setCustom_sid(String str) {
        this.custom_sid = str;
    }

    public void setDirect_source(String str) {
        this.direct_source = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setStream_id(String str) {
        this.stream_id = str;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("MovieData{stream_id='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.stream_id, '\'', ", name='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.name, '\'', ", added='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.added, '\'', ", category_id='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.category_id, '\'', ", container_extension='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.container_extension, '\'', ", custom_sid='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.custom_sid, '\'', ", direct_source='");
        sbM.append(this.direct_source);
        sbM.append('\'');
        sbM.append('}');
        return sbM.toString();
    }
}
