package com.nettv.livestore.models;

import androidx.core.app.NotificationCompat;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class SubTitleUserModel implements Serializable {

    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    public int status;

    @SerializedName("token")
    public String token;

    @SerializedName("user")
    public UserModel userModel;

    public static class UserModel implements Serializable {

        @SerializedName("allowed_downloads")
        public int allowed_downloads;

        @SerializedName("allowed_translations")
        public int allowed_translations;

        @SerializedName("ext_installed")
        public boolean ext_installed;

        @SerializedName("level")
        public String level;

        @SerializedName("remaining_downloads")
        public int remaining_downloads;

        @SerializedName("vip")
        public boolean vip;

        public int getAllowed_downloads() {
            return this.allowed_downloads;
        }

        public int getAllowed_translations() {
            return this.allowed_translations;
        }

        public String getLevel() {
            return this.level;
        }

        public int getRemaining_downloads() {
            return this.remaining_downloads;
        }

        public boolean isExt_installed() {
            return this.ext_installed;
        }

        public boolean isVip() {
            return this.vip;
        }
    }

    public int getStatus() {
        return this.status;
    }

    public String getToken() {
        return this.token;
    }

    public UserModel getUserModel() {
        return this.userModel;
    }
}
