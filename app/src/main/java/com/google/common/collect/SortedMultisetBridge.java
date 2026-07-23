package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import java.util.Set;
import java.util.SortedSet;

/* JADX INFO: loaded from: classes2.dex */
@GwtIncompatible
@ElementTypesAreNonnullByDefault
interface SortedMultisetBridge<E> extends Multiset<E> {

    /* JADX INFO: renamed from: com.google.common.collect.SortedMultisetBridge$-CC, reason: invalid class name */
    public final /* synthetic */ class CC<E> {
    }

    @Override // com.google.common.collect.Multiset
    /* bridge */ /* synthetic */ Set elementSet();

    @Override // com.google.common.collect.Multiset
    SortedSet<E> elementSet();
}
