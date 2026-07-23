package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.exceptions.RealmFileException;
import io.realm.internal.ObjectServerFacade;
import io.realm.internal.OsObjectStore;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.RealmNotifier;
import io.realm.internal.Util;
import io.realm.internal.android.AndroidCapabilities;
import io.realm.internal.android.AndroidRealmNotifier;
import io.realm.internal.async.RealmAsyncTaskImpl;
import io.realm.internal.async.RealmThreadPoolExecutor;
import io.realm.internal.util.Pair;
import io.realm.log.RealmLog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes2.dex */
final class RealmCache {
    private static final String ASYNC_CALLBACK_NULL_MSG = "The callback cannot be null.";
    private static final String ASYNC_NOT_ALLOWED_MSG = "Realm instances cannot be loaded asynchronously on a non-looper thread.";
    private static final String DIFFERENT_KEY_MESSAGE = "Wrong key used to decrypt Realm.";
    private static final String WRONG_REALM_CLASS_MESSAGE = "The type of Realm class must be Realm or DynamicRealm.";
    private static final List<WeakReference<RealmCache>> cachesList = new ArrayList();
    private static final Collection<RealmCache> leakedCaches = new ConcurrentLinkedQueue();
    private RealmConfiguration configuration;
    private final String realmPath;
    private final Map<Pair<RealmCacheType, OsSharedRealm.VersionID>, ReferenceCounter> refAndCountMap = new HashMap();
    private final AtomicBoolean isLeaked = new AtomicBoolean(false);
    private final Set<String> pendingRealmFileCreation = new HashSet();

    public interface Callback {
        void onResult(int i);
    }

    public interface Callback0 {
        void onCall();
    }

    public static class CreateRealmRunnable<T extends BaseRealm> implements Runnable {
        private final BaseRealm.InstanceCallback<T> callback;
        private final CountDownLatch canReleaseBackgroundInstanceLatch = new CountDownLatch(1);
        private final RealmConfiguration configuration;
        private Future future;
        private final RealmNotifier notifier;
        private final Class<T> realmClass;

        public CreateRealmRunnable(RealmNotifier realmNotifier, RealmConfiguration realmConfiguration, BaseRealm.InstanceCallback<T> instanceCallback, Class<T> cls) {
            this.configuration = realmConfiguration;
            this.realmClass = cls;
            this.callback = instanceCallback;
            this.notifier = realmNotifier;
        }

        @Override // java.lang.Runnable
        public void run() {
            BaseRealm baseRealmCreateRealmOrGetFromCache = null;
            try {
                try {
                    baseRealmCreateRealmOrGetFromCache = RealmCache.createRealmOrGetFromCache(this.configuration, this.realmClass);
                    if (!this.notifier.post(new Runnable() { // from class: io.realm.RealmCache.CreateRealmRunnable.1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.lang.Runnable
                        public void run() {
                            if (CreateRealmRunnable.this.future == null || CreateRealmRunnable.this.future.isCancelled()) {
                                CreateRealmRunnable.this.canReleaseBackgroundInstanceLatch.countDown();
                                return;
                            }
                            BaseRealm baseRealm = null;
                            try {
                                BaseRealm baseRealmCreateRealmOrGetFromCache2 = RealmCache.createRealmOrGetFromCache(CreateRealmRunnable.this.configuration, CreateRealmRunnable.this.realmClass);
                                CreateRealmRunnable.this.canReleaseBackgroundInstanceLatch.countDown();
                                th = null;
                                baseRealm = baseRealmCreateRealmOrGetFromCache2;
                            } catch (Throwable th) {
                                th = th;
                                CreateRealmRunnable.this.canReleaseBackgroundInstanceLatch.countDown();
                            }
                            if (baseRealm != null) {
                                CreateRealmRunnable.this.callback.onSuccess(baseRealm);
                            } else {
                                CreateRealmRunnable.this.callback.onError(th);
                            }
                        }
                    })) {
                        this.canReleaseBackgroundInstanceLatch.countDown();
                    }
                    if (!this.canReleaseBackgroundInstanceLatch.await(2L, TimeUnit.SECONDS)) {
                        RealmLog.warn("Timeout for creating Realm instance in foreground thread in `CreateRealmRunnable` ", new Object[0]);
                    }
                    if (baseRealmCreateRealmOrGetFromCache == null) {
                        return;
                    }
                } catch (InterruptedException e) {
                    RealmLog.warn(e, "`CreateRealmRunnable` has been interrupted.", new Object[0]);
                    if (baseRealmCreateRealmOrGetFromCache == null) {
                        return;
                    }
                } catch (Throwable th) {
                    if (!ObjectServerFacade.getSyncFacadeIfPossible().wasDownloadInterrupted(th)) {
                        RealmLog.error(th, "`CreateRealmRunnable` failed.", new Object[0]);
                        this.notifier.post(new Runnable() { // from class: io.realm.RealmCache.CreateRealmRunnable.2
                            @Override // java.lang.Runnable
                            public void run() {
                                CreateRealmRunnable.this.callback.onError(th);
                            }
                        });
                    }
                    if (baseRealmCreateRealmOrGetFromCache == null) {
                        return;
                    }
                }
                baseRealmCreateRealmOrGetFromCache.close();
            } catch (Throwable th2) {
                if (0 != 0) {
                    baseRealmCreateRealmOrGetFromCache.close();
                }
                throw th2;
            }
        }

        public void setFuture(Future future) {
            this.future = future;
        }
    }

    public static class GlobalReferenceCounter extends ReferenceCounter {
        private BaseRealm cachedRealm;

        private GlobalReferenceCounter() {
            super();
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public void clearThreadLocalCache() {
            String path = this.cachedRealm.getPath();
            this.localCount.set(null);
            this.cachedRealm = null;
            if (this.globalCount.decrementAndGet() < 0) {
                throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m("Global reference counter of Realm", path, " not be negative."));
            }
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public final BaseRealm getRealmInstance() {
            return this.cachedRealm;
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public final int getThreadLocalCount() {
            return this.globalCount.get();
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public final boolean hasInstanceAvailableForThread() {
            return this.cachedRealm != null;
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public final void onRealmCreated(BaseRealm baseRealm) {
            this.cachedRealm = baseRealm;
            this.localCount.set(0);
            this.globalCount.incrementAndGet();
        }
    }

    public enum RealmCacheType {
        TYPED_REALM,
        DYNAMIC_REALM
    }

    public static abstract class ReferenceCounter {
        public AtomicInteger globalCount;
        public final ThreadLocal<Integer> localCount;

        private ReferenceCounter() {
            this.localCount = new ThreadLocal<>();
            this.globalCount = new AtomicInteger(0);
        }

        public abstract void clearThreadLocalCache();

        public int getGlobalCount() {
            return this.globalCount.get();
        }

        public abstract BaseRealm getRealmInstance();

        public abstract int getThreadLocalCount();

        public abstract boolean hasInstanceAvailableForThread();

        public void incrementThreadCount(int i) {
            Integer num = this.localCount.get();
            ThreadLocal<Integer> threadLocal = this.localCount;
            if (num != null) {
                i += num.intValue();
            }
            threadLocal.set(Integer.valueOf(i));
        }

        public abstract void onRealmCreated(BaseRealm baseRealm);

        public void setThreadCount(int i) {
            this.localCount.set(Integer.valueOf(i));
        }
    }

    public static class ThreadConfinedReferenceCounter extends ReferenceCounter {
        private final ThreadLocal<BaseRealm> localRealm;

        private ThreadConfinedReferenceCounter() {
            super();
            this.localRealm = new ThreadLocal<>();
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public void clearThreadLocalCache() {
            String path = this.localRealm.get().getPath();
            this.localCount.set(null);
            this.localRealm.set(null);
            if (this.globalCount.decrementAndGet() < 0) {
                throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m("Global reference counter of Realm", path, " can not be negative."));
            }
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public BaseRealm getRealmInstance() {
            return this.localRealm.get();
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public int getThreadLocalCount() {
            Integer num = this.localCount.get();
            if (num != null) {
                return num.intValue();
            }
            return 0;
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public boolean hasInstanceAvailableForThread() {
            return this.localRealm.get() != null;
        }

        @Override // io.realm.RealmCache.ReferenceCounter
        public void onRealmCreated(BaseRealm baseRealm) {
            this.localRealm.set(baseRealm);
            this.localCount.set(0);
            this.globalCount.incrementAndGet();
        }
    }

    private RealmCache(String str) {
        this.realmPath = str;
    }

    private static void copyAssetFileIfNeeded(final RealmConfiguration realmConfiguration) {
        final File file = realmConfiguration.hasAssetFile() ? new File(realmConfiguration.getRealmDirectory(), realmConfiguration.getRealmFileName()) : null;
        final String syncServerCertificateAssetName = ObjectServerFacade.getFacade(false).getSyncServerCertificateAssetName(realmConfiguration);
        final boolean z = !Util.isEmptyString(syncServerCertificateAssetName);
        if (file != null || z) {
            OsObjectStore.callWithLock(realmConfiguration, new Runnable() { // from class: io.realm.RealmCache.1
                @Override // java.lang.Runnable
                public void run() throws Throwable {
                    if (file != null) {
                        RealmCache.copyFileIfNeeded(realmConfiguration.getAssetFilePath(), file);
                    }
                    if (z) {
                        Objects.requireNonNull(realmConfiguration);
                        RealmCache.copyFileIfNeeded(syncServerCertificateAssetName, new File(ObjectServerFacade.getFacade(false).getSyncServerCertificateFilePath(realmConfiguration)));
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:56:0x008e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:62:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:70:? A[SYNTHETIC] */
    public static void copyFileIfNeeded(String str, File file) throws Throwable {
        FileOutputStream fileOutputStream;
        if (file.exists()) {
            return;
        }
        InputStream inputStream = null;
        e = null;
        inputStream = null;
        try {
            InputStream inputStreamOpen = BaseRealm.applicationContext.getAssets().open(str);
            try {
                if (inputStreamOpen == null) {
                    throw new RealmFileException(RealmFileException.Kind.ACCESS_ERROR, "Invalid input stream to the asset file: " + str);
                }
                fileOutputStream = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int i = inputStreamOpen.read(bArr);
                        if (i > -1) {
                            fileOutputStream.write(bArr, 0, i);
                        } else {
                            try {
                                break;
                            } catch (IOException e) {
                                e = e;
                            }
                        }
                    }
                    inputStreamOpen.close();
                    try {
                        fileOutputStream.close();
                    } catch (IOException e2) {
                        if (e == null) {
                            e = e2;
                        }
                    }
                    if (e != null) {
                        throw new RealmFileException(RealmFileException.Kind.ACCESS_ERROR, e);
                    }
                } catch (IOException e3) {
                    e = e3;
                    inputStream = inputStreamOpen;
                    try {
                        throw new RealmFileException(RealmFileException.Kind.ACCESS_ERROR, "Could not resolve the path to the asset file: " + str, e);
                    } catch (Throwable th) {
                        th = th;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException unused) {
                            }
                        }
                        if (fileOutputStream != null) {
                            throw th;
                        }
                        try {
                            fileOutputStream.close();
                            throw th;
                        } catch (IOException unused2) {
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = inputStreamOpen;
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (fileOutputStream != null) {
                        throw th;
                    }
                    fileOutputStream.close();
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
                fileOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
            }
        } catch (IOException e5) {
            e = e5;
            fileOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
        }
    }

    private <E extends BaseRealm> void createInstance(Class<E> cls, ReferenceCounter referenceCounter, OsSharedRealm.VersionID versionID) {
        BaseRealm baseRealmCreateInstance;
        if (cls == Realm.class) {
            baseRealmCreateInstance = Realm.createInstance(this, versionID);
            baseRealmCreateInstance.getSchema().createKeyPathMapping();
        } else {
            if (cls != DynamicRealm.class) {
                throw new IllegalArgumentException(WRONG_REALM_CLASS_MESSAGE);
            }
            baseRealmCreateInstance = DynamicRealm.createInstance(this, versionID);
        }
        referenceCounter.onRealmCreated(baseRealmCreateInstance);
    }

    public static <E extends BaseRealm> E createRealmOrGetFromCache(RealmConfiguration realmConfiguration, Class<E> cls) {
        return (E) getCache(realmConfiguration.getPath(), true).doCreateRealmOrGetFromCache(realmConfiguration, cls, OsSharedRealm.VersionID.LIVE);
    }

    public static <T extends BaseRealm> RealmAsyncTask createRealmOrGetFromCacheAsync(RealmConfiguration realmConfiguration, BaseRealm.InstanceCallback<T> instanceCallback, Class<T> cls) {
        return getCache(realmConfiguration.getPath(), true).doCreateRealmOrGetFromCacheAsync(realmConfiguration, instanceCallback, cls);
    }

    private synchronized <E extends BaseRealm> E doCreateRealmOrGetFromCache(RealmConfiguration realmConfiguration, Class<E> cls, OsSharedRealm.VersionID versionID) {
        E e;
        ReferenceCounter refCounter = getRefCounter(cls, versionID);
        boolean z = getTotalGlobalRefCount() == 0;
        if (z) {
            copyAssetFileIfNeeded(realmConfiguration);
            realmConfiguration.realmExists();
            this.configuration = realmConfiguration;
        } else {
            validateConfiguration(realmConfiguration);
        }
        if (!refCounter.hasInstanceAvailableForThread()) {
            createInstance(cls, refCounter, versionID);
        }
        refCounter.incrementThreadCount(1);
        e = (E) refCounter.getRealmInstance();
        if (z) {
            ObjectServerFacade.getSyncFacadeIfPossible().downloadInitialFlexibleSyncData(Realm.createInstance(e.sharedRealm), realmConfiguration);
            if (!realmConfiguration.isReadOnly()) {
                e.refresh();
            }
        }
        return e;
    }

    private synchronized <T extends BaseRealm> RealmAsyncTask doCreateRealmOrGetFromCacheAsync(RealmConfiguration realmConfiguration, BaseRealm.InstanceCallback<T> instanceCallback, Class<T> cls) {
        RealmThreadPoolExecutor realmThreadPoolExecutor;
        Future<?> futureSubmitTransaction;
        AndroidCapabilities androidCapabilities = new AndroidCapabilities();
        androidCapabilities.checkCanDeliverNotification(ASYNC_NOT_ALLOWED_MSG);
        if (instanceCallback == null) {
            throw new IllegalArgumentException(ASYNC_CALLBACK_NULL_MSG);
        }
        Objects.requireNonNull(realmConfiguration);
        CreateRealmRunnable createRealmRunnable = new CreateRealmRunnable(new AndroidRealmNotifier(null, androidCapabilities), realmConfiguration, instanceCallback, cls);
        realmThreadPoolExecutor = BaseRealm.asyncTaskExecutor;
        futureSubmitTransaction = realmThreadPoolExecutor.submitTransaction(createRealmRunnable);
        createRealmRunnable.setFuture(futureSubmitTransaction);
        ObjectServerFacade.getSyncFacadeIfPossible().createNativeSyncSession(realmConfiguration);
        return new RealmAsyncTaskImpl(futureSubmitTransaction, realmThreadPoolExecutor);
    }

    private synchronized void doInvokeWithGlobalRefCount(Callback callback) {
        callback.onResult(getTotalGlobalRefCount());
    }

    private static RealmCache getCache(String str, boolean z) {
        RealmCache realmCache;
        List<WeakReference<RealmCache>> list = cachesList;
        synchronized (list) {
            Iterator<WeakReference<RealmCache>> it = list.iterator();
            realmCache = null;
            while (it.hasNext()) {
                RealmCache realmCache2 = it.next().get();
                if (realmCache2 == null) {
                    it.remove();
                } else if (realmCache2.realmPath.equals(str)) {
                    realmCache = realmCache2;
                }
            }
            if (realmCache == null && z) {
                realmCache = new RealmCache(str);
                cachesList.add(new WeakReference<>(realmCache));
            }
        }
        return realmCache;
    }

    public static int getLocalThreadCount(RealmConfiguration realmConfiguration) {
        int threadLocalCount = 0;
        RealmCache cache = getCache(realmConfiguration.getPath(), false);
        if (cache == null) {
            return 0;
        }
        Iterator<ReferenceCounter> it = cache.refAndCountMap.values().iterator();
        while (it.hasNext()) {
            threadLocalCount += it.next().getThreadLocalCount();
        }
        return threadLocalCount;
    }

    private <E extends BaseRealm> ReferenceCounter getRefCounter(Class<E> cls, OsSharedRealm.VersionID versionID) {
        RealmCacheType realmCacheType;
        RealmCacheType realmCacheType2 = RealmCacheType.TYPED_REALM;
        if (cls == Realm.class) {
            realmCacheType = RealmCacheType.TYPED_REALM;
        } else {
            if (cls != DynamicRealm.class) {
                throw new IllegalArgumentException(WRONG_REALM_CLASS_MESSAGE);
            }
            realmCacheType = RealmCacheType.DYNAMIC_REALM;
        }
        Pair<RealmCacheType, OsSharedRealm.VersionID> pair = new Pair<>(realmCacheType, versionID);
        ReferenceCounter threadConfinedReferenceCounter = this.refAndCountMap.get(pair);
        if (threadConfinedReferenceCounter == null) {
            boolean zEquals = versionID.equals(OsSharedRealm.VersionID.LIVE);
            threadConfinedReferenceCounter = zEquals ? new ThreadConfinedReferenceCounter() : new GlobalReferenceCounter();
            this.refAndCountMap.put(pair, threadConfinedReferenceCounter);
        }
        return threadConfinedReferenceCounter;
    }

    private int getTotalGlobalRefCount() {
        Iterator<ReferenceCounter> it = this.refAndCountMap.values().iterator();
        int globalCount = 0;
        while (it.hasNext()) {
            globalCount += it.next().getGlobalCount();
        }
        return globalCount;
    }

    private int getTotalLiveRealmGlobalRefCount() {
        int globalCount = 0;
        for (ReferenceCounter referenceCounter : this.refAndCountMap.values()) {
            if (referenceCounter instanceof ThreadConfinedReferenceCounter) {
                globalCount += referenceCounter.getGlobalCount();
            }
        }
        return globalCount;
    }

    public static void invokeWithGlobalRefCount(RealmConfiguration realmConfiguration, Callback callback) {
        synchronized (cachesList) {
            RealmCache cache = getCache(realmConfiguration.getPath(), false);
            if (cache == null) {
                callback.onResult(0);
            } else {
                cache.doInvokeWithGlobalRefCount(callback);
            }
        }
    }

    private void validateConfiguration(RealmConfiguration realmConfiguration) {
        if (this.configuration.equals(realmConfiguration)) {
            return;
        }
        if (!Arrays.equals(this.configuration.getEncryptionKey(), realmConfiguration.getEncryptionKey())) {
            throw new IllegalArgumentException(DIFFERENT_KEY_MESSAGE);
        }
        RealmMigration migration = realmConfiguration.getMigration();
        RealmMigration migration2 = this.configuration.getMigration();
        if (migration2 != null && migration != null && migration2.getClass().equals(migration.getClass()) && !migration.equals(migration2)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Configurations cannot be different if used to open the same file. The most likely cause is that equals() and hashCode() are not overridden in the migration class: ");
            sbM.append(realmConfiguration.getMigration().getClass().getCanonicalName());
            throw new IllegalArgumentException(sbM.toString());
        }
        StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("Configurations cannot be different if used to open the same file. \nCached configuration: \n");
        sbM2.append(this.configuration);
        sbM2.append("\n\nNew configuration: \n");
        sbM2.append(realmConfiguration);
        throw new IllegalArgumentException(sbM2.toString());
    }

    public RealmConfiguration getConfiguration() {
        return this.configuration;
    }

    public final void leak() {
        if (this.isLeaked.getAndSet(true)) {
            return;
        }
        leakedCaches.add(this);
    }

    public final synchronized void release(BaseRealm baseRealm) {
        BaseRealm realmInstance;
        String path = baseRealm.getPath();
        ReferenceCounter refCounter = getRefCounter(baseRealm.getClass(), baseRealm.isFrozen() ? baseRealm.sharedRealm.getVersionID() : OsSharedRealm.VersionID.LIVE);
        int threadLocalCount = refCounter.getThreadLocalCount();
        if (threadLocalCount <= 0) {
            RealmLog.warn("%s has been closed already. refCount is %s", path, Integer.valueOf(threadLocalCount));
            return;
        }
        int i = threadLocalCount - 1;
        if (i == 0) {
            refCounter.clearThreadLocalCache();
            baseRealm.doClose();
            if (getTotalLiveRealmGlobalRefCount() == 0) {
                this.configuration = null;
                for (ReferenceCounter referenceCounter : this.refAndCountMap.values()) {
                    if ((referenceCounter instanceof GlobalReferenceCounter) && (realmInstance = referenceCounter.getRealmInstance()) != null) {
                        while (!realmInstance.isClosed()) {
                            realmInstance.close();
                        }
                    }
                }
                Objects.requireNonNull(baseRealm.getConfiguration());
                ObjectServerFacade.getFacade(false).realmClosed(baseRealm.getConfiguration());
            }
        } else {
            refCounter.setThreadCount(i);
        }
    }

    public static <E extends BaseRealm> E createRealmOrGetFromCache(RealmConfiguration realmConfiguration, Class<E> cls, OsSharedRealm.VersionID versionID) {
        return (E) getCache(realmConfiguration.getPath(), true).doCreateRealmOrGetFromCache(realmConfiguration, cls, versionID);
    }
}
