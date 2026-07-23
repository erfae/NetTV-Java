package com.nettv.livestore.activities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import com.nettv.livestore.R;
import com.nettv.livestore.apps.Constants;

/* JADX INFO: loaded from: classes2.dex */
public class SportActivity extends Activity {
    public WebView a;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.spt_act);
        WebView webView = (WebView) findViewById(R.id.betView);
        webView.addJavascriptInterface(this, "Android");
        this.a = webView;
        webView.loadUrl(Constants.second_response_url.replaceAll("auth.php", "sport.php"));
        webView.setBackgroundColor(0);
        webView.getSettings().setJavaScriptEnabled(true);
    }
}
