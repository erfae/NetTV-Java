package com.nettv.livestore.models;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class AppInfoModel implements Serializable {

    @SerializedName("apk_link")
    private String apk_link;

    @SerializedName("app_version")
    private String app_version;

    @SerializedName("device_key")
    private String device_key;

    @SerializedName("expire_date")
    private String expiredDate;
    private boolean is_google_pay;

    @SerializedName("is_trial")
    private int is_trial;

    @SerializedName("languages")
    private List<LanguageModel> languageModels;

    @SerializedName("lock")
    private int lock;

    @SerializedName("mac_address")
    private String mac_address;

    @SerializedName("note_content")
    private String note_content;

    @SerializedName("note_title")
    private String note_title;

    @SerializedName("parent_control")
    private String parent_control;

    @SerializedName("parent_synced")
    private int parent_synced;

    @SerializedName("pin")
    private String pin_code;

    @SerializedName("plan_id")
    private String plan_id;

    @SerializedName("price")
    private String price;

    @SerializedName("urls")
    private List<UrlModel> result;

    @SerializedName("mac_registered")
    private boolean success;

    public static class UrlModel implements Serializable {

        @SerializedName(TtmlNode.ATTR_ID)
        private String id;

        @SerializedName("is_protected")
        private String is_protected;

        @SerializedName("name")
        private String name;

        @SerializedName("type")
        private String type;

        @SerializedName(ImagesContract.URL)
        private String url;

        public String getId() {
            return this.id;
        }

        public String getIs_protected() {
            String str = this.is_protected;
            return str == null ? "0" : str;
        }

        public String getName() {
            return this.name;
        }

        public String getType() {
            return this.type;
        }

        public String getUrl() {
            String str = this.url;
            return str == null ? "" : str;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setName(String str) {
            this.name = str;
        }

        public void setType(String str) {
            this.type = str;
        }

        public void setUrl(String str) {
            this.url = str;
        }
    }

    public String getApk_link() {
        return this.apk_link;
    }

    public String getApp_version() {
        String str = this.app_version;
        return str == null ? "1.0" : str;
    }

    public String getDevice_key() {
        return this.device_key;
    }

    public String getExpiredDate() {
        String str = this.expiredDate;
        return str == null ? "" : str;
    }

    public int getIs_trial() {
        return this.is_trial;
    }

    public List<LanguageModel> getLanguageModels() {
        List<LanguageModel> list = this.languageModels;
        return list == null ? new ArrayList() : list;
    }

    public int getLock() {
        return this.lock;
    }

    public String getMac_address() {
        return this.mac_address;
    }

    public String getNoteContent() {
        String str = this.note_content;
        return str == null ? "To IBO Player PRO" : str;
    }

    public String getNoteTitle() {
        String str = this.note_title;
        return str == null ? "Welcome" : str;
    }

    public String getParent_control() {
        String str = this.parent_control;
        return (str == null || str.isEmpty()) ? "0000" : this.parent_control;
    }

    public int getParent_synced() {
        return this.parent_synced;
    }

    public String getPin_code() {
        String str = this.pin_code;
        return str == null ? "0000" : str;
    }

    public String getPlan_id() {
        return this.plan_id;
    }

    public String getPrice() {
        String str = this.price;
        return (str == null || str.isEmpty()) ? "7.9" : this.price;
    }

    public List<UrlModel> getResult() {
        List<UrlModel> list = this.result;
        return list == null ? new ArrayList() : list;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public boolean isIs_google_pay() {
        return this.is_google_pay;
    }

    public void setExpiredDate(String str) {
        this.expiredDate = str;
    }

    public void setIs_google_pay(boolean z) {
        this.is_google_pay = z;
    }

    public void setIs_trial(int i) {
        this.is_trial = i;
    }

    public void setLanguageModels(List<LanguageModel> list) {
        this.languageModels = list;
    }

    public void setLock(int i) {
        this.lock = i;
    }

    public void setPin_code(String str) {
        this.pin_code = str;
    }

    public void setResult(List<UrlModel> list) {
        this.result = list;
    }
}
