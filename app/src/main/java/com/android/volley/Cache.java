package com.android.volley;

import androidx.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface Cache {

    public static class Entry {
        public List<Header> allResponseHeaders;
        public byte[] data;
        public String etag;
        public long lastModified;
        public Map<String, String> responseHeaders = Collections.emptyMap();
        public long serverDate;
        public long softTtl;
        public long ttl;

        public boolean isExpired() {
            return isExpired(System.currentTimeMillis());
        }

        public boolean refreshNeeded() {
            return refreshNeeded(System.currentTimeMillis());
        }

        public final boolean isExpired(long j) {
            return this.ttl < j;
        }

        public final boolean refreshNeeded(long j) {
            return this.softTtl < j;
        }
    }

    void clear();

    @Nullable
    Entry get(String str);

    void initialize();

    void invalidate(String str, boolean z);

    void put(String str, Entry entry);

    void remove(String str);
}
