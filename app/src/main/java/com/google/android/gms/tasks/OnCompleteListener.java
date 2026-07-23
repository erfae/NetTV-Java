package com.google.android.gms.tasks;

import androidx.annotation.NonNull;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tasks@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
public interface OnCompleteListener<TResult> {
    void onComplete(@NonNull Task<TResult> task);
}
