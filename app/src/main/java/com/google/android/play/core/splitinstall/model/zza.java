package com.google.android.play.core.splitinstall.model;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zza {
    private static final Map zza;
    private static final Map zzb;
    private static final Map zzc;

    static {
        HashMap map = new HashMap();
        zza = map;
        HashMap map2 = new HashMap();
        zzb = map2;
        Integer numM = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-1), "Too many sessions are running for current app, existing sessions must be resolved first.", -2, "A requested module is not available (to this user/device, for the installed apk).");
        Integer numM2 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-3), "Request is otherwise invalid.", -4, "Requested session is not found.");
        Integer numM3 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-5), "Split Install API is not available.", -6, "Network error: unable to obtain split details.");
        Integer numM4 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-7), "Download not permitted under current device circumstances (e.g. in background).", -8, "Requested session contains modules from an existing active session and also new modules.");
        Integer numM5 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-9), "Service handling split install has died.", -10, "Install failed due to insufficient storage.");
        Integer numM6 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-11), "Signature verification error when invoking SplitCompat.", -12, "Error in SplitCompat emulation.");
        Integer numM7 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-13), "Error in copying files for SplitCompat.", -14, "The Play Store app is either not installed or not the official version.");
        Integer numM8 = Insets$$ExternalSyntheticOutline0.m(map, (Integer) (-15), "The app is not owned by any user on this device. An app is \"owned\" if it has been acquired from Play.", -100, "Unknown error processing split install.");
        map2.put(-1, "ACTIVE_SESSIONS_LIMIT_EXCEEDED");
        map2.put(numM, "MODULE_UNAVAILABLE");
        map2.put(-3, "INVALID_REQUEST");
        map2.put(numM2, "DOWNLOAD_NOT_FOUND");
        map2.put(-5, "API_NOT_AVAILABLE");
        map2.put(numM3, "NETWORK_ERROR");
        map2.put(-7, "ACCESS_DENIED");
        map2.put(numM4, "INCOMPATIBLE_WITH_EXISTING_SESSION");
        map2.put(-9, "SERVICE_DIED");
        map2.put(numM5, "INSUFFICIENT_STORAGE");
        map2.put(-11, "SPLITCOMPAT_VERIFICATION_ERROR");
        map2.put(numM6, "SPLITCOMPAT_EMULATION_ERROR");
        map2.put(-13, "SPLITCOMPAT_COPY_ERROR");
        map2.put(numM7, "PLAY_STORE_NOT_FOUND");
        map2.put(-15, "APP_NOT_OWNED");
        map2.put(numM8, "INTERNAL_ERROR");
        zzc = new HashMap();
        for (Map.Entry entry : map2.entrySet()) {
            zzc.put((String) entry.getValue(), (Integer) entry.getKey());
        }
    }

    @SplitInstallErrorCode
    public static int zza(String str) {
        Integer num = (Integer) zzc.get(str);
        if (num != null) {
            return num.intValue();
        }
        throw new IllegalArgumentException(String.valueOf(str).concat(" is unknown error."));
    }

    public static String zzb(@SplitInstallErrorCode int i) {
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
        return Insets$$ExternalSyntheticOutline0.m(new StringBuilder(String.valueOf(str).length() + 118 + String.valueOf(str2).length()), str, " (https://developer.android.com/reference/com/google/android/play/core/splitinstall/model/SplitInstallErrorCode.html#", str2, ")");
    }
}
