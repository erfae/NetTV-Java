package com.google.android.play.core.review;

import com.google.android.play.core.tasks.zzj;
import java.util.Locale;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public class ReviewException extends zzj {
    private final int zza;

    public ReviewException(int i) {
        super(String.format(Locale.getDefault(), "Review Error(%d): %s", Integer.valueOf(i), com.google.android.play.core.review.model.zza.zza(i)));
        this.zza = i;
    }

    @Override // com.google.android.play.core.tasks.zzj
    public int getErrorCode() {
        return this.zza;
    }
}
