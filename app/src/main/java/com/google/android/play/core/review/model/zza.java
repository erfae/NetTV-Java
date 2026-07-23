package com.google.android.play.core.review.model;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zza {
    private static final Map zza;
    private static final Map zzb;

    static {
        HashMap map = new HashMap();
        zza = map;
        HashMap map2 = new HashMap();
        zzb = map2;
        Integer numM = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-1), "The Play Store app is either not installed or not the official version.", -2, "Call first requestReviewFlow to get the ReviewInfo.");
        map2.put(-1, "PLAY_STORE_NOT_FOUND");
        map2.put(numM, "INVALID_REQUEST");
    }

    public static String zza(int i) {
        Map map = zza;
        Integer numValueOf = Integer.valueOf(i);
        if (!map.containsKey(numValueOf)) {
            return "";
        }
        String str = (String) map.get(numValueOf);
        String str2 = (String) zzb.get(numValueOf);
        return Insets$$ExternalSyntheticOutline0.m(new StringBuilder(String.valueOf(str).length() + 106 + String.valueOf(str2).length()), str, " (https://developer.android.com/reference/com/google/android/play/core/review/model/ReviewErrorCode.html#", str2, ")");
    }
}
