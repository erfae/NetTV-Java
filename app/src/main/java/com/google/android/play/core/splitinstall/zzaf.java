package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.play.core.internal.zzcs;
import java.io.File;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzaf implements zzcs {
    private final zzcs zza;

    public zzaf(zzcs zzcsVar) {
        this.zza = zzcsVar;
    }

    @Override // com.google.android.play.core.internal.zzcs
    @Nullable
    public final /* bridge */ /* synthetic */ Object zza() {
        String string;
        Context contextZzb = ((zzad) this.zza).zzb();
        try {
            Bundle bundle = contextZzb.getPackageManager().getApplicationInfo(contextZzb.getPackageName(), 128).metaData;
            if (bundle != null && (string = bundle.getString("local_testing_dir")) != null) {
                return new File(contextZzb.getExternalFilesDir(null), string);
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return null;
    }
}
