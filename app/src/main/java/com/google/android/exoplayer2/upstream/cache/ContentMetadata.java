package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public interface ContentMetadata {
    public static final String KEY_CONTENT_LENGTH = "exo_len";
    public static final String KEY_CUSTOM_PREFIX = "custom_";
    public static final String KEY_REDIRECTED_URI = "exo_redir";

    /* JADX INFO: renamed from: com.google.android.exoplayer2.upstream.cache.ContentMetadata$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static long getContentLength(ContentMetadata contentMetadata) {
            return contentMetadata.get(ContentMetadata.KEY_CONTENT_LENGTH, -1L);
        }

        @Nullable
        public static Uri getRedirectedUri(ContentMetadata contentMetadata) {
            String str = contentMetadata.get(ContentMetadata.KEY_REDIRECTED_URI, (String) null);
            if (str == null) {
                return null;
            }
            return Uri.parse(str);
        }
    }

    boolean contains(String str);

    long get(String str, long j);

    @Nullable
    String get(String str, @Nullable String str2);

    @Nullable
    byte[] get(String str, @Nullable byte[] bArr);
}
