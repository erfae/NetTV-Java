package com.google.android.gms.common.api.internal;

import android.os.Looper;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
@KeepForSdk
public class StatusPendingResult extends BasePendingResult<Status> {
    @Deprecated
    public StatusPendingResult(@NonNull Looper looper) {
        super(looper);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    @NonNull
    public final /* bridge */ /* synthetic */ Result createFailedResult(@NonNull Status status) {
        return status;
    }

    @KeepForSdk
    public StatusPendingResult(@NonNull GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }
}
