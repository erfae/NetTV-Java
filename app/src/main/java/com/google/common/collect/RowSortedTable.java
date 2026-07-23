package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public interface RowSortedTable<R, C, V> extends Table<R, C, V> {

    /* JADX INFO: renamed from: com.google.common.collect.RowSortedTable$-CC, reason: invalid class name */
    public final /* synthetic */ class CC<R, C, V> {
    }

    @Override // com.google.common.collect.Table
    /* bridge */ /* synthetic */ Set rowKeySet();

    @Override // com.google.common.collect.Table
    SortedSet<R> rowKeySet();

    @Override // com.google.common.collect.Table
    /* bridge */ /* synthetic */ Map rowMap();

    @Override // com.google.common.collect.Table
    SortedMap<R, Map<C, V>> rowMap();
}
