package io.realm;

import android.content.Context;
import android.os.SystemClock;
import android.util.JsonReader;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.reactivex.Flowable;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import io.realm.internal.ColumnIndices;
import io.realm.internal.ObjectServerFacade;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectStore;
import io.realm.internal.OsRealmConfig;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.RealmCore;
import io.realm.internal.RealmNotifier;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Table;
import io.realm.internal.Util;
import io.realm.internal.async.RealmAsyncTaskImpl;
import io.realm.internal.async.RealmThreadPoolExecutor;
import io.realm.log.RealmLog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class Realm extends BaseRealm {
    public static final String DEFAULT_REALM_NAME = "default.realm";
    public static final int ENCRYPTION_KEY_LENGTH = 64;
    private static final String NULL_CONFIG_MSG = "A non-null RealmConfiguration must be provided";
    private static RealmConfiguration defaultConfiguration;
    private static final Object defaultConfigurationLock = new Object();
    private final RealmSchema schema;

    /* JADX INFO: renamed from: io.realm.Realm$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public final /* synthetic */ boolean val$canDeliverNotification;
        public final /* synthetic */ Transaction.OnError val$onError;
        public final /* synthetic */ Transaction.OnSuccess val$onSuccess;
        public final /* synthetic */ RealmConfiguration val$realmConfiguration;
        public final /* synthetic */ RealmNotifier val$realmNotifier;
        public final /* synthetic */ Transaction val$transaction;

        public AnonymousClass1(RealmConfiguration realmConfiguration, Transaction transaction, boolean z, Transaction.OnSuccess onSuccess, RealmNotifier realmNotifier, Transaction.OnError onError) {
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
            Realm realm = Realm.getInstance(this.val$realmConfiguration);
            realm.beginTransaction();
            final Throwable th = null;
            try {
                this.val$transaction.execute(realm);
                if (Thread.currentThread().isInterrupted()) {
                    try {
                        if (realm.isInTransaction()) {
                            realm.cancelTransaction();
                        }
                        return;
                    } finally {
                        realm.close();
                    }
                }
                realm.commitTransaction();
                versionID = realm.sharedRealm.getVersionID();
                try {
                    if (realm.isInTransaction()) {
                        realm.cancelTransaction();
                    }
                    realm.close();
                } catch (Throwable th2) {
                    realm.close();
                    throw th2;
                }
            } catch (Throwable th3) {
                try {
                    if (realm.isInTransaction()) {
                        realm.cancelTransaction();
                    }
                    realm.close();
                    versionID = null;
                    th = th3;
                } catch (Throwable th4) {
                    realm.close();
                    throw th4;
                }
            }
            if (!this.val$canDeliverNotification) {
                if (th != null) {
                    throw new RealmException("Async transaction failed", th);
                }
            } else if (versionID != null && this.val$onSuccess != null) {
                this.val$realmNotifier.post(new Runnable() { // from class: io.realm.Realm.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (Realm.this.isClosed()) {
                            AnonymousClass1.this.val$onSuccess.onSuccess();
                        } else if (Realm.this.sharedRealm.getVersionID().compareTo(versionID) < 0) {
                            Realm.this.sharedRealm.realmNotifier.addTransactionCallback(new Runnable() { // from class: io.realm.Realm.1.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass1.this.val$onSuccess.onSuccess();
                                }
                            });
                        } else {
                            AnonymousClass1.this.val$onSuccess.onSuccess();
                        }
                    }
                });
            } else if (th != null) {
                this.val$realmNotifier.post(new Runnable() { // from class: io.realm.Realm.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Transaction.OnError onError = AnonymousClass1.this.val$onError;
                        if (onError == null) {
                            throw new RealmException("Async transaction failed", th);
                        }
                        onError.onError(th);
                    }
                });
            }
        }
    }

    public static abstract class Callback extends BaseRealm.InstanceCallback<Realm> {
        @Override // io.realm.BaseRealm.InstanceCallback
        public void onError(Throwable th) {
            super.onError(th);
        }

        @Override // io.realm.BaseRealm.InstanceCallback
        public abstract void onSuccess(Realm realm);
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

        void execute(Realm realm);
    }

    private Realm(RealmCache realmCache, OsSharedRealm.VersionID versionID) {
        super(realmCache, createExpectedSchemaInfo(realmCache.getConfiguration().getSchemaMediator()), versionID);
        this.schema = new ImmutableRealmSchema(this, new ColumnIndices(this.configuration.getSchemaMediator(), this.sharedRealm.getSchemaInfo()));
        if (this.configuration.isReadOnly()) {
            RealmProxyMediator schemaMediator = this.configuration.getSchemaMediator();
            Iterator<Class<? extends RealmModel>> it = schemaMediator.getModelClasses().iterator();
            while (it.hasNext()) {
                String tableNameForClass = Table.getTableNameForClass(schemaMediator.getSimpleClassName(it.next()));
                if (!this.sharedRealm.hasTable(tableNameForClass)) {
                    this.sharedRealm.close();
                    throw new RealmMigrationNeededException(this.configuration.getPath(), String.format(Locale.US, "Cannot open the read only Realm. '%s' is missing.", Table.getClassNameForTable(tableNameForClass)));
                }
            }
        }
    }

    private static void checkFilesDirAvailable(Context context) {
        File filesDir = context.getFilesDir();
        if (filesDir != null) {
            if (filesDir.exists()) {
                return;
            } else {
                try {
                    filesDir.mkdirs();
                } catch (SecurityException unused) {
                }
            }
        }
        if (filesDir == null || !filesDir.exists()) {
            long[] jArr = {1, 2, 5, 10, 16};
            long j = 0;
            int i = -1;
            do {
                if (context.getFilesDir() != null && context.getFilesDir().exists()) {
                    break;
                }
                i++;
                long j2 = jArr[Math.min(i, 4)];
                SystemClock.sleep(j2);
                j += j2;
            } while (j <= 200);
        }
        if (context.getFilesDir() == null || !context.getFilesDir().exists()) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Context.getFilesDir() returns ");
            sbM.append(context.getFilesDir());
            sbM.append(" which is not an existing directory. See https://issuetracker.google.com/issues/36918154");
            throw new IllegalStateException(sbM.toString());
        }
    }

    private void checkHasPrimaryKey(Class<? extends RealmModel> cls) {
        if (this.configuration.getSchemaMediator().hasPrimaryKey(cls)) {
            return;
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("A RealmObject with no @PrimaryKey cannot be updated: ");
        sbM.append(cls.toString());
        throw new IllegalArgumentException(sbM.toString());
    }

    private void checkMaxDepth(int i) {
        if (i < 0) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m23m("maxDepth must be > 0. It was: ", i));
        }
    }

    private <E extends RealmModel> void checkNotNullObject(E e) {
        if (e == null) {
            throw new IllegalArgumentException("Null objects cannot be copied into Realm.");
        }
    }

    private <E extends RealmModel> void checkValidObjectForDetach(E e) {
        if (e == null) {
            throw new IllegalArgumentException("Null objects cannot be copied from Realm.");
        }
        if (!RealmObject.isManaged(e) || !RealmObject.isValid(e)) {
            throw new IllegalArgumentException("Only valid managed objects can be copied from Realm.");
        }
        if (e instanceof DynamicRealmObject) {
            throw new IllegalArgumentException("DynamicRealmObject cannot be copied from Realm.");
        }
    }

    public static boolean compactRealm(RealmConfiguration realmConfiguration) {
        OsSharedRealm osSharedRealm = OsSharedRealm.getInstance(realmConfiguration, OsSharedRealm.VersionID.LIVE);
        Boolean boolValueOf = Boolean.valueOf(osSharedRealm.compact());
        osSharedRealm.close();
        return boolValueOf.booleanValue();
    }

    private <E extends RealmModel> E copyOrUpdate(E e, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        checkIfValid();
        if (!isInTransaction()) {
            throw new IllegalStateException("`copyOrUpdate` can only be called inside a write transaction.");
        }
        if (this.configuration.getSchemaMediator().isEmbedded(Util.getOriginalModelClass(e.getClass()))) {
            throw new IllegalArgumentException("Embedded objects cannot be copied into Realm by themselves. They need to be attached to a parent object");
        }
        try {
            return (E) this.configuration.getSchemaMediator().copyOrUpdate(this, e, z, map, set);
        } catch (IllegalStateException e2) {
            if (e2.getMessage().startsWith("Attempting to create an object of type")) {
                throw new RealmPrimaryKeyConstraintException(e2.getMessage());
            }
            throw e2;
        }
    }

    private <E extends RealmModel> E createDetachedCopy(E e, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        checkIfValid();
        return (E) this.configuration.getSchemaMediator().createDetachedCopy(e, i, map);
    }

    private static OsSchemaInfo createExpectedSchemaInfo(RealmProxyMediator realmProxyMediator) {
        return new OsSchemaInfo(realmProxyMediator.getExpectedObjectSchemaInfoMap().values());
    }

    public static Realm createInstance(RealmCache realmCache, OsSharedRealm.VersionID versionID) {
        return new Realm(realmCache, versionID);
    }

    public static boolean deleteRealm(final RealmConfiguration realmConfiguration) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        if (OsObjectStore.callWithLock(realmConfiguration, new Runnable() { // from class: io.realm.BaseRealm.4
            @Override // java.lang.Runnable
            public void run() {
                atomicBoolean.set(Util.deleteRealm(realmConfiguration.getPath(), realmConfiguration.getRealmDirectory(), realmConfiguration.getRealmFileName()));
            }
        })) {
            return atomicBoolean.get();
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("It's not allowed to delete the file associated with an open Realm. Remember to close() all the instances of the Realm before deleting its file: ");
        sbM.append(realmConfiguration.getPath());
        throw new IllegalStateException(sbM.toString());
    }

    @Nullable
    public static Context getApplicationContext() {
        return BaseRealm.applicationContext;
    }

    @Nullable
    public static RealmConfiguration getDefaultConfiguration() {
        RealmConfiguration realmConfiguration;
        synchronized (defaultConfigurationLock) {
            realmConfiguration = defaultConfiguration;
        }
        return realmConfiguration;
    }

    public static Realm getDefaultInstance() {
        RealmConfiguration defaultConfiguration2 = getDefaultConfiguration();
        if (defaultConfiguration2 != null) {
            return (Realm) RealmCache.createRealmOrGetFromCache(defaultConfiguration2, Realm.class);
        }
        if (BaseRealm.applicationContext == null) {
            throw new IllegalStateException("Call `Realm.init(Context)` before calling this method.");
        }
        throw new IllegalStateException("Set default configuration by using `Realm.setDefaultConfiguration(RealmConfiguration)`.");
    }

    @Nullable
    public static Object getDefaultModule() {
        try {
            Constructor<?> constructor = DefaultRealmModule.class.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            return constructor.newInstance(new Object[0]);
        } catch (ClassNotFoundException unused) {
            return null;
        } catch (IllegalAccessException e) {
            throw new RealmException("Could not create an instance of io.realm.DefaultRealmModule", e);
        } catch (InstantiationException e2) {
            throw new RealmException("Could not create an instance of io.realm.DefaultRealmModule", e2);
        } catch (InvocationTargetException e3) {
            throw new RealmException("Could not create an instance of io.realm.DefaultRealmModule", e3);
        }
    }

    private Scanner getFullStringScanner(InputStream inputStream) {
        return new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
    }

    public static int getGlobalInstanceCount(RealmConfiguration realmConfiguration) {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        RealmCache.invokeWithGlobalRefCount(realmConfiguration, new RealmCache.Callback() { // from class: io.realm.Realm.2
            @Override // io.realm.RealmCache.Callback
            public void onResult(int i) {
                atomicInteger.set(i);
            }
        });
        return atomicInteger.get();
    }

    public static Realm getInstance(RealmConfiguration realmConfiguration) {
        if (realmConfiguration != null) {
            return (Realm) RealmCache.createRealmOrGetFromCache(realmConfiguration, Realm.class);
        }
        throw new IllegalArgumentException(NULL_CONFIG_MSG);
    }

    public static RealmAsyncTask getInstanceAsync(RealmConfiguration realmConfiguration, Callback callback) {
        if (realmConfiguration != null) {
            return RealmCache.createRealmOrGetFromCacheAsync(realmConfiguration, callback, Realm.class);
        }
        throw new IllegalArgumentException(NULL_CONFIG_MSG);
    }

    public static int getLocalInstanceCount(RealmConfiguration realmConfiguration) {
        return RealmCache.getLocalThreadCount(realmConfiguration);
    }

    public static synchronized void init(Context context) {
        initializeRealm(context, "");
    }

    private static void initializeRealm(Context context, String str) {
        if (BaseRealm.applicationContext == null) {
            if (context == null) {
                throw new IllegalArgumentException("Non-null context required.");
            }
            checkFilesDirAvailable(context);
            RealmCore.loadLibrary(context);
            setDefaultConfiguration(new RealmConfiguration.Builder(context).build());
            ObjectServerFacade.getSyncFacadeIfPossible().initialize(context, str, Realm$$ExternalSyntheticLambda0.INSTANCE, Realm$$ExternalSyntheticLambda0.INSTANCE$28);
            if (context.getApplicationContext() != null) {
                BaseRealm.applicationContext = context.getApplicationContext();
            } else {
                BaseRealm.applicationContext = context;
            }
            OsSharedRealm.initialize(new File(context.getFilesDir(), ".realm.temp"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Realm lambda$initializeRealm$0(RealmConfiguration realmConfiguration, OsSharedRealm.VersionID versionID) {
        return (Realm) RealmCache.createRealmOrGetFromCache(realmConfiguration, Realm.class, versionID);
    }

    public static void migrateRealm(final RealmConfiguration realmConfiguration, @Nullable final RealmMigration realmMigration) throws FileNotFoundException {
        if (realmConfiguration == null) {
            throw new IllegalArgumentException("RealmConfiguration must be provided");
        }
        if (realmMigration == null && realmConfiguration.getMigration() == null) {
            throw new RealmMigrationNeededException(realmConfiguration.getPath(), "RealmMigration must be provided.");
        }
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        RealmCache.invokeWithGlobalRefCount(realmConfiguration, new RealmCache.Callback() { // from class: io.realm.BaseRealm.5
            @Override // io.realm.RealmCache.Callback
            public void onResult(int i) {
                if (i != 0) {
                    StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Cannot migrate a Realm file that is already open: ");
                    sbM.append(realmConfiguration.getPath());
                    throw new IllegalStateException(sbM.toString());
                }
                if (!new File(realmConfiguration.getPath()).exists()) {
                    atomicBoolean.set(true);
                    return;
                }
                OsSchemaInfo osSchemaInfo = new OsSchemaInfo(realmConfiguration.getSchemaMediator().getExpectedObjectSchemaInfoMap().values());
                RealmMigration migration = realmMigration;
                if (migration == null) {
                    migration = realmConfiguration.getMigration();
                }
                OsSharedRealm osSharedRealm = OsSharedRealm.getInstance(new OsRealmConfig.Builder(realmConfiguration).autoUpdateNotification(false).schemaInfo(osSchemaInfo).migrationCallback(migration != null ? BaseRealm.createMigrationCallback(migration) : null), OsSharedRealm.VersionID.LIVE);
                if (osSharedRealm != null) {
                    osSharedRealm.close();
                }
            }
        });
        if (atomicBoolean.get()) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Cannot migrate a Realm file which doesn't exist: ");
            sbM.append(realmConfiguration.getPath());
            throw new FileNotFoundException(sbM.toString());
        }
    }

    public static void removeDefaultConfiguration() {
        synchronized (defaultConfigurationLock) {
            defaultConfiguration = null;
        }
    }

    public static void setDefaultConfiguration(RealmConfiguration realmConfiguration) {
        if (realmConfiguration == null) {
            throw new IllegalArgumentException(NULL_CONFIG_MSG);
        }
        synchronized (defaultConfigurationLock) {
            defaultConfiguration = realmConfiguration;
        }
    }

    public void addChangeListener(RealmChangeListener<Realm> realmChangeListener) {
        addListener(realmChangeListener);
    }

    @Override // io.realm.BaseRealm
    public Flowable<Realm> asFlowable() {
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

    public <E extends RealmModel> List<E> copyFromRealm(Iterable<E> iterable) {
        return copyFromRealm(iterable, Integer.MAX_VALUE);
    }

    public <E extends RealmModel> E copyToRealm(E e, ImportFlag... importFlagArr) {
        checkNotNullObject(e);
        return (E) copyOrUpdate(e, false, new HashMap(), Util.toSet(importFlagArr));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <E extends RealmModel> E copyToRealmOrUpdate(E e, ImportFlag... importFlagArr) {
        checkNotNullObject(e);
        checkHasPrimaryKey(e.getClass());
        return (E) copyOrUpdate(e, true, new HashMap(), Util.toSet(importFlagArr));
    }

    public <E extends RealmModel> void createAllFromJson(Class<E> cls, JSONArray jSONArray) {
        if (cls == null || jSONArray == null) {
            return;
        }
        checkIfValid();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONArray.getJSONObject(i), false);
            } catch (JSONException e) {
                throw new RealmException("Could not map JSON", e);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <E extends RealmModel> E createEmbeddedObject(Class<E> cls, RealmModel realmModel, String str) {
        checkIfValid();
        Util.checkNull(realmModel, "parentObject");
        Util.checkEmpty(str, "parentProperty");
        if (!RealmObject.isManaged(realmModel) || !RealmObject.isValid(realmModel)) {
            throw new IllegalArgumentException("Only valid, managed objects can be a parent to an embedded object.");
        }
        return (E) this.configuration.getSchemaMediator().newInstance(cls, this, getEmbeddedObjectRow(this.schema.getSchemaForClass((Class<? extends RealmModel>) cls).getClassName(), (RealmObjectProxy) realmModel, str, this.schema, this.schema.getSchemaForClass((Class<? extends RealmModel>) realmModel.getClass())), this.schema.getColumnInfo((Class<? extends RealmModel>) cls), true, Collections.EMPTY_LIST);
    }

    public <E extends RealmModel> E createObject(Class<E> cls) {
        checkIfValid();
        RealmProxyMediator schemaMediator = this.configuration.getSchemaMediator();
        if (!schemaMediator.isEmbedded(cls)) {
            return (E) createObjectInternal(cls, Collections.emptyList());
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("This class is marked embedded. Use `createEmbeddedObject(class, parent, property)` instead:  ");
        sbM.append(schemaMediator.getSimpleClassName(cls));
        throw new IllegalArgumentException(sbM.toString());
    }

    @Nullable
    public <E extends RealmModel> E createObjectFromJson(Class<E> cls, JSONObject jSONObject) {
        if (cls == null || jSONObject == null) {
            return null;
        }
        checkIfValid();
        try {
            return (E) this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONObject, false);
        } catch (JSONException e) {
            throw new RealmException("Could not map JSON", e);
        }
    }

    public final RealmModel createObjectInternal(Class cls, List list) {
        Table table = this.schema.getTable((Class<? extends RealmModel>) cls);
        if (OsObjectStore.getPrimaryKeyForObject(this.sharedRealm, this.configuration.getSchemaMediator().getSimpleClassName(cls)) == null) {
            return this.configuration.getSchemaMediator().newInstance(cls, this, OsObject.create(table), this.schema.getColumnInfo((Class<? extends RealmModel>) cls), true, list);
        }
        throw new RealmException(String.format(Locale.US, "'%s' has a primary key, use 'createObject(Class<E>, Object)' instead.", table.getClassName()));
    }

    public <E extends RealmModel> void createOrUpdateAllFromJson(Class<E> cls, JSONArray jSONArray) {
        if (cls == null || jSONArray == null) {
            return;
        }
        checkIfValid();
        checkHasPrimaryKey(cls);
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONArray.getJSONObject(i), true);
            } catch (JSONException e) {
                throw new RealmException("Could not map JSON", e);
            }
        }
    }

    public <E extends RealmModel> E createOrUpdateObjectFromJson(Class<E> cls, JSONObject jSONObject) {
        if (cls == null || jSONObject == null) {
            return null;
        }
        checkIfValid();
        checkHasPrimaryKey(cls);
        try {
            return (E) this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONObject, true);
        } catch (JSONException e) {
            throw new RealmException("Could not map JSON", e);
        }
    }

    public void delete(Class<? extends RealmModel> cls) {
        checkIfValid();
        this.schema.getTable(cls).clear();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void deleteAll() {
        super.deleteAll();
    }

    public void executeTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction should not be null");
        }
        checkIfValid();
        checkAllowWritesOnUiThread();
        beginTransaction();
        try {
            transaction.execute(this);
            commitTransaction();
        } catch (Throwable th) {
            if (isInTransaction()) {
                cancelTransaction();
            } else {
                RealmLog.warn("Could not cancel transaction, not currently in a transaction.", new Object[0]);
            }
            throw th;
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

    public final Table getTable(Class<? extends RealmModel> cls) {
        return this.schema.getTable(cls);
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ long getVersion() {
        return super.getVersion();
    }

    public void insert(Collection<? extends RealmModel> collection) {
        checkIfValidAndInTransaction();
        if (collection == null) {
            throw new IllegalArgumentException("Null objects cannot be inserted into Realm.");
        }
        if (collection.isEmpty()) {
            return;
        }
        this.configuration.getSchemaMediator().insert(this, collection);
    }

    public void insertOrUpdate(Collection<? extends RealmModel> collection) {
        checkIfValidAndInTransaction();
        if (collection == null) {
            throw new IllegalArgumentException("Null objects cannot be inserted into Realm.");
        }
        if (collection.isEmpty()) {
            return;
        }
        this.configuration.getSchemaMediator().insertOrUpdate(this, collection);
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
        for (RealmObjectSchema realmObjectSchema : this.schema.getAll()) {
            if (!realmObjectSchema.getClassName().startsWith("__") && realmObjectSchema.table.size() > 0) {
                return false;
            }
        }
        return true;
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

    public void removeChangeListener(RealmChangeListener<Realm> realmChangeListener) {
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

    public <E extends RealmModel> RealmQuery<E> where(Class<E> cls) {
        checkIfValid();
        return RealmQuery.createQuery(this, cls);
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void writeCopyTo(File file) {
        super.writeCopyTo(file);
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void writeEncryptedCopyTo(File file, byte[] bArr) {
        super.writeEncryptedCopyTo(file, bArr);
    }

    public static Realm createInstance(OsSharedRealm osSharedRealm) {
        return new Realm(osSharedRealm);
    }

    public <E extends RealmModel> List<E> copyFromRealm(Iterable<E> iterable, int i) {
        checkMaxDepth(i);
        if (iterable == null) {
            return new ArrayList(0);
        }
        ArrayList arrayList = iterable instanceof Collection ? new ArrayList(((Collection) iterable).size()) : new ArrayList();
        HashMap map = new HashMap();
        for (E e : iterable) {
            checkValidObjectForDetach(e);
            arrayList.add(createDetachedCopy(e, i, map));
        }
        return arrayList;
    }

    public RealmAsyncTask executeTransactionAsync(Transaction transaction, Transaction.OnSuccess onSuccess) {
        if (onSuccess != null) {
            return executeTransactionAsync(transaction, onSuccess, null);
        }
        throw new IllegalArgumentException("onSuccess callback can't be null");
    }

    @Override // io.realm.BaseRealm
    public Realm freeze() {
        return (Realm) RealmCache.createRealmOrGetFromCache(this.configuration, Realm.class, this.sharedRealm.getVersionID());
    }

    public <E extends RealmModel> List<E> copyToRealm(Iterable<E> iterable, ImportFlag... importFlagArr) {
        ArrayList arrayList;
        if (iterable == null) {
            return new ArrayList();
        }
        if (iterable instanceof Collection) {
            arrayList = new ArrayList(((Collection) iterable).size());
        } else {
            arrayList = new ArrayList();
        }
        HashMap map = new HashMap();
        for (E e : iterable) {
            checkNotNullObject(e);
            arrayList.add(copyOrUpdate(e, false, map, Util.toSet(importFlagArr)));
        }
        return arrayList;
    }

    public <E extends RealmModel> List<E> copyToRealmOrUpdate(Iterable<E> iterable, ImportFlag... importFlagArr) {
        ArrayList arrayList;
        if (iterable == null) {
            return new ArrayList(0);
        }
        if (iterable instanceof Collection) {
            arrayList = new ArrayList(((Collection) iterable).size());
        } else {
            arrayList = new ArrayList();
        }
        HashMap map = new HashMap();
        Set<ImportFlag> set = Util.toSet(importFlagArr);
        for (E e : iterable) {
            checkNotNullObject(e);
            arrayList.add(copyOrUpdate(e, true, map, set));
        }
        return arrayList;
    }

    @Nullable
    public <E extends RealmModel> E createObjectFromJson(Class<E> cls, String str) {
        if (cls == null || str == null || str.length() == 0) {
            return null;
        }
        try {
            return (E) createObjectFromJson(cls, new JSONObject(str));
        } catch (JSONException e) {
            throw new RealmException("Could not create Json object from string", e);
        }
    }

    public RealmAsyncTask executeTransactionAsync(Transaction transaction, Transaction.OnError onError) {
        if (onError != null) {
            return executeTransactionAsync(transaction, null, onError);
        }
        throw new IllegalArgumentException("onError callback can't be null");
    }

    public <E extends RealmModel> void createAllFromJson(Class<E> cls, String str) {
        if (cls == null || str == null || str.length() == 0) {
            return;
        }
        try {
            createAllFromJson(cls, new JSONArray(str));
        } catch (JSONException e) {
            throw new RealmException("Could not create JSON array from string", e);
        }
    }

    public <E extends RealmModel> E createOrUpdateObjectFromJson(Class<E> cls, String str) {
        if (cls == null || str == null || str.length() == 0) {
            return null;
        }
        checkIfValid();
        checkHasPrimaryKey(cls);
        try {
            return (E) createOrUpdateObjectFromJson(cls, new JSONObject(str));
        } catch (JSONException e) {
            throw new RealmException("Could not create Json object from string", e);
        }
    }

    public void insert(RealmModel realmModel) {
        checkIfValidAndInTransaction();
        if (realmModel != null) {
            this.configuration.getSchemaMediator().insert(this, realmModel, new HashMap());
            return;
        }
        throw new IllegalArgumentException("Null object cannot be inserted into Realm.");
    }

    public void insertOrUpdate(RealmModel realmModel) {
        checkIfValidAndInTransaction();
        if (realmModel != null) {
            this.configuration.getSchemaMediator().insertOrUpdate(this, realmModel, new HashMap());
            return;
        }
        throw new IllegalArgumentException("Null object cannot be inserted into Realm.");
    }

    public <E extends RealmModel> void createOrUpdateAllFromJson(Class<E> cls, String str) {
        if (cls == null || str == null || str.length() == 0) {
            return;
        }
        checkIfValid();
        checkHasPrimaryKey(cls);
        try {
            createOrUpdateAllFromJson(cls, new JSONArray(str));
        } catch (JSONException e) {
            throw new RealmException("Could not create JSON array from string", e);
        }
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
                return new RealmAsyncTaskImpl(realmThreadPoolExecutor.submitTransaction(new AnonymousClass1(configuration, transaction, zCanDeliverNotification, onSuccess, realmNotifier, onError)), realmThreadPoolExecutor);
            }
            throw new IllegalStateException("Write transactions on a frozen Realm is not allowed.");
        }
        throw new IllegalArgumentException("Transaction should not be null");
    }

    @Nullable
    public <E extends RealmModel> E createObjectFromJson(Class<E> cls, InputStream inputStream) throws IOException {
        E e;
        Scanner fullStringScanner = null;
        if (cls == null || inputStream == null) {
            return null;
        }
        checkIfValid();
        try {
            if (OsObjectStore.getPrimaryKeyForObject(this.sharedRealm, this.configuration.getSchemaMediator().getSimpleClassName(cls)) != null) {
                try {
                    fullStringScanner = getFullStringScanner(inputStream);
                    e = (E) this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, new JSONObject(fullStringScanner.next()), false);
                    fullStringScanner.close();
                } catch (JSONException e2) {
                    throw new RealmException("Failed to read JSON", e2);
                }
            } else {
                JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
                try {
                    e = (E) this.configuration.getSchemaMediator().createUsingJsonStream(cls, this, jsonReader);
                } finally {
                    jsonReader.close();
                }
            }
            return e;
        } catch (Throwable th) {
            if (fullStringScanner != null) {
                fullStringScanner.close();
            }
            throw th;
        }
    }

    public <E extends RealmModel> void createAllFromJson(Class<E> cls, InputStream inputStream) throws IOException {
        if (cls == null || inputStream == null) {
            return;
        }
        checkIfValid();
        JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                this.configuration.getSchemaMediator().createUsingJsonStream(cls, this, jsonReader);
            }
            jsonReader.endArray();
        } finally {
            jsonReader.close();
        }
    }

    public <E extends RealmModel> E copyFromRealm(E e) {
        return (E) copyFromRealm(e, Integer.MAX_VALUE);
    }

    public <E extends RealmModel> E createObject(Class<E> cls, @Nullable Object obj) {
        checkIfValid();
        RealmProxyMediator schemaMediator = this.configuration.getSchemaMediator();
        if (!schemaMediator.isEmbedded(cls)) {
            return (E) this.configuration.getSchemaMediator().newInstance(cls, this, OsObject.createWithPrimaryKey(this.schema.getTable((Class<? extends RealmModel>) cls), obj), this.schema.getColumnInfo((Class<? extends RealmModel>) cls), true, Collections.emptyList());
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("This class is marked embedded. Use `createEmbeddedObject(class, parent, property)` instead:  ");
        sbM.append(schemaMediator.getSimpleClassName(cls));
        throw new IllegalArgumentException(sbM.toString());
    }

    public <E extends RealmModel> E createOrUpdateObjectFromJson(Class<E> cls, InputStream inputStream) {
        Scanner fullStringScanner = null;
        if (cls == null || inputStream == null) {
            return null;
        }
        checkIfValid();
        checkHasPrimaryKey(cls);
        try {
            try {
                fullStringScanner = getFullStringScanner(inputStream);
                E e = (E) createOrUpdateObjectFromJson(cls, new JSONObject(fullStringScanner.next()));
                fullStringScanner.close();
                return e;
            } catch (JSONException e2) {
                throw new RealmException("Failed to read JSON", e2);
            }
        } catch (Throwable th) {
            if (fullStringScanner != null) {
                fullStringScanner.close();
            }
            throw th;
        }
    }

    public <E extends RealmModel> E copyFromRealm(E e, int i) {
        checkMaxDepth(i);
        checkValidObjectForDetach(e);
        return (E) createDetachedCopy(e, i, new HashMap());
    }

    public <E extends RealmModel> void createOrUpdateAllFromJson(Class<E> cls, InputStream inputStream) {
        if (cls == null || inputStream == null) {
            return;
        }
        checkIfValid();
        checkHasPrimaryKey(cls);
        Scanner fullStringScanner = null;
        try {
            try {
                fullStringScanner = getFullStringScanner(inputStream);
                JSONArray jSONArray = new JSONArray(fullStringScanner.next());
                for (int i = 0; i < jSONArray.length(); i++) {
                    this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONArray.getJSONObject(i), true);
                }
                fullStringScanner.close();
            } catch (JSONException e) {
                throw new RealmException("Failed to read JSON", e);
            }
        } catch (Throwable th) {
            if (fullStringScanner != null) {
                fullStringScanner.close();
            }
            throw th;
        }
    }

    public static void migrateRealm(RealmConfiguration realmConfiguration) throws FileNotFoundException {
        migrateRealm(realmConfiguration, null);
    }

    private Realm(OsSharedRealm osSharedRealm) {
        super(osSharedRealm);
        this.schema = new ImmutableRealmSchema(this, new ColumnIndices(this.configuration.getSchemaMediator(), osSharedRealm.getSchemaInfo()));
    }
}
