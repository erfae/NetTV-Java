package com.google.android.play.core.assetpacks;

import androidx.annotation.Nullable;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.zip.ZipException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbt {
    @Nullable
    public static AssetLocation zza(String str, String str2) throws IOException {
        Long lValueOf;
        int i;
        com.google.android.play.core.internal.zzci.zzb(str != null, "Attempted to get file location from a null apk path.");
        com.google.android.play.core.internal.zzci.zzb(str2 != null, String.format("Attempted to get file location in apk %s with a null file path.", str));
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        byte[] bArr = new byte[22];
        randomAccessFile.seek(randomAccessFile.length() - 22);
        randomAccessFile.readFully(bArr);
        zzbs zzbsVarZzb = zzbr.zzb(bArr, 0) == 1347093766 ? zzb(bArr) : null;
        byte b = 5;
        if (zzbsVarZzb == null) {
            long length = randomAccessFile.length() - 22;
            long j = (-65536) + length;
            if (j < 0) {
                j = 0;
            }
            int iMin = (int) Math.min(1024L, randomAccessFile.length());
            byte[] bArr2 = new byte[iMin];
            byte[] bArr3 = new byte[22];
            loop0: while (true) {
                long jMax = Math.max(3 + (length - ((long) iMin)), j);
                randomAccessFile.seek(jMax);
                randomAccessFile.readFully(bArr2);
                for (int i2 = iMin - 4; i2 >= 0; i2 -= 4) {
                    byte b2 = bArr2[i2];
                    if (b2 == b) {
                        i = 2;
                    } else if (b2 == 6) {
                        i = 3;
                    } else if (b2 != 75) {
                        i = b2 != 80 ? -1 : 0;
                    } else {
                        i = 1;
                    }
                    if (i >= 0 && i2 >= i && zzbr.zzb(bArr2, i2 - i) == 1347093766) {
                        randomAccessFile.seek((jMax + ((long) i2)) - ((long) i));
                        randomAccessFile.readFully(bArr3);
                        zzbsVarZzb = zzb(bArr3);
                        break loop0;
                    }
                    b = 5;
                }
                if (jMax == j) {
                    throw new ZipException(String.format("End Of Central Directory signature not found in APK %s", str));
                }
                length = jMax;
            }
        }
        long jZza = zzbsVarZzb.zza;
        byte[] bytes = str2.getBytes("UTF-8");
        byte[] bArr4 = new byte[46];
        byte[] bArr5 = new byte[str2.length()];
        int i3 = 0;
        while (true) {
            if (i3 >= zzbsVarZzb.zzb) {
                lValueOf = null;
                break;
            }
            randomAccessFile.seek(jZza);
            randomAccessFile.readFully(bArr4);
            int iZzb = zzbr.zzb(bArr4, 0);
            if (iZzb != 1347092738) {
                throw new ZipException(String.format("Missing central directory file header signature when looking for file %s in APK %s. Read %d entries out of %d. Found %d instead of the header signature %d.", str2, str, Integer.valueOf(i3), Integer.valueOf(zzbsVarZzb.zzb), Integer.valueOf(iZzb), 1347092738));
            }
            randomAccessFile.seek(jZza + 28);
            int iZza = zzbr.zza(bArr4, 28);
            if (iZza == str2.length()) {
                randomAccessFile.seek(46 + jZza);
                randomAccessFile.read(bArr5);
                if (Arrays.equals(bArr5, bytes)) {
                    lValueOf = Long.valueOf(zzbr.zzc(bArr4, 42));
                    break;
                }
            }
            jZza += (long) (iZza + 46 + zzbr.zza(bArr4, 30) + zzbr.zza(bArr4, 32));
            i3++;
        }
        if (lValueOf == null) {
            return null;
        }
        long jLongValue = lValueOf.longValue();
        byte[] bArr6 = new byte[8];
        randomAccessFile.seek(22 + jLongValue);
        randomAccessFile.readFully(bArr6);
        return new zzbl(str, jLongValue + 30 + ((long) zzbr.zza(bArr6, 4)) + ((long) zzbr.zza(bArr6, 6)), zzbr.zzc(bArr6, 0));
    }

    private static zzbs zzb(byte[] bArr) {
        int iZza = zzbr.zza(bArr, 10);
        zzbr.zzc(bArr, 12);
        return new zzbs(zzbr.zzc(bArr, 16), iZza);
    }
}
