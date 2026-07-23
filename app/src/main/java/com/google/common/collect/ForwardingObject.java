package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

/* JADX INFO: loaded from: classes2.dex */
@GwtCompatible
@ElementTypesAreNonnullByDefault
public abstract class ForwardingObject {
    public abstract Object delegate();

    public String toString() {
        return delegate().toString();
    }
}
