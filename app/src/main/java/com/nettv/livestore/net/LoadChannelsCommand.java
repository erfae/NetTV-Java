package com.nettv.livestore.net;

import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.models.EPGChannel;
import iptv.m3u.parser.M3UItem;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes2.dex */
public class LoadChannelsCommand {
    public ArrayList<EPGChannel> epgChannels;

    public ArrayList<EPGChannel> execute() {
        LTVApp lTVApp = LTVApp.getInstance();
        this.epgChannels = new ArrayList<>();
        Iterator<M3UItem> it = lTVApp.getM3UChannelsItems().iterator();
        int i = 0;
        while (it.hasNext()) {
            EPGChannel ePGChannelFromM3UItem = EPGChannel.fromM3UItem(it.next());
            if (ePGChannelFromM3UItem != null) {
                i++;
                ePGChannelFromM3UItem.setNum(String.valueOf(i));
                this.epgChannels.add(ePGChannelFromM3UItem);
            }
        }
        return this.epgChannels;
    }
}
