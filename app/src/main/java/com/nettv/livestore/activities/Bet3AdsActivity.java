package com.nettv.livestore.activities;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/* JADX INFO: loaded from: classes2.dex */
public class Bet3AdsActivity extends WebViewClient {
    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        webView.loadUrl(str);
        return false;
    }
}
