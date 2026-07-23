package com.google.android.play.core.review;

import android.app.PendingIntent;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zza extends ReviewInfo {
    private final PendingIntent zza;
    private final boolean zzb;

    public zza(PendingIntent pendingIntent, boolean z) {
        Objects.requireNonNull(pendingIntent, "Null pendingIntent");
        this.zza = pendingIntent;
        this.zzb = z;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ReviewInfo) {
            ReviewInfo reviewInfo = (ReviewInfo) obj;
            if (this.zza.equals(reviewInfo.zza()) && this.zzb == reviewInfo.zzb()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((this.zza.hashCode() ^ 1000003) * 1000003) ^ (true != this.zzb ? 1237 : 1231);
    }

    public final String toString() {
        String string = this.zza.toString();
        boolean z = this.zzb;
        StringBuilder sb = new StringBuilder(string.length() + 40);
        sb.append("ReviewInfo{pendingIntent=");
        sb.append(string);
        sb.append(", isNoOp=");
        sb.append(z);
        sb.append("}");
        return sb.toString();
    }

    @Override // com.google.android.play.core.review.ReviewInfo
    public final PendingIntent zza() {
        return this.zza;
    }

    @Override // com.google.android.play.core.review.ReviewInfo
    public final boolean zzb() {
        return this.zzb;
    }
}
