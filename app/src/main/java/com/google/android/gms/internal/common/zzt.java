package com.google.android.gms.internal.common;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzt extends zzw {
    public final /* synthetic */ zzu zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzt(zzu zzuVar, zzx zzxVar, CharSequence charSequence) {
        super(zzxVar, charSequence);
        this.zza = zzuVar;
    }

    @Override // com.google.android.gms.internal.common.zzw
    public final int zzc(int i) {
        return i + 1;
    }

    @Override // com.google.android.gms.internal.common.zzw
    public final int zzd(int i) {
        zzo zzoVar = this.zza.zza;
        CharSequence charSequence = ((zzw) this).zzb;
        int length = charSequence.length();
        zzs.zzb(i, length, "index");
        while (i < length) {
            if (zzoVar.zza(charSequence.charAt(i))) {
                return i;
            }
            i++;
        }
        return -1;
    }
}
