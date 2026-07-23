package com.nettv.livestore.net;

import android.util.Log;
import com.nettv.livestore.apps.LTVApp;
import com.nettv.livestore.utils.Utils;
import iptv.m3u.parser.M3UItem;
import iptv.m3u.parser.M3UToolSet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;

/* JADX INFO: loaded from: classes2.dex */
public class LoadM3UItemsCommand extends BaseCommand {
    public LoadM3UItemsCommand(String str, ArrayList<NameValuePair> arrayList) {
        super(str, arrayList);
    }

    @Override // com.nettv.livestore.net.BaseCommand
    public List<M3UItem> execute() throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        File file = new File(LTVApp.getInstance().getExternalFilesDir(null), Utils.getUserId(this.urlServer) + ".m3u");
        Log.e("urlServer", this.urlServer);
        if (!file.exists() || !simpleDateFormat.format(new Date()).equals(LTVApp.instance.getM3uDate())) {
            if (file.exists()) {
                file.delete();
            }
            try {
                InputStream inputStreamOpenStream = new URL(this.urlServer).openStream();
                FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                byte[] bArr = new byte[4096];
                while (true) {
                    int i = inputStreamOpenStream.read(bArr);
                    if (i <= -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, i);
                }
                fileOutputStream.close();
                inputStreamOpenStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return M3UToolSet.load(file.getPath()).getItems();
    }
}
