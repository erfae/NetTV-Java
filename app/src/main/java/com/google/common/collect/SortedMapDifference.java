package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Map;
import java.util.SortedMap;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public interface SortedMapDifference<K, V> extends MapDifference<K, V> {

    /* JADX INFO: renamed from: com.google.common.collect.SortedMapDifference$-CC, reason: invalid class name */
    public final /* synthetic */ class CC<K, V> {
    }

    @Override // com.google.common.collect.MapDifference
    /* bridge */ /* synthetic */ Map entriesDiffering();

    @Override // com.google.common.collect.MapDifference
    SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering();

    @Override // com.google.common.collect.MapDifference
    /* bridge */ /* synthetic */ Map entriesInCommon();

    @Override // com.google.common.collect.MapDifference
    SortedMap<K, V> entriesInCommon();

    @Override // com.google.common.collect.MapDifference
    /* bridge */ /* synthetic */ Map entriesOnlyOnLeft();

    @Override // com.google.common.collect.MapDifference
    SortedMap<K, V> entriesOnlyOnLeft();

    @Override // com.google.common.collect.MapDifference
    /* bridge */ /* synthetic */ Map entriesOnlyOnRight();

    @Override // com.google.common.collect.MapDifference
    SortedMap<K, V> entriesOnlyOnRight();
}
