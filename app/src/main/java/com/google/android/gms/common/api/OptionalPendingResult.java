package com.google.android.gms.common.api;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Result;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
public abstract class OptionalPendingResult<R extends Result> extends PendingResult<R> {
    @NonNull
    public abstract R get();

    public abstract boolean isDone();
}
