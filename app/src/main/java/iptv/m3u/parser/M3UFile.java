package iptv.m3u.parser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class M3UFile {
    private M3UHead mHeader;
    private List<M3UItem> mItems = new LinkedList();

    public boolean addItem(M3UItem m3UItem) {
        return this.mItems.add(m3UItem);
    }

    public boolean addItems(List<M3UItem> list) {
        return this.mItems.addAll(list);
    }

    public M3UHead getHeader() {
        return this.mHeader;
    }

    public List<M3UItem> getItems() {
        return this.mItems;
    }

    public void setHeader(M3UHead m3UHead) {
        this.mHeader = m3UHead;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        M3UHead m3UHead = this.mHeader;
        if (m3UHead != null) {
            stringBuffer.append(m3UHead.toString());
        } else {
            stringBuffer.append("No header");
        }
        stringBuffer.append('\n');
        Iterator<M3UItem> it = this.mItems.iterator();
        while (it.hasNext()) {
            stringBuffer.append(it.next().toString());
            stringBuffer.append('\n');
        }
        return stringBuffer.toString();
    }
}
