package org.androidannotations.api.rest;

import org.springframework.http.HttpAuthentication;

/* JADX INFO: loaded from: classes2.dex */
public interface RestClientHeaders {
    String getCookie(String str);

    String getHeader(String str);

    void setAuthentication(HttpAuthentication httpAuthentication);

    void setBearerAuth(String str);

    void setCookie(String str, String str2);

    void setHeader(String str, String str2);

    void setHttpBasicAuth(String str, String str2);
}
