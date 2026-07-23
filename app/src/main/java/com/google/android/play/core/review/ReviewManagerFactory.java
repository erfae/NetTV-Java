package com.google.android.play.core.review;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.play.core.internal.zzce;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public class ReviewManagerFactory {
    private ReviewManagerFactory() {
    }

    @NonNull
    public static ReviewManager create(@NonNull Context context) {
        return new zzd(new zzi(zzce.zza(context)));
    }
}
