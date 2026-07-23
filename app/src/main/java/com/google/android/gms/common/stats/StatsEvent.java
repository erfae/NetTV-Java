package com.google.android.gms.common.stats;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
@KeepForSdk
@Deprecated
public abstract class StatsEvent extends AbstractSafeParcelable implements ReflectedParcelable {

    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    @KeepForSdk
    public interface Types {

        @KeepForSdk
        public static final int EVENT_TYPE_ACQUIRE_WAKE_LOCK = 7;

        @KeepForSdk
        public static final int EVENT_TYPE_RELEASE_WAKE_LOCK = 8;
    }

    @NonNull
    public final String toString() {
        long jZzc = zzc();
        int iZza = zza();
        long jZzb = zzb();
        String strZzd = zzd();
        StringBuilder sb = new StringBuilder(strZzd.length() + 53);
        sb.append(jZzc);
        sb.append("\t");
        sb.append(iZza);
        sb.append("\t");
        sb.append(jZzb);
        sb.append(strZzd);
        return sb.toString();
    }

    public abstract int zza();

    public abstract long zzb();

    public abstract long zzc();

    @NonNull
    public abstract String zzd();
}
