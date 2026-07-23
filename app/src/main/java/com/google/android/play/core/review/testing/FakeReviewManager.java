package com.google.android.play.core.review.testing;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import com.google.android.play.core.review.ReviewException;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public class FakeReviewManager implements ReviewManager {
    private final Context zza;
    private ReviewInfo zzb;

    public FakeReviewManager(Context context) {
        this.zza = context;
    }

    @Override // com.google.android.play.core.review.ReviewManager
    @NonNull
    public Task<Void> launchReviewFlow(@NonNull Activity activity, @NonNull ReviewInfo reviewInfo) {
        return reviewInfo != this.zzb ? Tasks.zza(new ReviewException(-2)) : Tasks.zzb(null);
    }

    @Override // com.google.android.play.core.review.ReviewManager
    @NonNull
    public Task<ReviewInfo> requestReviewFlow() {
        ReviewInfo reviewInfoZzc = ReviewInfo.zzc(PendingIntent.getBroadcast(this.zza, 0, new Intent(), Build.VERSION.SDK_INT >= 23 ? 67108864 : 0), false);
        this.zzb = reviewInfoZzc;
        return Tasks.zzb(reviewInfoZzc);
    }
}
