package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
interface FilteredSetMultimap<K, V> extends FilteredMultimap<K, V>, SetMultimap<K, V> {

    /* JADX INFO: renamed from: com.google.common.collect.FilteredSetMultimap$-CC, reason: invalid class name */
    public final /* synthetic */ class CC<K, V> {
    }

    @Override // com.google.common.collect.FilteredMultimap
    /* bridge */ /* synthetic */ Multimap unfiltered();

    @Override // com.google.common.collect.FilteredMultimap
    SetMultimap<K, V> unfiltered();
}
