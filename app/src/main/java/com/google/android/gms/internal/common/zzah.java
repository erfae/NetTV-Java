package com.google.android.gms.internal.common;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
public final class zzah {
    public static Object[] zza(Object[] objArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (objArr[i2] == null) {
                throw new NullPointerException(Insets$$ExternalSyntheticOutline0.m(20, "at index ", i2));
            }
        }
        return objArr;
    }
}
