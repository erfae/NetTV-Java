package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.DataSpec;
import io.realm.Realm$$ExternalSyntheticLambda0;

/* JADX INFO: loaded from: classes.dex */
public interface CacheKeyFactory {
    public static final CacheKeyFactory DEFAULT = Realm$$ExternalSyntheticLambda0.INSTANCE$10;

    /* JADX INFO: renamed from: com.google.android.exoplayer2.upstream.cache.CacheKeyFactory$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        static {
            CacheKeyFactory cacheKeyFactory = CacheKeyFactory.DEFAULT;
        }

        public static /* synthetic */ String lambda$static$0(DataSpec dataSpec) {
            String str = dataSpec.key;
            return str != null ? str : dataSpec.uri.toString();
        }
    }

    String buildCacheKey(DataSpec dataSpec);
}
