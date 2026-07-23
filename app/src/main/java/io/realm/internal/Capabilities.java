package io.realm.internal;

import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public interface Capabilities {
    boolean canDeliverNotification();

    void checkCanDeliverNotification(@Nullable String str);

    boolean isMainThread();
}
