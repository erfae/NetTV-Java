package com.google.android.play.core.review;

import android.app.Activity;
import androidx.annotation.NonNull;
import com.google.android.play.core.tasks.Task;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public interface ReviewManager {
    @NonNull
    Task<Void> launchReviewFlow(@NonNull Activity activity, @NonNull ReviewInfo reviewInfo);

    @NonNull
    Task<ReviewInfo> requestReviewFlow();
}
