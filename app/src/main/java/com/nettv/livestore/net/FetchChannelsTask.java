package com.nettv.livestore.net;

import com.nettv.livestore.models.EPGChannel;
import java.io.IOException;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: classes2.dex */
public class FetchChannelsTask extends NetworkTask<Void, Void, List<EPGChannel>> {
    private LoadChannelsCommand command;

    @Override // com.nettv.livestore.net.NetworkTask
    public final List<EPGChannel> doNetworkAction() throws JSONException, IOException {
        LoadChannelsCommand loadChannelsCommand = new LoadChannelsCommand();
        this.command = loadChannelsCommand;
        return loadChannelsCommand.execute();
    }

    @Override // com.nettv.livestore.net.NetworkTask, android.os.AsyncTask
    public final void onPreExecute() {
    }
}
