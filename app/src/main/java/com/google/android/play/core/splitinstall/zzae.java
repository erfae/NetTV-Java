package com.google.android.play.core.splitinstall;

import androidx.annotation.Nullable;
import com.google.android.play.core.internal.zzcs;
import java.io.File;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzae implements zzcs {
    private final zzcs zza;

    public zzae(zzcs zzcsVar) {
        this.zza = zzcsVar;
    }

    @Override // com.google.android.play.core.internal.zzcs
    @Nullable
    public final /* bridge */ /* synthetic */ Object zza() {
        File file = (File) this.zza.zza();
        if (file == null) {
            return null;
        }
        return com.google.android.play.core.splitinstall.testing.zzy.zza(file);
    }
}
