package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public interface BiMap<K, V> extends Map<K, V> {

    /* JADX INFO: renamed from: com.google.common.collect.BiMap$-CC, reason: invalid class name */
    public final /* synthetic */ class CC<K, V> {
    }

    @CanIgnoreReturnValue
    @CheckForNull
    V forcePut(@ParametricNullness K k, @ParametricNullness V v);

    BiMap<V, K> inverse();

    @CanIgnoreReturnValue
    @CheckForNull
    V put(@ParametricNullness K k, @ParametricNullness V v);

    void putAll(Map<? extends K, ? extends V> map);

    /* bridge */ /* synthetic */ Collection values();

    @Override // java.util.Map
    Set<V> values();
}
