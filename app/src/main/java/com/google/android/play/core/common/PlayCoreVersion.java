package com.google.android.play.core.common;

import android.os.Bundle;
import com.google.android.play.core.internal.zzag;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public class PlayCoreVersion {
    private static final Set zza = new HashSet(Arrays.asList("app_update", "review"));
    private static final Set zzb = new HashSet(Arrays.asList("native", "unity"));
    private static final Map zzc = new HashMap();
    private static final zzag zzd = new zzag("PlayCoreVersion");

    private PlayCoreVersion() {
    }

    public static synchronized void addVersion(String str, String str2, int i) {
        if (!zza.contains(str)) {
            zzd.zze("Illegal module name: %s", str);
        } else if (zzb.contains(str2)) {
            zzb(str).put(str2, Integer.valueOf(i));
        } else {
            zzd.zze("Illegal platform name: %s", str2);
        }
    }

    public static Bundle zza(String str) {
        Bundle bundle = new Bundle();
        Map mapZzb = zzb(str);
        bundle.putInt("playcore_version_code", ((Integer) mapZzb.get("java")).intValue());
        if (mapZzb.containsKey("native")) {
            bundle.putInt("playcore_native_version", ((Integer) mapZzb.get("native")).intValue());
        }
        if (mapZzb.containsKey("unity")) {
            bundle.putInt("playcore_unity_version", ((Integer) mapZzb.get("unity")).intValue());
        }
        return bundle;
    }

    public static synchronized Map zzb(String str) {
        Map map;
        map = zzc;
        if (!map.containsKey(str)) {
            HashMap map2 = new HashMap();
            map2.put("java", 11003);
            map.put(str, map2);
        }
        return (Map) map.get(str);
    }
}
