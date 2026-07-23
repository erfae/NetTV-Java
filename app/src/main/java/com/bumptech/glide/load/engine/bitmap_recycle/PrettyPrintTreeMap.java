package com.bumptech.glide.load.engine.bitmap_recycle;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes.dex */
class PrettyPrintTreeMap<K, V> extends TreeMap<K, V> {
    @Override // java.util.AbstractMap
    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("( ");
        for (Map.Entry<K, V> entry : entrySet()) {
            sbM.append('{');
            sbM.append(entry.getKey());
            sbM.append(':');
            sbM.append(entry.getValue());
            sbM.append("}, ");
        }
        if (!isEmpty()) {
            sbM.replace(sbM.length() - 2, sbM.length(), "");
        }
        sbM.append(" )");
        return sbM.toString();
    }
}
