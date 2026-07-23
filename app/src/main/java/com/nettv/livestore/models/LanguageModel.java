package com.nettv.livestore.models;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class LanguageModel implements Serializable {

    @SerializedName("code")
    private String code;

    @SerializedName(TtmlNode.ATTR_ID)
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("words")
    private WordModels wordModels;

    public String getCode() {
        return this.code;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public WordModels getWordModel() {
        return this.wordModels;
    }
}
