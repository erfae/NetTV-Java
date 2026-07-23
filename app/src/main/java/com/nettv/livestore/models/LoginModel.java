package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.nettv.livestore.MainActivity$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class LoginModel implements Serializable {
    private String active_cons;
    private String auth;
    private Long created_at;
    private Long exp_date = 0L;
    private String max_connections;
    private String message;
    private String password;
    private String status;
    private String username;

    public String getActive_cons() {
        return this.active_cons;
    }

    public String getAuth() {
        return this.auth;
    }

    public Long getCreated_at() {
        return this.created_at;
    }

    public Long getExp_date() {
        return this.exp_date;
    }

    public String getMax_connections() {
        return this.max_connections;
    }

    public String getMessage() {
        return this.message;
    }

    public String getPassword() {
        return this.password;
    }

    public String getStatus() {
        return this.status;
    }

    public String getUsername() {
        return this.username;
    }

    public void setActive_cons(String str) {
        this.active_cons = str;
    }

    public void setAuth(String str) {
        this.auth = str;
    }

    public void setCreated_at(Long l) {
        this.created_at = l;
    }

    public void setExp_date(Long l) {
        this.exp_date = l;
    }

    public void setMax_connections(String str) {
        this.max_connections = str;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("LoginModel{username='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.username, '\'', ", password='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.password, '\'', ", status='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.status, '\'', ", auth='");
        MainActivity$$ExternalSyntheticOutline0.m(sbM, this.auth, '\'', ", exp_date='");
        sbM.append(this.exp_date);
        sbM.append('\'');
        sbM.append(", created_at='");
        sbM.append(this.created_at);
        sbM.append('\'');
        sbM.append('}');
        return sbM.toString();
    }
}
