package com.nettv.livestore.net;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;

/* JADX INFO: loaded from: classes2.dex */
public class BaseCommand {
    public HttpClient client;
    private Integer timeOut = 60000;
    public String urlServer;

    public BaseCommand(String str, ArrayList<NameValuePair> arrayList) {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        this.client = defaultHttpClient;
        HttpConnectionParams.setConnectionTimeout(defaultHttpClient.getParams(), this.timeOut.intValue());
        HttpConnectionParams.setSoTimeout(this.client.getParams(), this.timeOut.intValue());
        ConnManagerParams.setTimeout(this.client.getParams(), this.timeOut.intValue());
        this.urlServer = str;
    }

    public Object execute() throws JSONException, IOException {
        return null;
    }
}
