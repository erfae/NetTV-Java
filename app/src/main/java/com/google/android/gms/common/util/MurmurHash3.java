package com.google.android.gms.common.util;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
@KeepForSdk
public class MurmurHash3 {
    private MurmurHash3() {
    }

    @KeepForSdk
    public static int murmurhash3_x86_32(@NonNull byte[] bArr, int i, int i2, int i3) {
        int i4 = (i2 & (-4)) + i;
        while (i < i4) {
            int i5 = ((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | (bArr[i + 3] << 24)) * (-862048943);
            int i6 = i3 ^ (((i5 << 15) | (i5 >>> 17)) * 461845907);
            i3 = (((i6 >>> 19) | (i6 << 13)) * 5) - 430675100;
            i += 4;
        }
        int i7 = i2 & 3;
        int i8 = 0;
        if (i7 == 1) {
            int i9 = ((bArr[i4] & 255) | i8) * (-862048943);
            i3 ^= ((i9 >>> 17) | (i9 << 15)) * 461845907;
        } else {
            if (i7 != 2) {
                i8 = i7 == 3 ? (bArr[i4 + 2] & 255) << 16 : 0;
            }
            i8 |= (bArr[i4 + 1] & 255) << 8;
            int i10 = ((bArr[i4] & 255) | i8) * (-862048943);
            i3 ^= ((i10 >>> 17) | (i10 << 15)) * 461845907;
        }
        int i11 = i3 ^ i2;
        int i12 = (i11 ^ (i11 >>> 16)) * (-2048144789);
        int i13 = (i12 ^ (i12 >>> 13)) * (-1028477387);
        return i13 ^ (i13 >>> 16);
    }
}
