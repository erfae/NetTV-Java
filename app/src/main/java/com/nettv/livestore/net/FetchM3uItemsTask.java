package com.nettv.livestore.net;

import iptv.m3u.parser.M3UItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.json.JSONException;

/* JADX INFO: loaded from: classes2.dex */
public class FetchM3uItemsTask extends NetworkTask<Void, Void, List<M3UItem>> {
    private LoadM3UItemsCommand command;
    private ArrayList<NameValuePair> params;
    private String urlServer;

    public FetchM3uItemsTask(String str, ArrayList<NameValuePair> arrayList) {
        this.urlServer = str;
        this.params = arrayList;
    }

    @Override // com.nettv.livestore.net.NetworkTask
    public final List<M3UItem> doNetworkAction() throws JSONException, IOException {
        LoadM3UItemsCommand loadM3UItemsCommand = new LoadM3UItemsCommand(this.urlServer, this.params);
        this.command = loadM3UItemsCommand;
        return loadM3UItemsCommand.execute();
    }
}
