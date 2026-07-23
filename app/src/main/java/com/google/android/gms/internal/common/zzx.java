package com.google.android.gms.internal.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
public final class zzx {
    private final zzo zza;
    private final boolean zzb;
    private final zzu zzc;

    private zzx(zzu zzuVar, boolean z, zzo zzoVar, int i, byte[] bArr) {
        this.zzc = zzuVar;
        this.zzb = z;
        this.zza = zzoVar;
    }

    public static zzx zzc(zzo zzoVar) {
        return new zzx(new zzu(zzoVar), false, zzn.zza, Integer.MAX_VALUE, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Iterator<String> zzh(CharSequence charSequence) {
        return new zzt(this.zzc, this, charSequence);
    }

    public final zzx zzb() {
        return new zzx(this.zzc, true, this.zza, Integer.MAX_VALUE, null);
    }

    public final Iterable<String> zzd(CharSequence charSequence) {
        return new zzv(this, charSequence);
    }

    public final List<String> zzf(CharSequence charSequence) {
        Objects.requireNonNull(charSequence);
        Iterator<String> itZzh = zzh(charSequence);
        ArrayList arrayList = new ArrayList();
        while (itZzh.hasNext()) {
            arrayList.add(itZzh.next());
        }
        return Collections.unmodifiableList(arrayList);
    }
}
