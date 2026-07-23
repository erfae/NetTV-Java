package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ImmutableMap;
import java.util.concurrent.ExecutionException;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
@GwtIncompatible
public abstract class ForwardingLoadingCache<K, V> extends ForwardingCache<K, V> implements LoadingCache<K, V> {

    public static abstract class SimpleForwardingLoadingCache<K, V> extends ForwardingLoadingCache<K, V> {
        private final LoadingCache<K, V> delegate;

        @Override // com.google.common.cache.ForwardingLoadingCache, com.google.common.cache.ForwardingCache, com.google.common.collect.ForwardingObject
        public final Cache delegate() {
            return this.delegate;
        }

        @Override // com.google.common.cache.ForwardingLoadingCache, com.google.common.cache.ForwardingCache, com.google.common.collect.ForwardingObject
        public final LoadingCache<K, V> delegate() {
            return this.delegate;
        }

        @Override // com.google.common.cache.ForwardingLoadingCache, com.google.common.cache.ForwardingCache, com.google.common.collect.ForwardingObject
        public final Object delegate() {
            return this.delegate;
        }
    }

    @Override // com.google.common.cache.LoadingCache, com.google.common.base.Function
    public V apply(K k) {
        return delegate().apply(k);
    }

    @Override // com.google.common.cache.ForwardingCache, com.google.common.collect.ForwardingObject
    public abstract LoadingCache<K, V> delegate();

    @Override // com.google.common.cache.LoadingCache
    public V get(K k) throws ExecutionException {
        return delegate().get(k);
    }

    @Override // com.google.common.cache.LoadingCache
    public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
        return delegate().getAll(iterable);
    }

    @Override // com.google.common.cache.LoadingCache
    public V getUnchecked(K k) {
        return delegate().getUnchecked(k);
    }

    @Override // com.google.common.cache.LoadingCache
    public void refresh(K k) {
        delegate().refresh(k);
    }
}
