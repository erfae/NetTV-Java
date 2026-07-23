package com.nettv.livestore.net;

import com.nettv.livestore.models.MovieModel;
import java.io.IOException;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: classes2.dex */
public class FetchVideosTask extends NetworkTask<Void, Void, List<MovieModel>> {
    private LoadVideosCommand command;

    @Override // com.nettv.livestore.net.NetworkTask
    public final List<MovieModel> doNetworkAction() throws JSONException, IOException {
        LoadVideosCommand loadVideosCommand = new LoadVideosCommand();
        this.command = loadVideosCommand;
        return loadVideosCommand.execute();
    }

    @Override // com.nettv.livestore.net.NetworkTask, android.os.AsyncTask
    public final void onPreExecute() {
    }
}
