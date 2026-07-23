package com.google.android.play.core.internal;

import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.IllegalFormatException;
import java.util.Locale;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzag {
    private final String zza;

    public zzag(String str) {
        int iMyUid = Process.myUid();
        int iMyPid = Process.myPid();
        StringBuilder sb = new StringBuilder(39);
        sb.append("UID: [");
        sb.append(iMyUid);
        sb.append("]  PID: [");
        sb.append(iMyPid);
        sb.append("] ");
        String string = sb.toString();
        String strValueOf = String.valueOf(str);
        this.zza = strValueOf.length() != 0 ? string.concat(strValueOf) : new String(string);
    }

    private static String zzf(String str, String str2, @Nullable Object... objArr) {
        if (objArr.length > 0) {
            try {
                str2 = String.format(Locale.US, str2, objArr);
            } catch (IllegalFormatException e) {
                String strValueOf = String.valueOf(str2);
                Log.e("PlayCore", strValueOf.length() != 0 ? "Unable to format ".concat(strValueOf) : new String("Unable to format "), e);
                String strJoin = TextUtils.join(", ", objArr);
                str2 = Insets$$ExternalSyntheticOutline0.m(new StringBuilder(String.valueOf(str2).length() + 3 + String.valueOf(strJoin).length()), str2, " [", strJoin, "]");
            }
        }
        return Insets$$ExternalSyntheticOutline0.m(new StringBuilder(String.valueOf(str).length() + 3 + String.valueOf(str2).length()), str, " : ", str2);
    }

    public final int zza(String str, @Nullable Object... objArr) {
        if (Log.isLoggable("PlayCore", 3)) {
            return Log.d("PlayCore", zzf(this.zza, str, objArr));
        }
        return 0;
    }

    public final int zzb(String str, @Nullable Object... objArr) {
        if (Log.isLoggable("PlayCore", 6)) {
            return Log.e("PlayCore", zzf(this.zza, str, objArr));
        }
        return 0;
    }

    public final int zzc(Throwable th, String str, @Nullable Object... objArr) {
        if (Log.isLoggable("PlayCore", 6)) {
            return Log.e("PlayCore", zzf(this.zza, str, objArr), th);
        }
        return 0;
    }

    public final int zzd(String str, @Nullable Object... objArr) {
        if (Log.isLoggable("PlayCore", 4)) {
            return Log.i("PlayCore", zzf(this.zza, str, objArr));
        }
        return 0;
    }

    public final int zze(String str, @Nullable Object... objArr) {
        if (Log.isLoggable("PlayCore", 5)) {
            return Log.w("PlayCore", zzf(this.zza, str, objArr));
        }
        return 0;
    }
}
