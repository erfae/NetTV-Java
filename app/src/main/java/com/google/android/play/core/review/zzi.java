package com.google.android.play.core.review;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.play.core.internal.zzab;
import com.google.android.play.core.internal.zzag;
import com.google.android.play.core.internal.zzan;
import com.google.android.play.core.internal.zzas;
import com.google.android.play.core.internal.zzch;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
@SuppressLint({"RestrictedApi"})
public final class zzi {
    private static final zzag zzb = new zzag("ReviewService");

    @Nullable
    @VisibleForTesting
    public zzas zza;
    private final String zzc;

    public zzi(Context context) {
        this.zzc = context.getPackageName();
        if (zzch.zzb(context)) {
            this.zza = new zzas(context, zzb, "com.google.android.finsky.inappreviewservice.InAppReviewService", new Intent("com.google.android.finsky.BIND_IN_APP_REVIEW_SERVICE").setPackage("com.android.vending"), new zzan() { // from class: com.google.android.play.core.review.zze
                @Override // com.google.android.play.core.internal.zzan
                public final Object zza(IBinder iBinder) {
                    return zzab.zzb(iBinder);
                }
            }, null);
        }
    }

    public final Task zzb() {
        zzag zzagVar = zzb;
        zzagVar.zzd("requestInAppReview (%s)", this.zzc);
        if (this.zza == null) {
            zzagVar.zzb("Play Store app is either not installed or not the official version", new Object[0]);
            return Tasks.zza(new ReviewException(-1));
        }
        com.google.android.play.core.tasks.zzi zziVar = new com.google.android.play.core.tasks.zzi();
        this.zza.zzq(new zzf(this, zziVar, zziVar), zziVar);
        return zziVar.zza();
    }
}
