package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public interface ListMultimap<K, V> extends Multimap<K, V> {

    /* JADX INFO: renamed from: com.google.common.collect.ListMultimap$-CC, reason: invalid class name */
    public final /* synthetic */ class CC<K, V> {
    }

    Map<K, Collection<V>> asMap();

    boolean equals(@CheckForNull Object obj);

    /* bridge */ /* synthetic */ Collection get(@ParametricNullness Object obj);

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    List<V> get(@ParametricNullness K k);

    @CanIgnoreReturnValue
    /* bridge */ /* synthetic */ Collection removeAll(@CheckForNull Object obj);

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    List<V> removeAll(@CheckForNull Object obj);

    @CanIgnoreReturnValue
    /* bridge */ /* synthetic */ Collection replaceValues(@ParametricNullness Object obj, Iterable iterable);

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    @CanIgnoreReturnValue
    List<V> replaceValues(@ParametricNullness K k, Iterable<? extends V> iterable);
}
