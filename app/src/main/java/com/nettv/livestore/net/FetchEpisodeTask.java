package com.nettv.livestore.net;

import com.nettv.livestore.models.EpisodeModel;
import java.io.IOException;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: classes2.dex */
public class FetchEpisodeTask extends NetworkTask<Void, Void, List<EpisodeModel>> {
    private LoadEpisodeCommand command;

    @Override // com.nettv.livestore.net.NetworkTask
    public final List<EpisodeModel> doNetworkAction() throws JSONException, IOException {
        LoadEpisodeCommand loadEpisodeCommand = new LoadEpisodeCommand();
        this.command = loadEpisodeCommand;
        return loadEpisodeCommand.execute();
    }

    @Override // com.nettv.livestore.net.NetworkTask, android.os.AsyncTask
    public final void onPreExecute() {
    }
}
