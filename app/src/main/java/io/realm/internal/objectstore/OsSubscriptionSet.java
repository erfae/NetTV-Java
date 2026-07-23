package io.realm.internal.objectstore;

import android.os.Handler;
import android.os.Looper;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.RealmAsyncTask;
import io.realm.RealmQuery;
import io.realm.internal.NativeObject;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.async.RealmAsyncTaskImpl;
import io.realm.internal.async.RealmThreadPoolExecutor;
import io.realm.mongodb.sync.Subscription;
import io.realm.mongodb.sync.SubscriptionSet;
import io.realm.mongodb.sync.SubscriptionSet$State;
import io.realm.mongodb.sync.SubscriptionSet$StateChangeCallback;
import io.realm.mongodb.sync.SubscriptionSet$UpdateAsyncCallback;
import io.realm.mongodb.sync.SubscriptionSet$UpdateCallback;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public class OsSubscriptionSet implements NativeObject, SubscriptionSet {
    public static final byte STATE_VALUE_BOOTSTRAPPING = 2;
    public static final byte STATE_VALUE_COMPLETE = 3;
    public static final byte STATE_VALUE_ERROR = 4;
    public static final byte STATE_VALUE_PENDING = 1;
    public static final byte STATE_VALUE_SUPERSEDED = 5;
    public static final byte STATE_VALUE_UNCOMMITTED = 0;
    private static final long nativeFinalizerPtr = nativeGetFinalizerMethodPtr();
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private long nativePtr;
    public final RealmProxyMediator schema;
    private final RealmThreadPoolExecutor stateListenerExecutor;
    private final RealmThreadPoolExecutor updateExecutor;

    public interface StateChangeCallback {
        void onChange(byte b);
    }

    public OsSubscriptionSet(long j, RealmProxyMediator realmProxyMediator, RealmThreadPoolExecutor realmThreadPoolExecutor, RealmThreadPoolExecutor realmThreadPoolExecutor2) {
        this.nativePtr = j;
        this.schema = realmProxyMediator;
        this.stateListenerExecutor = realmThreadPoolExecutor;
        this.updateExecutor = realmThreadPoolExecutor2;
    }

    private static native long nativeCreateMutableSubscriptionSet(long j);

    private static native String nativeErrorMessage(long j);

    private static native long nativeFindByName(long j, String str);

    private static native long nativeFindByQuery(long j, long j2);

    private static native long nativeGetFinalizerMethodPtr();

    private static native void nativeRefresh(long j);

    private static native void nativeRelease(long j);

    private static native long nativeSize(long j);

    private static native byte nativeState(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeSubscriptionAt(long j, int i);

    private static native void nativeWaitForSynchronization(long j, StateChangeCallback stateChangeCallback);

    @Nullable
    public Subscription find(RealmQuery realmQuery) {
        long jNativeFindByQuery = nativeFindByQuery(this.nativePtr, realmQuery.getQueryPointer());
        if (jNativeFindByQuery != -1) {
            return new OsSubscription(jNativeFindByQuery);
        }
        return null;
    }

    public String getErrorMessage() {
        return nativeErrorMessage(this.nativePtr);
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    public SubscriptionSet$State getState() {
        return SubscriptionSet$State.fromNativeValue(nativeState(this.nativePtr));
    }

    public Iterator<Subscription> iterator() {
        return new Iterator<Subscription>() { // from class: io.realm.internal.objectstore.OsSubscriptionSet.4
            private int cursor = 0;
            private final int size;

            {
                this.size = OsSubscriptionSet.this.size();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.cursor < this.size;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Iterator
            public Subscription next() {
                if (this.cursor < this.size) {
                    long jNativeSubscriptionAt = OsSubscriptionSet.nativeSubscriptionAt(OsSubscriptionSet.this.nativePtr, this.cursor);
                    this.cursor++;
                    return new OsSubscription(jNativeSubscriptionAt);
                }
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Iterator has no more elements. Tried index ");
                sbM.append(this.cursor);
                sbM.append(". Size is ");
                throw new NoSuchElementException(Insets$$ExternalSyntheticOutline0.m(sbM, this.size, "."));
            }
        };
    }

    public void refresh() {
        nativeRefresh(this.nativePtr);
    }

    public int size() {
        return (int) nativeSize(this.nativePtr);
    }

    public SubscriptionSet update(SubscriptionSet$UpdateCallback subscriptionSet$UpdateCallback) {
        OsMutableSubscriptionSet osMutableSubscriptionSet = new OsMutableSubscriptionSet(nativeCreateMutableSubscriptionSet(this.nativePtr), this.schema, this.stateListenerExecutor, this.updateExecutor);
        subscriptionSet$UpdateCallback.update(osMutableSubscriptionSet);
        long jCommit = osMutableSubscriptionSet.commit();
        long j = this.nativePtr;
        this.nativePtr = jCommit;
        nativeRelease(j);
        return this;
    }

    public RealmAsyncTask updateAsync(final SubscriptionSet$UpdateAsyncCallback subscriptionSet$UpdateAsyncCallback) {
        return new RealmAsyncTaskImpl(this.updateExecutor.submit(new Runnable() { // from class: io.realm.internal.objectstore.OsSubscriptionSet.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    final SubscriptionSet subscriptionSetUpdate = OsSubscriptionSet.this.update(subscriptionSet$UpdateAsyncCallback);
                    OsSubscriptionSet.this.mainHandler.post(new Runnable() { // from class: io.realm.internal.objectstore.OsSubscriptionSet.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            subscriptionSet$UpdateAsyncCallback.onSuccess(subscriptionSetUpdate);
                        }
                    });
                } catch (Throwable th) {
                    OsSubscriptionSet.this.mainHandler.post(new Runnable() { // from class: io.realm.internal.objectstore.OsSubscriptionSet.3.2
                        @Override // java.lang.Runnable
                        public void run() {
                            subscriptionSet$UpdateAsyncCallback.onError(th);
                        }
                    });
                }
            }
        }), this.updateExecutor);
    }

    public boolean waitForSynchronization() {
        return waitForSynchronization(Long.MAX_VALUE, TimeUnit.SECONDS);
    }

    public RealmAsyncTask waitForSynchronizationAsync(SubscriptionSet$StateChangeCallback subscriptionSet$StateChangeCallback) {
        return waitForSynchronizationAsync(Long.MAX_VALUE, TimeUnit.SECONDS, subscriptionSet$StateChangeCallback);
    }

    public boolean waitForSynchronization(Long l, TimeUnit timeUnit) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        nativeWaitForSynchronization(this.nativePtr, new StateChangeCallback() { // from class: io.realm.internal.objectstore.OsSubscriptionSet.1
            @Override // io.realm.internal.objectstore.OsSubscriptionSet.StateChangeCallback
            public void onChange(byte b) {
                atomicBoolean.set(SubscriptionSet$State.fromNativeValue((long) b) == SubscriptionSet$State.COMPLETE);
                countDownLatch.countDown();
            }
        });
        try {
            if (!countDownLatch.await(l.longValue(), timeUnit)) {
                throw new RuntimeException("Waiting for waitForSynchronization() timed out.");
            }
            refresh();
            return atomicBoolean.get();
        } catch (InterruptedException unused) {
            throw new RuntimeException("Waiting for waitForSynchronization() was interrupted.");
        }
    }

    public RealmAsyncTask waitForSynchronizationAsync(final Long l, final TimeUnit timeUnit, final SubscriptionSet$StateChangeCallback subscriptionSet$StateChangeCallback) {
        return new RealmAsyncTaskImpl(this.stateListenerExecutor.submit(new Runnable() { // from class: io.realm.internal.objectstore.OsSubscriptionSet.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    OsSubscriptionSet.this.waitForSynchronization(l, timeUnit);
                    OsSubscriptionSet.this.mainHandler.post(new Runnable() { // from class: io.realm.internal.objectstore.OsSubscriptionSet.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                            subscriptionSet$StateChangeCallback.onStateChange(OsSubscriptionSet.this);
                        }
                    });
                } catch (Exception e) {
                    OsSubscriptionSet.this.mainHandler.post(new Runnable() { // from class: io.realm.internal.objectstore.OsSubscriptionSet.2.2
                        @Override // java.lang.Runnable
                        public void run() {
                            subscriptionSet$StateChangeCallback.onError(e);
                        }
                    });
                }
            }
        }), this.stateListenerExecutor);
    }

    @Nullable
    public Subscription find(String str) {
        long jNativeFindByName = nativeFindByName(this.nativePtr, str);
        if (jNativeFindByName != -1) {
            return new OsSubscription(jNativeFindByName);
        }
        return null;
    }
}
