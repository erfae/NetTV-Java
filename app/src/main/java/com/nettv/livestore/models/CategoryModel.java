package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.gson.annotations.SerializedName;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class CategoryModel implements Serializable {

    @SerializedName("category_id")
    private String id;

    @SerializedName("category_name")
    private String name;

    public CategoryModel(String str, String str2) {
        this.id = str;
        this.name = str2;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        String str = this.name;
        if (str == null || str.isEmpty()) {
            return "Unknown category";
        }
        return this.name.contains("!@#%") ? this.name.split("!@#%")[1] : this.name;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("CategoryModel{category_id='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.id, '\'', ", category_name='");
        sbM.append(this.name);
        sbM.append('\'');
        sbM.append('}');
        return sbM.toString();
    }
}
