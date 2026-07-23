package com.google.android.play.core.splitinstall;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzk {
    private final Map zza;

    public final Map zza(Collection collection) {
        Set setUnmodifiableSet;
        HashMap map = new HashMap();
        for (String str : this.zza.keySet()) {
            if (this.zza.containsKey(str)) {
                HashSet hashSet = new HashSet();
                for (Map.Entry entry : ((Map) this.zza.get(str)).entrySet()) {
                    if (collection.contains(entry.getKey())) {
                        hashSet.add((String) entry.getValue());
                    }
                }
                setUnmodifiableSet = Collections.unmodifiableSet(hashSet);
            } else {
                setUnmodifiableSet = Collections.emptySet();
            }
            map.put(str, setUnmodifiableSet);
        }
        return map;
    }
}
