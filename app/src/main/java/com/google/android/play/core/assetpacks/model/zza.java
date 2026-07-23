package com.google.android.play.core.assetpacks.model;

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
        Integer numM = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-1), "The requesting app is unavailable (e.g. unpublished, nonexistent version code).", -2, "The requested pack is not available.");
        Integer numM2 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-3), "The request is invalid.", -4, "The requested download is not found.");
        Integer numM3 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-5), "The Asset Delivery API is not available.", -6, "Network error. Unable to obtain the asset pack details.");
        Integer numM4 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-7), "Download not permitted under current device circumstances (e.g. in background).", -10, "Asset pack download failed due to insufficient storage.");
        Integer numM5 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-11), "The Play Store app is either not installed or not the official version.", -12, "Tried to show the cellular data confirmation but no asset packs are waiting for Wi-Fi.");
        Integer numM6 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-13), "The app is not owned by any user on this device. An app is \"owned\" if it has been acquired from Play.", -100, "Unknown error downloading an asset pack.");
        map2.put(-1, "APP_UNAVAILABLE");
        map2.put(numM, "PACK_UNAVAILABLE");
        map2.put(-3, "INVALID_REQUEST");
        map2.put(numM2, "DOWNLOAD_NOT_FOUND");
        map2.put(-5, "API_NOT_AVAILABLE");
        map2.put(numM3, "NETWORK_ERROR");
        map2.put(-7, "ACCESS_DENIED");
        map2.put(numM4, "INSUFFICIENT_STORAGE");
        map2.put(-11, "PLAY_STORE_NOT_FOUND");
        map2.put(numM5, "NETWORK_UNRESTRICTED");
        map2.put(-13, "APP_NOT_OWNED");
        map2.put(numM6, "INTERNAL_ERROR");
    }

    public static String zza(@AssetPackErrorCode int i) {
        Map map = zza;
        Integer numValueOf = Integer.valueOf(i);
        if (!map.containsKey(numValueOf)) {
            return "";
        }
        String str = (String) map.get(numValueOf);
        String str2 = (String) zzb.get(numValueOf);
        return Insets$$ExternalSyntheticOutline0.m(new StringBuilder(String.valueOf(str).length() + 113 + String.valueOf(str2).length()), str, " (https://developer.android.com/reference/com/google/android/play/core/assetpacks/model/AssetPackErrorCode.html#", str2, ")");
    }
}
