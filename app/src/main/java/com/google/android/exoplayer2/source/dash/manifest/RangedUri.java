package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.util.UriUtil;

/* JADX INFO: loaded from: classes.dex */
public final class RangedUri {
    private int hashCode;
    public final long length;
    private final String referenceUri;
    public final long start;

    public RangedUri(@Nullable String str, long j, long j2) {
        this.referenceUri = str == null ? "" : str;
        this.start = j;
        this.length = j2;
    }

    @Nullable
    public RangedUri attemptMerge(@Nullable RangedUri rangedUri, String str) {
        String strResolveUriString = resolveUriString(str);
        if (rangedUri != null && strResolveUriString.equals(rangedUri.resolveUriString(str))) {
            long j = this.length;
            if (j != -1) {
                long j2 = this.start;
                if (j2 + j == rangedUri.start) {
                    long j3 = rangedUri.length;
                    return new RangedUri(strResolveUriString, j2, j3 != -1 ? j + j3 : -1L);
                }
            }
            long j4 = rangedUri.length;
            if (j4 != -1) {
                long j5 = rangedUri.start;
                if (j5 + j4 == this.start) {
                    return new RangedUri(strResolveUriString, j5, j != -1 ? j4 + j : -1L);
                }
            }
        }
        return null;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || RangedUri.class != obj.getClass()) {
            return false;
        }
        RangedUri rangedUri = (RangedUri) obj;
        return this.start == rangedUri.start && this.length == rangedUri.length && this.referenceUri.equals(rangedUri.referenceUri);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = this.referenceUri.hashCode() + ((((527 + ((int) this.start)) * 31) + ((int) this.length)) * 31);
        }
        return this.hashCode;
    }

    public Uri resolveUri(String str) {
        return UriUtil.resolveToUri(str, this.referenceUri);
    }

    public String resolveUriString(String str) {
        return UriUtil.resolve(str, this.referenceUri);
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("RangedUri(referenceUri=");
        sbM.append(this.referenceUri);
        sbM.append(", start=");
        sbM.append(this.start);
        sbM.append(", length=");
        sbM.append(this.length);
        sbM.append(")");
        return sbM.toString();
    }
}
