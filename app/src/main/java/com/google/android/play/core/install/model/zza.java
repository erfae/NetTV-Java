package com.google.android.play.core.install.model;

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
        Integer numM = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-2), "An unknown error occurred.", -3, "The API is not available on this device.");
        Integer numM2 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-4), "The request that was sent by the app is malformed.", -5, "The install is unavailable to this user or device.");
        Integer numM3 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-6), "The download/install is not allowed, due to the current device state (e.g. low battery, low disk space, ...).", -7, "The install/update has not been (fully) downloaded yet.");
        Integer numM4 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-8), "The install is already in progress and there is no UI flow to resume.", -9, "The Play Store app is either not installed or not the official version.");
        Integer numM5 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-10), "The app is not owned by any user on this device. An app is \"owned\" if it has been acquired from Play.", -100, "An internal error happened in the Play Store.");
        map2.put(-2, "ERROR_UNKNOWN");
        map2.put(numM, "ERROR_API_NOT_AVAILABLE");
        map2.put(-4, "ERROR_INVALID_REQUEST");
        map2.put(numM2, "ERROR_INSTALL_UNAVAILABLE");
        map2.put(-6, "ERROR_INSTALL_NOT_ALLOWED");
        map2.put(numM3, "ERROR_DOWNLOAD_NOT_PRESENT");
        map2.put(-8, "ERROR_INSTALL_IN_PROGRESS");
        map2.put(numM5, "ERROR_INTERNAL_ERROR");
        map2.put(numM4, "ERROR_PLAY_STORE_NOT_FOUND");
        map2.put(-10, "ERROR_APP_NOT_OWNED");
        map2.put(numM5, "ERROR_INTERNAL_ERROR");
    }

    public static String zza(@InstallErrorCode int i) {
        Map map = zza;
        Integer numValueOf = Integer.valueOf(i);
        if (!map.containsKey(numValueOf)) {
            return "";
        }
        Map map2 = zzb;
        if (!map2.containsKey(numValueOf)) {
            return "";
        }
        String str = (String) map.get(numValueOf);
        String str2 = (String) map2.get(numValueOf);
        return Insets$$ExternalSyntheticOutline0.m(new StringBuilder(String.valueOf(str).length() + 103 + String.valueOf(str2).length()), str, " (https://developer.android.com/reference/com/google/android/play/core/install/model/InstallErrorCode#", str2, ")");
    }
}
