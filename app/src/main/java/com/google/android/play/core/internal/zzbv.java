package com.google.android.play.core.internal;

import java.lang.reflect.Field;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public class zzbv {
    private final Object zza;
    private final Field zzb;
    private final Class zzc;

    public zzbv(Object obj, Field field, Class cls) {
        this.zza = obj;
        this.zzb = field;
        this.zzc = cls;
    }

    public final Object zzc() {
        try {
            return this.zzc.cast(this.zzb.get(this.zza));
        } catch (Exception e) {
            throw new zzbx(String.format("Failed to get value of field %s of type %s on object of type %s", this.zzb.getName(), this.zza.getClass().getName(), this.zzc.getName()), e);
        }
    }

    public final Field zzd() {
        return this.zzb;
    }

    public final void zze(Object obj) {
        try {
            this.zzb.set(this.zza, obj);
        } catch (Exception e) {
            throw new zzbx(String.format("Failed to set value of field %s of type %s on object of type %s", this.zzb.getName(), this.zza.getClass().getName(), this.zzc.getName()), e);
        }
    }
}
