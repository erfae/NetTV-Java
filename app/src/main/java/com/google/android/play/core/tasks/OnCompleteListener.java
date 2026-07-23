package com.google.android.play.core.tasks;

import androidx.annotation.NonNull;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public interface OnCompleteListener<ResultT> {
    void onComplete(@NonNull Task<ResultT> task);
}
