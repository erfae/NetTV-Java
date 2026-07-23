package iptv.m3u.parser;

/* JADX INFO: loaded from: classes2.dex */
public class M3UToolSet {
    public static M3UFile load(String str) {
        final M3UFile m3UFile = new M3UFile();
        M3UParser.getInstance().parse(str, new M3UHandler() { // from class: iptv.m3u.parser.M3UToolSet.1
            @Override // iptv.m3u.parser.M3UHandler
            public void onReadEXTINF(M3UItem m3UItem) {
                m3UFile.addItem(m3UItem);
            }

            @Override // iptv.m3u.parser.M3UHandler
            public void onReadEXTM3U(M3UHead m3UHead) {
                m3UFile.setHeader(m3UHead);
            }
        });
        return m3UFile;
    }
}
