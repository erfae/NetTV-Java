package com.google.android.gms.internal.common;

import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
abstract class zzw extends zzj<String> {
    public final CharSequence zzb;
    public final boolean zzd;
    public int zze = 0;
    public int zzf;

    public zzw(zzx zzxVar, CharSequence charSequence) {
        zzo unused = zzxVar.zza;
        this.zzd = zzxVar.zzb;
        this.zzf = Integer.MAX_VALUE;
        this.zzb = charSequence;
    }

    @Override // com.google.android.gms.internal.common.zzj
    @CheckForNull
    public final /* bridge */ /* synthetic */ String zza() {
        int iZzc;
        int i = this.zze;
        while (true) {
            int i2 = this.zze;
            if (i2 == -1) {
                zzb();
                return null;
            }
            int iZzd = zzd(i2);
            if (iZzd == -1) {
                iZzd = this.zzb.length();
                this.zze = -1;
                iZzc = -1;
            } else {
                iZzc = zzc(iZzd);
                this.zze = iZzc;
            }
            if (iZzc == i) {
                int i3 = iZzc + 1;
                this.zze = i3;
                if (i3 > this.zzb.length()) {
                    this.zze = -1;
                }
            } else {
                if (i < iZzd) {
                    this.zzb.charAt(i);
                }
                if (i < iZzd) {
                    this.zzb.charAt(iZzd - 1);
                }
                if (!this.zzd || i != iZzd) {
                    int i4 = this.zzf;
                    if (i4 == 1) {
                        iZzd = this.zzb.length();
                        this.zze = -1;
                        if (iZzd > i) {
                            this.zzb.charAt(iZzd - 1);
                        }
                    } else {
                        this.zzf = i4 - 1;
                    }
                    return this.zzb.subSequence(i, iZzd).toString();
                }
                i = this.zze;
            }
        }
    }

    public abstract int zzc(int i);

    public abstract int zzd(int i);
}
