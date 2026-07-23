package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
@ShowFirstParty
@KeepForSdk
public interface Clock {

    /* JADX INFO: renamed from: com.google.android.gms.common.util.Clock$-CC, reason: invalid class name */
    /* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    public final /* synthetic */ class CC {
    }

    @KeepForSdk
    long currentThreadTimeMillis();

    @KeepForSdk
    long currentTimeMillis();

    @KeepForSdk
    long elapsedRealtime();

    @KeepForSdk
    long nanoTime();
}
