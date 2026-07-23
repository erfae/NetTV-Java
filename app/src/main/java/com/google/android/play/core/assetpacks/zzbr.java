package com.google.android.play.core.assetpacks;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbr {
    public static int zza(byte[] bArr, int i) {
        return ((bArr[i + 1] & 255) << 8) | (bArr[i] & 255);
    }

    public static int zzb(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    public static long zzc(byte[] bArr, int i) {
        return ((long) ((zza(bArr, i + 2) << 16) | zza(bArr, i))) & 4294967295L;
    }
}
