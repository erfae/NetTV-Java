package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible(serializable = true)
@ElementTypesAreNonnullByDefault
class EmptyImmutableListMultimap extends ImmutableListMultimap<Object, Object> {
    public static final EmptyImmutableListMultimap INSTANCE = new EmptyImmutableListMultimap();
    private static final long serialVersionUID = 0;

    private EmptyImmutableListMultimap() {
        super(ImmutableMap.of(), 0);
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
