package com.google.android.gms.auth.api.signin.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
@KeepForSdk
public class HashAccumulator {
    private int zab = 1;

    @NonNull
    @KeepForSdk
    public HashAccumulator addObject(@Nullable Object obj) {
        this.zab = (31 * this.zab) + (obj == null ? 0 : obj.hashCode());
        return this;
    }

    @KeepForSdk
    public int hash() {
        return this.zab;
    }

    @NonNull
    public final HashAccumulator zaa(boolean z) {
        this.zab = (31 * this.zab) + (z ? 1 : 0);
        return this;
    }
}
