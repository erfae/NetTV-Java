package com.google.android.play.core.splitinstall.testing;

import androidx.annotation.Nullable;
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzt {
    public static final zzt zza;

    static {
        zza zzaVar = new zza();
        zzaVar.zzb(new HashMap());
        zzaVar.zzb(Collections.unmodifiableMap(zzaVar.zzd()));
        zza = zzaVar.zzc();
    }

    @Nullable
    @SplitInstallErrorCode
    public abstract Integer zza();

    public abstract Map zzb();
}
