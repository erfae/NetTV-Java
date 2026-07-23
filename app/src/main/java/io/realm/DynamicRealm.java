package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.reactivex.Flowable;
import io.realm.exceptions.RealmException;
import io.realm.internal.CheckedRow;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectStore;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.RealmNotifier;
import io.realm.internal.Table;
import io.realm.internal.Util;
import io.realm.internal.async.RealmAsyncTaskImpl;
import io.realm.internal.async.RealmThreadPoolExecutor;
import io.realm.log.RealmLog;
import java.io.File;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public class DynamicRealm extends BaseRealm {
    private final RealmSchema schema;

    /* JADX INFO: renamed from: io.realm.DynamicRealm$2, reason: invalid class name */
    public class AnonymousClass2 implements Runnable {
        public final /* synthetic */ boolean val$canDeliverNotification;
        public final /* synthetic */ Transaction.OnError val$onError;
        public final /* synthetic */ Transaction.OnSuccess val$onSuccess;
        public final /* synthetic */ RealmConfiguration val$realmConfiguration;
        public final /* synthetic */ RealmNotifier val$realmNotifier;
        public final /* synthetic */ Transaction val$transaction;

        public AnonymousClass2(RealmConfiguration realmConfiguration, Transaction transaction, boolean z, Transaction.OnSuccess onSuccess, RealmNotifier realmNotifier, Transaction.OnError onError) {
            this.val$realmConfiguration = realmConfiguration;
            this.val$transaction = transaction;
            this.val$canDeliverNotification = z;
            this.val$onSuccess = onSuccess;
            this.val$realmNotifier = realmNotifier;
            this.val$onError = onError;
        }

        @Override // java.lang.Runnable
        public void run() {
            final OsSharedRealm.VersionID versionID;
            if (Thread.currentThread().isInterrupted()) {
                return;
            }
            DynamicRealm dynamicRealm = DynamicRealm.getInstance(this.val$realmConfiguration);
            dynamicRealm.beginTransaction();
            final Throwable th = null;
            try {
                this.val$transaction.execute(dynamicRealm);
                if (Thread.currentThread().isInterrupted()) {
                    try {
                        if (dynamicRealm.isInTransaction()) {
                            dynamicRealm.cancelTransaction();
                        }
                        return;
                    } finally {
                        dynamicRealm.close();
                    }
                }
                dynamicRealm.commitTransaction();
                versionID = dynamicRealm.sharedRealm.getVersionID();
                try {
                    if (dynamicRealm.isInTransaction()) {
                        dynamicRealm.cancelTransaction();
                    }
                    dynamicRealm.close();
                } catch (Throwable th2) {
                    dynamicRealm.close();
                    throw th2;
                }
            } catch (Throwable th3) {
                try {
                    if (dynamicRealm.isInTransaction()) {
                        dynamicRealm.cancelTransaction();
                    }
                    dynamicRealm.close();
                    versionID = null;
                    th = th3;
                } catch (Throwable th4) {
                    dynamicRealm.close();
                    throw th4;
                }
            }
            if (!this.val$canDeliverNotification) {
                if (th != null) {
                    throw new RealmException("Async transaction failed", th);
                }
            } else if (versionID != null && this.val$onSuccess != null) {
                this.val$realmNotifier.post(new Runnable() { // from class: io.realm.DynamicRealm.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (DynamicRealm.this.isClosed()) {
                            AnonymousClass2.this.val$onSuccess.onSuccess();
                        } else if (DynamicRealm.this.sharedRealm.getVersionID().compareTo(versionID) < 0) {
                            DynamicRealm.this.sharedRealm.realmNotifier.addTransactionCallback(new Runnable() { // from class: io.realm.DynamicRealm.2.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass2.this.val$onSuccess.onSuccess();
                                }
                            });
                        } else {
                            AnonymousClass2.this.val$onSuccess.onSuccess();
                        }
                    }
                });
            } else if (th != null) {
                this.val$realmNotifier.post(new Runnable() { // from class: io.realm.DynamicRealm.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Transaction.OnError onError = AnonymousClass2.this.val$onError;
                        if (onError == null) {
                            throw new RealmException("Async transaction failed", th);
                        }
                        onError.onError(th);
                    }
                });
            }
        }
    }

    public static abstract class Callback extends BaseRealm.InstanceCallback<DynamicRealm> {
        @Override // io.realm.BaseRealm.InstanceCallback
        public void onError(Throwable th) {
            super.onError(th);
        }

        @Override // io.realm.BaseRealm.InstanceCallback
        public abstract void onSuccess(DynamicRealm dynamicRealm);
    }

    public interface Transaction {

        public static class Callback {
            public void onError(Exception exc) {
            }

            public void onSuccess() {
            }
        }

        public interface OnError {
            void onError(Throwable th);
        }

        public interface OnSuccess {
            void onSuccess();
        }

        void execute(DynamicRealm dynamicRealm);
    }

    private DynamicRealm(final RealmCache realmCache, OsSharedRealm.VersionID versionID) {
        super(realmCache, null, versionID);
        RealmCache.invokeWithGlobalRefCount(realmCache.getConfiguration(), new RealmCache.Callback() { // from class: io.realm.DynamicRealm.1
            @Override // io.realm.RealmCache.Callback
            public void onResult(int i) {
                if (i <= 0 && !realmCache.getConfiguration().isReadOnly() && OsObjectStore.getSchemaVersion(DynamicRealm.this.sharedRealm) == -1) {
                    DynamicRealm.this.sharedRealm.beginTransaction();
                    if (OsObjectStore.getSchemaVersion(DynamicRealm.this.sharedRealm) == -1) {
                        OsObjectStore.setSchemaVersion(DynamicRealm.this.sharedRealm, -1L);
                    }
                    DynamicRealm.this.sharedRealm.commitTransaction();
                }
            }
        });
        this.schema = new MutableRealmSchema(this);
    }

    public static DynamicRealm createInstance(RealmCache realmCache, OsSharedRealm.VersionID versionID) {
        return new DynamicRealm(realmCache, versionID);
    }

    public static DynamicRealm getInstance(RealmConfiguration realmConfiguration) {
        if (realmConfiguration != null) {
            return (DynamicRealm) RealmCache.createRealmOrGetFromCache(realmConfiguration, DynamicRealm.class);
        }
        throw new IllegalArgumentException("A non-null RealmConfiguration must be provided");
    }

    public static RealmAsyncTask getInstanceAsync(RealmConfiguration realmConfiguration, Callback callback) {
        if (realmConfiguration != null) {
            return RealmCache.createRealmOrGetFromCacheAsync(realmConfiguration, callback, DynamicRealm.class);
        }
        throw new IllegalArgumentException("A non-null RealmConfiguration must be provided");
    }

    public void addChangeListener(RealmChangeListener<DynamicRealm> realmChangeListener) {
        addListener(realmChangeListener);
    }

    @Override // io.realm.BaseRealm
    public Flowable<DynamicRealm> asFlowable() {
        return this.configuration.getRxFactory().from(this);
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void beginTransaction() {
        super.beginTransaction();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void cancelTransaction() {
        super.cancelTransaction();
    }

    @Override // io.realm.BaseRealm, java.io.Closeable, java.lang.AutoCloseable
    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void commitTransaction() {
        super.commitTransaction();
    }

    public DynamicRealmObject createEmbeddedObject(String str, DynamicRealmObject dynamicRealmObject, String str2) {
        checkIfValid();
        Util.checkNull(dynamicRealmObject, "parentObject");
        Util.checkEmpty(str2, "parentProperty");
        if (!RealmObject.isManaged(dynamicRealmObject) || !RealmObject.isValid(dynamicRealmObject)) {
            throw new IllegalArgumentException("Only valid, managed objects can be a parent to an embedded object.");
        }
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.sharedRealm, str);
        if (primaryKeyForObject != null) {
            throw new RealmException(String.format(Locale.US, "'%s' has a primary key field '%s', embedded objects cannot have primary keys.", str, primaryKeyForObject));
        }
        String type = dynamicRealmObject.getType();
        RealmObjectSchema realmObjectSchema = this.schema.get(type);
        if (realmObjectSchema != null) {
            return new DynamicRealmObject(this, getEmbeddedObjectRow(str, dynamicRealmObject, str2, this.schema, realmObjectSchema));
        }
        throw new IllegalStateException(String.format("No schema found for '%s'.", type));
    }

    public DynamicRealmObject createObject(String str) {
        checkIfValid();
        Table table = this.schema.getTable(str);
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.sharedRealm, str);
        if (primaryKeyForObject == null) {
            return new DynamicRealmObject(this, CheckedRow.getFromRow(OsObject.create(table)));
        }
        throw new RealmException(String.format(Locale.US, "'%s' has a primary key field '%s', use  'createObject(String, Object)' instead.", str, primaryKeyForObject));
    }

    public void delete(String str) {
        checkIfValid();
        if (!this.sharedRealm.isInTransaction()) {
            throw new IllegalStateException("Changing Realm data can only be done from inside a transaction.");
        }
        this.schema.getTable(str).clear();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void deleteAll() {
        super.deleteAll();
    }

    public void executeTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction should not be null");
        }
        checkAllowWritesOnUiThread();
        beginTransaction();
        try {
            transaction.execute(this);
            commitTransaction();
        } catch (RuntimeException e) {
            if (isInTransaction()) {
                cancelTransaction();
            } else {
                RealmLog.warn("Could not cancel transaction, not currently in a transaction.", new Object[0]);
            }
            throw e;
        }
    }

    public RealmAsyncTask executeTransactionAsync(Transaction transaction) {
        return executeTransactionAsync(transaction, null, null);
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ RealmConfiguration getConfiguration() {
        return super.getConfiguration();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ long getNumberOfActiveVersions() {
        return super.getNumberOfActiveVersions();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ String getPath() {
        return super.getPath();
    }

    @Override // io.realm.BaseRealm
    public RealmSchema getSchema() {
        return this.schema;
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ long getVersion() {
        return super.getVersion();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ boolean isAutoRefresh() {
        return super.isAutoRefresh();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ boolean isClosed() {
        return super.isClosed();
    }

    @Override // io.realm.BaseRealm
    public boolean isEmpty() {
        checkIfValid();
        return this.sharedRealm.isEmpty();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ boolean isFrozen() {
        return super.isFrozen();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ boolean isInTransaction() {
        return super.isInTransaction();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void refresh() {
        super.refresh();
    }

    public void removeAllChangeListeners() {
        removeAllListeners();
    }

    public void removeChangeListener(RealmChangeListener<DynamicRealm> realmChangeListener) {
        removeListener(realmChangeListener);
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void setAutoRefresh(boolean z) {
        super.setAutoRefresh(z);
    }

    @Override // io.realm.BaseRealm
    @Deprecated
    public /* bridge */ /* synthetic */ void stopWaitForChange() {
        super.stopWaitForChange();
    }

    @Override // io.realm.BaseRealm
    @Deprecated
    public /* bridge */ /* synthetic */ boolean waitForChange() {
        return super.waitForChange();
    }

    public RealmQuery<DynamicRealmObject> where(String str) {
        checkIfValid();
        if (this.sharedRealm.hasTable(Table.getTableNameForClass(str))) {
            return RealmQuery.createDynamicQuery(this, str);
        }
        throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Class does not exist in the Realm and cannot be queried: ", str));
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void writeCopyTo(File file) {
        super.writeCopyTo(file);
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void writeEncryptedCopyTo(File file, byte[] bArr) {
        super.writeEncryptedCopyTo(file, bArr);
    }

    public static DynamicRealm createInstance(OsSharedRealm osSharedRealm) {
        return new DynamicRealm(osSharedRealm);
    }

    public RealmAsyncTask executeTransactionAsync(Transaction transaction, Transaction.OnSuccess onSuccess) {
        if (onSuccess != null) {
            return executeTransactionAsync(transaction, onSuccess, null);
        }
        throw new IllegalArgumentException("onSuccess callback can't be null");
    }

    @Override // io.realm.BaseRealm
    public DynamicRealm freeze() {
        OsSharedRealm.VersionID versionID;
        try {
            versionID = this.sharedRealm.getVersionID();
        } catch (IllegalStateException unused) {
            getVersion();
            versionID = this.sharedRealm.getVersionID();
        }
        return (DynamicRealm) RealmCache.createRealmOrGetFromCache(this.configuration, DynamicRealm.class, versionID);
    }

    private DynamicRealm(OsSharedRealm osSharedRealm) {
        super(osSharedRealm);
        this.schema = new MutableRealmSchema(this);
    }

    public RealmAsyncTask executeTransactionAsync(Transaction transaction, Transaction.OnError onError) {
        if (onError != null) {
            return executeTransactionAsync(transaction, null, onError);
        }
        throw new IllegalArgumentException("onError callback can't be null");
    }

    public DynamicRealmObject createObject(String str, Object obj) {
        return new DynamicRealmObject(this, CheckedRow.getFromRow(OsObject.createWithPrimaryKey(this.schema.getTable(str), obj)));
    }

    public RealmAsyncTask executeTransactionAsync(Transaction transaction, @Nullable Transaction.OnSuccess onSuccess, @Nullable Transaction.OnError onError) {
        checkIfValid();
        if (transaction != null) {
            if (!isFrozen()) {
                boolean zCanDeliverNotification = this.sharedRealm.capabilities.canDeliverNotification();
                if (onSuccess != null || onError != null) {
                    this.sharedRealm.capabilities.checkCanDeliverNotification("Callback cannot be delivered on current thread.");
                }
                RealmConfiguration configuration = getConfiguration();
                RealmNotifier realmNotifier = this.sharedRealm.realmNotifier;
                RealmThreadPoolExecutor realmThreadPoolExecutor = BaseRealm.asyncTaskExecutor;
                return new RealmAsyncTaskImpl(realmThreadPoolExecutor.submitTransaction(new AnonymousClass2(configuration, transaction, zCanDeliverNotification, onSuccess, realmNotifier, onError)), realmThreadPoolExecutor);
            }
            throw new IllegalStateException("Write transactions on a frozen Realm is not allowed.");
        }
        throw new IllegalArgumentException("Transaction should not be null");
    }
}
