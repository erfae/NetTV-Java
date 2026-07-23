package com.nettv.livestore.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class SubtitleLinkModel implements Serializable {

    @SerializedName("file_name")
    public String file_name;

    @SerializedName("link")
    public String link;

    @SerializedName("message")
    public String message;

    @SerializedName("remaining")
    public int remaining;

    @SerializedName("requests")
    public int requests;

    @SerializedName("reset_time")
    public String reset_time;

    @SerializedName("reset_time_utc")
    public String reset_time_utc;

    public String getFile_name() {
        return this.file_name;
    }

    public String getLink() {
        return this.link;
    }

    public String getMessage() {
        return this.message;
    }

    public int getRemaining() {
        return this.remaining;
    }

    public int getRequests() {
        return this.requests;
    }

    public String getReset_time() {
        return this.reset_time;
    }

    public String getReset_time_utc() {
        return this.reset_time_utc;
    }
}
