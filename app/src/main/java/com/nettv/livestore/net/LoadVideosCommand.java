package com.nettv.livestore.net;

import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.models.MovieModel;
import iptv.m3u.parser.M3UItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class LoadVideosCommand {
    private HashMap<String, List<MovieModel>> generoHashMap = new HashMap<>();

    public ArrayList<MovieModel> execute() throws IOException {
        LTVApp lTVApp = LTVApp.getInstance();
        ArrayList<MovieModel> arrayList = new ArrayList<>();
        Iterator<M3UItem> it = lTVApp.getM3UVideosItems().iterator();
        int i = 0;
        while (it.hasNext()) {
            MovieModel movieModelFromM3UItem = MovieModel.fromM3UItem(it.next());
            if (movieModelFromM3UItem != null) {
                i++;
                movieModelFromM3UItem.setNum(i);
                arrayList.add(movieModelFromM3UItem);
            }
        }
        return arrayList;
    }
}
