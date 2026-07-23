package io.realm.internal.objectstore;

import io.realm.RealmModel;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.UnmanagedSubscription;
import io.realm.internal.async.RealmThreadPoolExecutor;
import io.realm.mongodb.sync.MutableSubscriptionSet;
import io.realm.mongodb.sync.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public class OsMutableSubscriptionSet extends OsSubscriptionSet implements MutableSubscriptionSet {
    public OsMutableSubscriptionSet(long j, RealmProxyMediator realmProxyMediator, RealmThreadPoolExecutor realmThreadPoolExecutor, RealmThreadPoolExecutor realmThreadPoolExecutor2) {
        super(j, realmProxyMediator, realmThreadPoolExecutor, realmThreadPoolExecutor2);
    }

    private Subscription addSubscription(Subscription subscription, boolean z) {
        if (!(subscription instanceof UnmanagedSubscription)) {
            throw new IllegalArgumentException("Only unmanaged subscriptions are allowed as input. This subscription was managed.");
        }
        UnmanagedSubscription unmanagedSubscription = (UnmanagedSubscription) subscription;
        return new OsSubscription(nativeInsertOrAssign(getNativePtr(), unmanagedSubscription.getName(), unmanagedSubscription.getQueryPointer(), z));
    }

    private static native long nativeCommit(long j);

    private static native long nativeInsertOrAssign(long j, String str, long j2, boolean z);

    private static native boolean nativeRemove(long j, long j2);

    private static native boolean nativeRemoveAll(long j);

    private static native boolean nativeRemoveAllForType(long j, String str);

    private static native boolean nativeRemoveNamed(long j, String str);

    public Subscription add(Subscription subscription) {
        return addSubscription(subscription, true);
    }

    public Subscription addOrUpdate(Subscription subscription) {
        return addSubscription(subscription, false);
    }

    public long commit() {
        return nativeCommit(getNativePtr());
    }

    public boolean remove(Subscription subscription) {
        if (subscription instanceof OsSubscription) {
            return nativeRemove(getNativePtr(), ((OsSubscription) subscription).getNativePtr());
        }
        throw new IllegalArgumentException("Only managed Subscriptions can be removed.");
    }

    public boolean removeAll(String str) {
        return nativeRemoveAllForType(getNativePtr(), str);
    }

    public <T extends RealmModel> boolean removeAll(Class<T> cls) {
        return nativeRemoveAllForType(getNativePtr(), this.schema.getSimpleClassName(cls));
    }

    public boolean removeAll() {
        return nativeRemoveAll(getNativePtr());
    }

    public boolean remove(String str) {
        return nativeRemoveNamed(getNativePtr(), str);
    }
}
