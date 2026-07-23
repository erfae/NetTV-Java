package io.realm.mongodb.sync;

import io.realm.internal.Keep;

/* JADX INFO: loaded from: classes2.dex */
@Keep
public interface SubscriptionSet$StateChangeCallback {
    void onError(Throwable th);

    void onStateChange(SubscriptionSet subscriptionSet);
}
