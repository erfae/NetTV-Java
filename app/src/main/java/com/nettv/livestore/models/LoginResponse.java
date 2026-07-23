package com.nettv.livestore.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes2.dex */
public class LoginResponse {
    private ServerModel server_info;
    private LoginModel user_info;

    public ServerModel getServerModel() {
        return this.server_info;
    }

    public LoginModel getUser_info() {
        return this.user_info;
    }

    public void setServerModel(ServerModel serverModel) {
        this.server_info = serverModel;
    }

    public void setUser_info(LoginModel loginModel) {
        this.user_info = loginModel;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("LoginResponse{user_info=");
        sbM.append(this.user_info);
        sbM.append('\'');
        sbM.append("server_info=");
        sbM.append(this.server_info);
        sbM.append('}');
        return sbM.toString();
    }
}
