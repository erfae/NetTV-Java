package io.realm;

import android.content.Context;
import android.os.Looper;
import io.reactivex.Flowable;
import io.realm.exceptions.RealmException;
import io.realm.internal.CheckedRow;
import io.realm.internal.ColumnInfo;
import io.realm.internal.InvalidRow;
import io.realm.internal.OsObjectStore;
import io.realm.internal.OsRealmConfig;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import io.realm.internal.async.RealmThreadPoolExecutor;
import io.realm.log.RealmLog;
import java.io.Closeable;
import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes2.dex */
public abstract class BaseRealm implements Closeable {
    private static final String INCORRECT_THREAD_CLOSE_MESSAGE = "Realm access from incorrect thread. Realm instance can only be closed on the thread it was created.";
    private static final String NOT_IN_TRANSACTION_MESSAGE = "Changing Realm data can only be done from inside a transaction.";
    public static volatile Context applicationContext;
    public final RealmConfiguration configuration;
    public final boolean frozen;
    private RealmCache realmCache;
    private OsSharedRealm.SchemaChangedCallback schemaChangedCallback;
    public OsSharedRealm sharedRealm;
    private boolean shouldCloseSharedRealm;
    public final long threadId;
    public static final RealmThreadPoolExecutor asyncTaskExecutor = RealmThreadPoolExecutor.newDefaultExecutor();
    public static final RealmThreadPoolExecutor WRITE_EXECUTOR = RealmThreadPoolExecutor.newSingleThreadExecutor();
    public static final ThreadLocalRealmObjectContext objectContext = new ThreadLocalRealmObjectContext();

    public static abstract class InstanceCallback<T extends BaseRealm> {
        public void onError(Throwable th) {
            throw new RealmException("Exception happens when initializing Realm in the background thread.", th);
        }

        public abstract void onSuccess(T t);
    }

    public static final class RealmObjectContext {
        private boolean acceptDefaultValue;
        private ColumnInfo columnInfo;
        private List<String> excludeFields;
        private BaseRealm realm;
        private Row row;

        public void clear() {
            this.realm = null;
            this.row = null;
            this.columnInfo = null;
            this.acceptDefaultValue = false;
            this.excludeFields = null;
        }

        public boolean getAcceptDefaultValue() {
            return this.acceptDefaultValue;
        }

        public ColumnInfo getColumnInfo() {
            return this.columnInfo;
        }

        public List<String> getExcludeFields() {
            return this.excludeFields;
        }

        public final BaseRealm getRealm() {
            return this.realm;
        }

        public Row getRow() {
            return this.row;
        }

        public void set(BaseRealm baseRealm, Row row, ColumnInfo columnInfo, boolean z, List<String> list) {
            this.realm = baseRealm;
            this.row = row;
            this.columnInfo = columnInfo;
            this.acceptDefaultValue = z;
            this.excludeFields = list;
        }
    }

    public static final class ThreadLocalRealmObjectContext extends ThreadLocal<RealmObjectContext> {
        @Override // java.lang.ThreadLocal
        public final RealmObjectContext initialValue() {
            return new RealmObjectContext();
        }
    }

    public BaseRealm(RealmCache realmCache, @Nullable OsSchemaInfo osSchemaInfo, OsSharedRealm.VersionID versionID) {
        RealmConfiguration configuration = realmCache.getConfiguration();
        this.schemaChangedCallback = new OsSharedRealm.SchemaChangedCallback() { // from class: io.realm.BaseRealm.1
            @Override // io.realm.internal.OsSharedRealm.SchemaChangedCallback
            public void onSchemaChanged() {
                RealmSchema schema = BaseRealm.this.getSchema();
                if (schema != null) {
                    schema.refresh();
                }
                if (BaseRealm.this instanceof Realm) {
                    schema.createKeyPathMapping();
                }
            }
        };
        this.threadId = Thread.currentThread().getId();
        this.configuration = configuration;
        this.realmCache = null;
        OsSharedRealm.MigrationCallback migrationCallbackCreateMigrationCallback = (osSchemaInfo == null || configuration.getMigration() == null) ? null : createMigrationCallback(configuration.getMigration());
        final Realm.Transaction initialDataTransaction = configuration.getInitialDataTransaction();
        OsSharedRealm osSharedRealm = OsSharedRealm.getInstance(new OsRealmConfig.Builder(configuration).fifoFallbackDir(new File(applicationContext.getFilesDir(), ".realm.temp")).autoUpdateNotification(true).migrationCallback(migrationCallbackCreateMigrationCallback).schemaInfo(osSchemaInfo).initializationCallback(initialDataTransaction != null ? new OsSharedRealm.InitializationCallback() { // from class: io.realm.BaseRealm.2
            @Override // io.realm.internal.OsSharedRealm.InitializationCallback
            public void onInit(OsSharedRealm osSharedRealm2) {
                initialDataTransaction.execute(Realm.createInstance(osSharedRealm2));
            }
        } : null), versionID);
        this.sharedRealm = osSharedRealm;
        this.frozen = osSharedRealm.isFrozen();
        this.shouldCloseSharedRealm = true;
        this.sharedRealm.registerSchemaChangedCallback(this.schemaChangedCallback);
        this.realmCache = realmCache;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static OsSharedRealm.MigrationCallback createMigrationCallback(final RealmMigration realmMigration) {
        return new OsSharedRealm.MigrationCallback() { // from class: io.realm.BaseRealm.6
            @Override // io.realm.internal.OsSharedRealm.MigrationCallback
            public void onMigrationNeeded(OsSharedRealm osSharedRealm, long j, long j2) {
                realmMigration.migrate(DynamicRealm.createInstance(osSharedRealm), j, j2);
            }
        };
    }

    public final <T extends BaseRealm> void addListener(RealmChangeListener<T> realmChangeListener) {
        if (realmChangeListener == null) {
            throw new IllegalArgumentException("Listener should not be null");
        }
        checkIfValid();
        this.sharedRealm.capabilities.checkCanDeliverNotification("Listeners cannot be used on current thread.");
        if (this.frozen) {
            throw new IllegalStateException("It is not possible to add a change listener to a frozen Realm since it never changes.");
        }
        this.sharedRealm.realmNotifier.addChangeListener(this, realmChangeListener);
    }

    public abstract Flowable asFlowable();

    public void beginTransaction() {
        checkIfValid();
        this.sharedRealm.beginTransaction();
    }

    public void cancelTransaction() {
        checkIfValid();
        this.sharedRealm.cancelTransaction();
    }

    public final void checkAllowQueriesOnUiThread() {
        if (this.sharedRealm.capabilities.isMainThread() && !getConfiguration().isAllowQueriesOnUiThread()) {
            throw new RealmException("Queries on the UI thread have been disabled. They can be enabled by setting 'RealmConfiguration.Builder.allowQueriesOnUiThread(true)'.");
        }
    }

    public final void checkAllowWritesOnUiThread() {
        if (this.sharedRealm.capabilities.isMainThread() && !getConfiguration().isAllowWritesOnUiThread()) {
            throw new RealmException("Running transactions on the UI thread has been disabled. It can be enabled by setting 'RealmConfiguration.Builder.allowWritesOnUiThread(true)'.");
        }
    }

    public final void checkIfValid() {
        OsSharedRealm osSharedRealm = this.sharedRealm;
        if (osSharedRealm == null || osSharedRealm.isClosed()) {
            throw new IllegalStateException("This Realm instance has already been closed, making it unusable.");
        }
        if (!this.frozen && this.threadId != Thread.currentThread().getId()) {
            throw new IllegalStateException("Realm access from incorrect thread. Realm objects can only be accessed on the thread they were created.");
        }
    }

    public final void checkIfValidAndInTransaction() {
        if (!isInTransaction()) {
            throw new IllegalStateException(NOT_IN_TRANSACTION_MESSAGE);
        }
    }

    public final void checkNotInSync() {
        Objects.requireNonNull(this.configuration);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (!this.frozen && this.threadId != Thread.currentThread().getId()) {
            throw new IllegalStateException(INCORRECT_THREAD_CLOSE_MESSAGE);
        }
        RealmCache realmCache = this.realmCache;
        if (realmCache != null) {
            realmCache.release(this);
            return;
        }
        this.realmCache = null;
        OsSharedRealm osSharedRealm = this.sharedRealm;
        if (osSharedRealm == null || !this.shouldCloseSharedRealm) {
            return;
        }
        osSharedRealm.close();
        this.sharedRealm = null;
    }

    public void commitTransaction() {
        checkIfValid();
        this.sharedRealm.commitTransaction();
    }

    public void deleteAll() {
        checkIfValid();
        Iterator<RealmObjectSchema> it = getSchema().getAll().iterator();
        while (it.hasNext()) {
            getSchema().getTable(it.next().getClassName()).clear();
        }
    }

    public final void doClose() {
        this.realmCache = null;
        OsSharedRealm osSharedRealm = this.sharedRealm;
        if (osSharedRealm == null || !this.shouldCloseSharedRealm) {
            return;
        }
        osSharedRealm.close();
        this.sharedRealm = null;
    }

    public final void finalize() throws Throwable {
        OsSharedRealm osSharedRealm;
        if (this.shouldCloseSharedRealm && (osSharedRealm = this.sharedRealm) != null && !osSharedRealm.isClosed()) {
            RealmLog.warn("Remember to call close() on all Realm instances. Realm %s is being finalized without being closed, this can lead to running out of native memory.", this.configuration.getPath());
            RealmCache realmCache = this.realmCache;
            if (realmCache != null) {
                realmCache.leak();
            }
        }
        super.finalize();
    }

    public abstract BaseRealm freeze();

    public final <E extends RealmModel> E get(@Nullable Class<E> cls, @Nullable String str, UncheckedRow uncheckedRow) {
        return str != null ? new DynamicRealmObject(this, CheckedRow.getFromRow(uncheckedRow)) : (E) this.configuration.getSchemaMediator().newInstance(cls, this, uncheckedRow, getSchema().getColumnInfo((Class<? extends RealmModel>) cls), false, Collections.emptyList());
    }

    public RealmConfiguration getConfiguration() {
        return this.configuration;
    }

    public final Row getEmbeddedObjectRow(String str, RealmObjectProxy realmObjectProxy, String str2, RealmSchema realmSchema, RealmObjectSchema realmObjectSchema) {
        long columnKey = realmObjectSchema.getColumnKey(str2);
        RealmFieldType fieldType = realmObjectSchema.getFieldType(str2);
        Row row$realm = realmObjectProxy.realmGet$proxyState().getRow$realm();
        RealmFieldType fieldType2 = realmObjectSchema.getFieldType(str2);
        if (!(fieldType2 == RealmFieldType.OBJECT || fieldType2 == RealmFieldType.LIST)) {
            throw new IllegalArgumentException(String.format("Field '%s' does not contain a valid link", str2));
        }
        String propertyClassName = realmObjectSchema.getPropertyClassName(str2);
        if (propertyClassName.equals(str)) {
            return realmSchema.getTable(str).getCheckedRow(row$realm.createEmbeddedObject(columnKey, fieldType));
        }
        throw new IllegalArgumentException(String.format("Parent type %s expects that property '%s' be of type %s but was %s.", realmObjectSchema.getClassName(), str2, propertyClassName, str));
    }

    public long getNumberOfActiveVersions() {
        checkIfValid();
        return this.sharedRealm.getNumberOfVersions();
    }

    public String getPath() {
        return this.configuration.getPath();
    }

    public abstract RealmSchema getSchema();

    public long getVersion() {
        return OsObjectStore.getSchemaVersion(this.sharedRealm);
    }

    public boolean isAutoRefresh() {
        return this.sharedRealm.isAutoRefresh();
    }

    public boolean isClosed() {
        if (!this.frozen && this.threadId != Thread.currentThread().getId()) {
            throw new IllegalStateException("Realm access from incorrect thread. Realm objects can only be accessed on the thread they were created.");
        }
        OsSharedRealm osSharedRealm = this.sharedRealm;
        return osSharedRealm == null || osSharedRealm.isClosed();
    }

    public abstract boolean isEmpty();

    public boolean isFrozen() {
        OsSharedRealm osSharedRealm = this.sharedRealm;
        if (osSharedRealm == null || osSharedRealm.isClosed()) {
            throw new IllegalStateException("This Realm instance has already been closed, making it unusable.");
        }
        return this.frozen;
    }

    public boolean isInTransaction() {
        checkIfValid();
        return this.sharedRealm.isInTransaction();
    }

    public void refresh() {
        checkIfValid();
        checkAllowQueriesOnUiThread();
        if (isInTransaction()) {
            throw new IllegalStateException("Cannot refresh a Realm instance inside a transaction.");
        }
        this.sharedRealm.refresh();
    }

    public final void removeAllListeners() {
        if (isClosed()) {
            RealmLog.warn("Calling removeChangeListener on a closed Realm %s, make sure to close all listeners before closing the Realm.", this.configuration.getPath());
        }
        this.sharedRealm.realmNotifier.removeChangeListeners(this);
    }

    public final <T extends BaseRealm> void removeListener(RealmChangeListener<T> realmChangeListener) {
        if (realmChangeListener == null) {
            throw new IllegalArgumentException("Listener should not be null");
        }
        if (isClosed()) {
            RealmLog.warn("Calling removeChangeListener on a closed Realm %s, make sure to close all listeners before closing the Realm.", this.configuration.getPath());
        }
        this.sharedRealm.realmNotifier.removeChangeListener(this, realmChangeListener);
    }

    public void setAutoRefresh(boolean z) {
        checkIfValid();
        this.sharedRealm.setAutoRefresh(z);
    }

    @Deprecated
    public void stopWaitForChange() {
        RealmCache realmCache = this.realmCache;
        if (realmCache == null) {
            throw new IllegalStateException("This Realm instance has already been closed, making it unusable.");
        }
        RealmCache.Callback0 callback0 = new RealmCache.Callback0() { // from class: io.realm.BaseRealm.3
            @Override // io.realm.RealmCache.Callback0
            public void onCall() {
                OsSharedRealm osSharedRealm = BaseRealm.this.sharedRealm;
                if (osSharedRealm == null || osSharedRealm.isClosed()) {
                    throw new IllegalStateException("This Realm instance has already been closed, making it unusable.");
                }
                BaseRealm.this.sharedRealm.stopWaitForChange();
            }
        };
        synchronized (realmCache) {
            callback0.onCall();
        }
    }

    @Deprecated
    public boolean waitForChange() {
        checkIfValid();
        if (isInTransaction()) {
            throw new IllegalStateException("Cannot wait for changes inside of a transaction.");
        }
        if (Looper.myLooper() != null) {
            throw new IllegalStateException("Cannot wait for changes inside a Looper thread. Use RealmChangeListeners instead.");
        }
        boolean zWaitForChange = this.sharedRealm.waitForChange();
        if (zWaitForChange) {
            this.sharedRealm.refresh();
        }
        return zWaitForChange;
    }

    public void writeCopyTo(File file) {
        if (file == null) {
            throw new IllegalArgumentException("The destination argument cannot be null");
        }
        checkIfValid();
        this.sharedRealm.writeCopy(file, null);
    }

    public void writeEncryptedCopyTo(File file, byte[] bArr) {
        if (file == null) {
            throw new IllegalArgumentException("The destination argument cannot be null");
        }
        checkIfValid();
        this.sharedRealm.writeCopy(file, bArr);
    }

    public final RealmModel get(Class cls, long j, List list) {
        return this.configuration.getSchemaMediator().newInstance(cls, this, getSchema().getTable((Class<? extends RealmModel>) cls).getUncheckedRow(j), getSchema().getColumnInfo((Class<? extends RealmModel>) cls), false, list);
    }

    public final <E extends RealmModel> E get(@Nullable Class<E> cls, @Nullable String str, long j) {
        boolean z = str != null;
        Table table = z ? getSchema().getTable(str) : getSchema().getTable((Class<? extends RealmModel>) cls);
        if (z) {
            return new DynamicRealmObject(this, j != -1 ? table.getCheckedRow(j) : InvalidRow.INSTANCE);
        }
        return (E) this.configuration.getSchemaMediator().newInstance(cls, this, j != -1 ? table.getUncheckedRow(j) : InvalidRow.INSTANCE, getSchema().getColumnInfo((Class<? extends RealmModel>) cls), false, Collections.emptyList());
    }

    public BaseRealm(OsSharedRealm osSharedRealm) {
        this.schemaChangedCallback = new OsSharedRealm.SchemaChangedCallback() { // from class: io.realm.BaseRealm.1
            @Override // io.realm.internal.OsSharedRealm.SchemaChangedCallback
            public void onSchemaChanged() {
                RealmSchema schema = BaseRealm.this.getSchema();
                if (schema != null) {
                    schema.refresh();
                }
                if (BaseRealm.this instanceof Realm) {
                    schema.createKeyPathMapping();
                }
            }
        };
        this.threadId = Thread.currentThread().getId();
        this.configuration = osSharedRealm.getConfiguration();
        this.realmCache = null;
        this.sharedRealm = osSharedRealm;
        this.frozen = osSharedRealm.isFrozen();
        this.shouldCloseSharedRealm = false;
    }
}
