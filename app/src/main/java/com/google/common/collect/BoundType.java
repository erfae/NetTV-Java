package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public enum BoundType {
    OPEN(false),
    CLOSED(true);

    BoundType(boolean z) {
    }

    public static BoundType forBoolean(boolean z) {
        return z ? CLOSED : OPEN;
    }
}
