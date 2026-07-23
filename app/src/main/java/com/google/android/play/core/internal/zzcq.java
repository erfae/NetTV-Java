package com.google.android.play.core.internal;

import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzcq implements zzcs, zzco {
    private static final Object zza = new Object();
    private volatile zzcs zzb;
    private volatile Object zzc = zza;

    private zzcq(zzcs zzcsVar) {
        this.zzb = zzcsVar;
    }

    public static zzco zzb(zzcs zzcsVar) {
        if (zzcsVar instanceof zzco) {
            return (zzco) zzcsVar;
        }
        Objects.requireNonNull(zzcsVar);
        return new zzcq(zzcsVar);
    }

    public static zzcs zzc(zzcs zzcsVar) {
        Objects.requireNonNull(zzcsVar);
        return zzcsVar instanceof zzcq ? zzcsVar : new zzcq(zzcsVar);
    }

    @Override // com.google.android.play.core.internal.zzcs
    public final Object zza() {
        Object objZza = this.zzc;
        Object obj = zza;
        if (objZza == obj) {
            synchronized (this) {
                objZza = this.zzc;
                if (objZza == obj) {
                    objZza = this.zzb.zza();
                    Object obj2 = this.zzc;
                    if (obj2 != obj && obj2 != objZza) {
                        String strValueOf = String.valueOf(obj2);
                        String strValueOf2 = String.valueOf(objZza);
                        StringBuilder sb = new StringBuilder(strValueOf.length() + 118 + strValueOf2.length());
                        sb.append("Scoped provider was invoked recursively returning different results: ");
                        sb.append(strValueOf);
                        sb.append(" & ");
                        sb.append(strValueOf2);
                        sb.append(". This is likely due to a circular dependency.");
                        throw new IllegalStateException(sb.toString());
                    }
                    this.zzc = objZza;
                    this.zzb = null;
                }
            }
        }
        return objZza;
    }
}
