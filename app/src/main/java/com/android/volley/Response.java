package com.android.volley;

import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class Response<T> {

    @Nullable
    public final Cache.Entry cacheEntry;

    @Nullable
    public final VolleyError error;
    public boolean intermediate;

    @Nullable
    public final T result;

    public interface ErrorListener {
        void onErrorResponse(VolleyError volleyError);
    }

    public interface Listener<T> {
        void onResponse(T t);
    }

    private Response(@Nullable T t, @Nullable Cache.Entry entry) {
        this.intermediate = false;
        this.result = t;
        this.cacheEntry = entry;
        this.error = null;
    }

    public static <T> Response<T> error(VolleyError volleyError) {
        return new Response<>(volleyError);
    }

    public static <T> Response<T> success(@Nullable T t, @Nullable Cache.Entry entry) {
        return new Response<>(t, entry);
    }

    public boolean isSuccess() {
        return this.error == null;
    }

    private Response(VolleyError volleyError) {
        this.intermediate = false;
        this.result = null;
        this.cacheEntry = null;
        this.error = volleyError;
    }
}
