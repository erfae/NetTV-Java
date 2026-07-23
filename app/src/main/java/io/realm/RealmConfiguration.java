package io.realm;

import android.content.Context;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.annotations.RealmModule;
import io.realm.coroutines.FlowFactory;
import io.realm.coroutines.RealmFlowFactory;
import io.realm.exceptions.RealmException;
import io.realm.internal.OsRealmConfig;
import io.realm.internal.RealmCore;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Util;
import io.realm.internal.modules.CompositeMediator;
import io.realm.internal.modules.FilterableMediator;
import io.realm.rx.RealmObservableFactory;
import io.realm.rx.RxObservableFactory;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public class RealmConfiguration {
    private static final Object DEFAULT_MODULE;
    public static final RealmProxyMediator DEFAULT_MODULE_MEDIATOR;
    public static final String DEFAULT_REALM_NAME = "default.realm";
    private final boolean allowQueriesOnUiThread;
    private final boolean allowWritesOnUiThread;
    private final String assetFilePath;
    private final String canonicalPath;
    private final CompactOnLaunchCallback compactOnLaunch;
    private final boolean deleteRealmIfMigrationNeeded;
    private final OsRealmConfig.Durability durability;
    private final FlowFactory flowFactory;
    private final Realm.Transaction initialDataTransaction;
    private final boolean isRecoveryConfiguration = false;
    private final byte[] key;
    private final long maxNumberOfActiveVersions;
    private final RealmMigration migration;
    private final boolean readOnly;
    private final File realmDirectory;
    private final String realmFileName;
    private final RxObservableFactory rxObservableFactory;
    private final RealmProxyMediator schemaMediator;
    private final long schemaVersion;

    public static class Builder {
        private boolean allowQueriesOnUiThread;
        private boolean allowWritesOnUiThread;
        private String assetFilePath;
        private CompactOnLaunchCallback compactOnLaunch;
        private HashSet<Class<? extends RealmModel>> debugSchema;
        private boolean deleteRealmIfMigrationNeeded;
        private File directory;
        private OsRealmConfig.Durability durability;
        private boolean excludeDebugSchema;
        private String fileName;

        @Nullable
        private FlowFactory flowFactory;
        private Realm.Transaction initialDataTransaction;
        private byte[] key;
        private long maxNumberOfActiveVersions;
        private RealmMigration migration;
        private HashSet<Object> modules;
        private boolean readOnly;

        @Nullable
        private RxObservableFactory rxFactory;
        private long schemaVersion;

        public Builder() {
            this(BaseRealm.applicationContext);
        }

        private void checkModule(Object obj) {
            if (obj.getClass().isAnnotationPresent(RealmModule.class)) {
                return;
            }
            throw new IllegalArgumentException(obj.getClass().getCanonicalName() + " is not a RealmModule. Add @RealmModule to the class definition.");
        }

        private void initializeBuilder(Context context) {
            this.directory = context.getFilesDir();
            this.fileName = "default.realm";
            this.key = null;
            this.schemaVersion = 0L;
            this.migration = null;
            this.deleteRealmIfMigrationNeeded = false;
            this.durability = OsRealmConfig.Durability.FULL;
            this.readOnly = false;
            this.compactOnLaunch = null;
            if (RealmConfiguration.DEFAULT_MODULE != null) {
                this.modules.add(RealmConfiguration.DEFAULT_MODULE);
            }
            this.allowWritesOnUiThread = false;
            this.allowQueriesOnUiThread = true;
        }

        public final Builder addModule(Object obj) {
            if (obj != null) {
                checkModule(obj);
                this.modules.add(obj);
            }
            return this;
        }

        public Builder allowQueriesOnUiThread(boolean z) {
            this.allowQueriesOnUiThread = z;
            return this;
        }

        public Builder allowWritesOnUiThread(boolean z) {
            this.allowWritesOnUiThread = z;
            return this;
        }

        public Builder assetFile(String str) {
            if (Util.isEmptyString(str)) {
                throw new IllegalArgumentException("A non-empty asset file path must be provided");
            }
            if (this.durability == OsRealmConfig.Durability.MEM_ONLY) {
                throw new RealmException("Realm can not use in-memory configuration if asset file is present.");
            }
            if (this.deleteRealmIfMigrationNeeded) {
                throw new IllegalStateException("Realm cannot use an asset file when previously configured to clear its schema in migration by calling deleteRealmIfMigrationNeeded().");
            }
            this.assetFilePath = str;
            return this;
        }

        public RealmConfiguration build() {
            if (this.readOnly) {
                if (this.initialDataTransaction != null) {
                    throw new IllegalStateException("This Realm is marked as read-only. Read-only Realms cannot use initialData(Realm.Transaction).");
                }
                if (this.assetFilePath == null) {
                    throw new IllegalStateException("Only Realms provided using 'assetFile(path)' can be marked read-only. No such Realm was provided.");
                }
                if (this.deleteRealmIfMigrationNeeded) {
                    throw new IllegalStateException("'deleteRealmIfMigrationNeeded()' and read-only Realms cannot be combined");
                }
                if (this.compactOnLaunch != null) {
                    throw new IllegalStateException("'compactOnLaunch()' and read-only Realms cannot be combined");
                }
            }
            if (this.rxFactory == null && Util.isRxJavaAvailable()) {
                this.rxFactory = new RealmObservableFactory(true);
            }
            if (this.flowFactory == null && Util.isCoroutinesAvailable()) {
                this.flowFactory = new RealmFlowFactory(Boolean.TRUE);
            }
            return new RealmConfiguration(new File(this.directory, this.fileName), this.assetFilePath, this.key, this.schemaVersion, this.migration, this.deleteRealmIfMigrationNeeded, this.durability, RealmConfiguration.createSchemaMediator(this.modules, this.debugSchema, this.excludeDebugSchema), this.rxFactory, this.flowFactory, this.initialDataTransaction, this.readOnly, this.compactOnLaunch, this.maxNumberOfActiveVersions, this.allowWritesOnUiThread, this.allowQueriesOnUiThread);
        }

        public Builder compactOnLaunch() {
            return compactOnLaunch(new DefaultCompactOnLaunchCallback());
        }

        public Builder deleteRealmIfMigrationNeeded() {
            String str = this.assetFilePath;
            if (str != null && str.length() != 0) {
                throw new IllegalStateException("Realm cannot clear its schema when previously configured to use an asset file by calling assetFile().");
            }
            this.deleteRealmIfMigrationNeeded = true;
            return this;
        }

        public Builder directory(File file) {
            if (file == null) {
                throw new IllegalArgumentException("Non-null 'dir' required.");
            }
            if (file.isFile()) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("'dir' is a file, not a directory: ");
                sbM.append(file.getAbsolutePath());
                sbM.append(".");
                throw new IllegalArgumentException(sbM.toString());
            }
            if (!file.exists() && !file.mkdirs()) {
                StringBuilder sbM2 = Insets$$ExternalSyntheticOutline0.m("Could not create the specified directory: ");
                sbM2.append(file.getAbsolutePath());
                sbM2.append(".");
                throw new IllegalArgumentException(sbM2.toString());
            }
            if (file.canWrite()) {
                this.directory = file;
                return this;
            }
            StringBuilder sbM3 = Insets$$ExternalSyntheticOutline0.m("Realm directory is not writable: ");
            sbM3.append(file.getAbsolutePath());
            sbM3.append(".");
            throw new IllegalArgumentException(sbM3.toString());
        }

        public Builder encryptionKey(byte[] bArr) {
            if (bArr == null) {
                throw new IllegalArgumentException("A non-null key must be provided");
            }
            if (bArr.length != 64) {
                throw new IllegalArgumentException(String.format(Locale.US, "The provided key must be %s bytes. Yours was: %s", 64, Integer.valueOf(bArr.length)));
            }
            this.key = Arrays.copyOf(bArr, bArr.length);
            return this;
        }

        public Builder flowFactory(@Nonnull FlowFactory flowFactory) {
            if (flowFactory == null) {
                throw new IllegalArgumentException("The provided Flow factory must not be null.");
            }
            this.flowFactory = flowFactory;
            return this;
        }

        public Builder inMemory() {
            if (!Util.isEmptyString(this.assetFilePath)) {
                throw new RealmException("Realm can not use in-memory configuration if asset file is present.");
            }
            this.durability = OsRealmConfig.Durability.MEM_ONLY;
            return this;
        }

        public Builder initialData(Realm.Transaction transaction) {
            this.initialDataTransaction = transaction;
            return this;
        }

        public Builder maxNumberOfActiveVersions(long j) {
            if (j < 1) {
                throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Only positive numbers above 0 are allowed. Yours was: ", j));
            }
            this.maxNumberOfActiveVersions = j;
            return this;
        }

        public Builder migration(RealmMigration realmMigration) {
            if (realmMigration == null) {
                throw new IllegalArgumentException("A non-null migration must be provided");
            }
            this.migration = realmMigration;
            return this;
        }

        public Builder modules(Object obj, Object... objArr) {
            this.modules.clear();
            addModule(obj);
            if (objArr != null) {
                for (Object obj2 : objArr) {
                    addModule(obj2);
                }
            }
            return this;
        }

        public Builder name(String str) {
            if (str == null || str.isEmpty()) {
                throw new IllegalArgumentException("A non-empty filename must be provided");
            }
            this.fileName = str;
            return this;
        }

        public Builder readOnly() {
            this.readOnly = true;
            return this;
        }

        public Builder rxFactory(@Nonnull RxObservableFactory rxObservableFactory) {
            if (rxObservableFactory == null) {
                throw new IllegalArgumentException("The provided Rx Observable factory must not be null.");
            }
            this.rxFactory = rxObservableFactory;
            return this;
        }

        public Builder schemaVersion(long j) {
            if (j < 0) {
                throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Realm schema version numbers must be 0 (zero) or higher. Yours was: ", j));
            }
            this.schemaVersion = j;
            return this;
        }

        public Builder(Context context) {
            this.modules = new HashSet<>();
            this.debugSchema = new HashSet<>();
            this.excludeDebugSchema = false;
            this.maxNumberOfActiveVersions = Long.MAX_VALUE;
            if (context == null) {
                throw new IllegalStateException("Call `Realm.init(Context)` before creating a RealmConfiguration");
            }
            RealmCore.loadLibrary(context);
            initializeBuilder(context);
        }

        public Builder compactOnLaunch(CompactOnLaunchCallback compactOnLaunchCallback) {
            if (compactOnLaunchCallback == null) {
                throw new IllegalArgumentException("A non-null compactOnLaunch must be provided");
            }
            this.compactOnLaunch = compactOnLaunchCallback;
            return this;
        }
    }

    static {
        Object defaultModule = Realm.getDefaultModule();
        DEFAULT_MODULE = defaultModule;
        if (defaultModule == null) {
            DEFAULT_MODULE_MEDIATOR = null;
            return;
        }
        RealmProxyMediator moduleMediator = getModuleMediator(defaultModule.getClass().getCanonicalName());
        if (!moduleMediator.transformerApplied()) {
            throw new ExceptionInInitializerError("RealmTransformer doesn't seem to be applied. Please update the project configuration to use the Realm Gradle plugin. See https://docs.mongodb.com/realm/sdk/android/install/#customize-dependecies-defined-by-the-realm-gradle-plugin");
        }
        DEFAULT_MODULE_MEDIATOR = moduleMediator;
    }

    public RealmConfiguration(File file, @Nullable String str, @Nullable byte[] bArr, long j, @Nullable RealmMigration realmMigration, boolean z, OsRealmConfig.Durability durability, RealmProxyMediator realmProxyMediator, @Nullable RxObservableFactory rxObservableFactory, @Nullable FlowFactory flowFactory, @Nullable Realm.Transaction transaction, boolean z2, @Nullable CompactOnLaunchCallback compactOnLaunchCallback, long j2, boolean z3, boolean z4) {
        this.realmDirectory = file.getParentFile();
        this.realmFileName = file.getName();
        this.canonicalPath = file.getAbsolutePath();
        this.assetFilePath = str;
        this.key = bArr;
        this.schemaVersion = j;
        this.migration = realmMigration;
        this.deleteRealmIfMigrationNeeded = z;
        this.durability = durability;
        this.schemaMediator = realmProxyMediator;
        this.rxObservableFactory = rxObservableFactory;
        this.flowFactory = flowFactory;
        this.initialDataTransaction = transaction;
        this.readOnly = z2;
        this.compactOnLaunch = compactOnLaunchCallback;
        this.maxNumberOfActiveVersions = j2;
        this.allowWritesOnUiThread = z3;
        this.allowQueriesOnUiThread = z4;
    }

    public static RealmProxyMediator createSchemaMediator(Set<Object> set, Set<Class<? extends RealmModel>> set2, boolean z) {
        if (set2.size() > 0) {
            return new FilterableMediator(DEFAULT_MODULE_MEDIATOR, set2, z);
        }
        if (set.size() == 1) {
            return getModuleMediator(set.iterator().next().getClass().getCanonicalName());
        }
        RealmProxyMediator[] realmProxyMediatorArr = new RealmProxyMediator[set.size()];
        int i = 0;
        Iterator<Object> it = set.iterator();
        while (it.hasNext()) {
            realmProxyMediatorArr[i] = getModuleMediator(it.next().getClass().getCanonicalName());
            i++;
        }
        return new CompositeMediator(realmProxyMediatorArr);
    }

    private static RealmProxyMediator getModuleMediator(String str) {
        String[] strArrSplit = str.split("\\.");
        String str2 = String.format(Locale.US, "io.realm.%s%s", strArrSplit[strArrSplit.length - 1], "Mediator");
        try {
            Constructor<?> constructor = Class.forName(str2).getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            return (RealmProxyMediator) constructor.newInstance(new Object[0]);
        } catch (ClassNotFoundException e) {
            throw new RealmException(Insets$$ExternalSyntheticOutline0.m("Could not find ", str2), e);
        } catch (IllegalAccessException e2) {
            throw new RealmException(Insets$$ExternalSyntheticOutline0.m("Could not create an instance of ", str2), e2);
        } catch (InstantiationException e3) {
            throw new RealmException(Insets$$ExternalSyntheticOutline0.m("Could not create an instance of ", str2), e3);
        } catch (InvocationTargetException e4) {
            throw new RealmException(Insets$$ExternalSyntheticOutline0.m("Could not create an instance of ", str2), e4);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RealmConfiguration realmConfiguration = (RealmConfiguration) obj;
        if (this.schemaVersion != realmConfiguration.schemaVersion || this.deleteRealmIfMigrationNeeded != realmConfiguration.deleteRealmIfMigrationNeeded || this.readOnly != realmConfiguration.readOnly || this.isRecoveryConfiguration != realmConfiguration.isRecoveryConfiguration) {
            return false;
        }
        File file = this.realmDirectory;
        if (file == null ? realmConfiguration.realmDirectory != null : !file.equals(realmConfiguration.realmDirectory)) {
            return false;
        }
        String str = this.realmFileName;
        if (str == null ? realmConfiguration.realmFileName != null : !str.equals(realmConfiguration.realmFileName)) {
            return false;
        }
        if (!this.canonicalPath.equals(realmConfiguration.canonicalPath)) {
            return false;
        }
        String str2 = this.assetFilePath;
        if (str2 == null ? realmConfiguration.assetFilePath != null : !str2.equals(realmConfiguration.assetFilePath)) {
            return false;
        }
        if (!Arrays.equals(this.key, realmConfiguration.key)) {
            return false;
        }
        RealmMigration realmMigration = this.migration;
        if (realmMigration == null ? realmConfiguration.migration != null : !realmMigration.equals(realmConfiguration.migration)) {
            return false;
        }
        if (this.durability != realmConfiguration.durability || !this.schemaMediator.equals(realmConfiguration.schemaMediator)) {
            return false;
        }
        RxObservableFactory rxObservableFactory = this.rxObservableFactory;
        if (rxObservableFactory == null ? realmConfiguration.rxObservableFactory != null : !rxObservableFactory.equals(realmConfiguration.rxObservableFactory)) {
            return false;
        }
        Realm.Transaction transaction = this.initialDataTransaction;
        if (transaction == null ? realmConfiguration.initialDataTransaction != null : !transaction.equals(realmConfiguration.initialDataTransaction)) {
            return false;
        }
        CompactOnLaunchCallback compactOnLaunchCallback = this.compactOnLaunch;
        if (compactOnLaunchCallback == null ? realmConfiguration.compactOnLaunch == null : compactOnLaunchCallback.equals(realmConfiguration.compactOnLaunch)) {
            return this.maxNumberOfActiveVersions == realmConfiguration.maxNumberOfActiveVersions;
        }
        return false;
    }

    @Nullable
    public String getAssetFilePath() {
        return this.assetFilePath;
    }

    public CompactOnLaunchCallback getCompactOnLaunchCallback() {
        return this.compactOnLaunch;
    }

    public OsRealmConfig.Durability getDurability() {
        return this.durability;
    }

    public byte[] getEncryptionKey() {
        byte[] bArr = this.key;
        if (bArr == null) {
            return null;
        }
        return Arrays.copyOf(bArr, bArr.length);
    }

    public FlowFactory getFlowFactory() {
        FlowFactory flowFactory = this.flowFactory;
        if (flowFactory != null) {
            return flowFactory;
        }
        throw new UnsupportedOperationException("The coroutines framework is missing from the classpath. Remember to add it as an implementation dependency. See https://github.com/Kotlin/kotlinx.coroutines#android for more details");
    }

    public final Realm.Transaction getInitialDataTransaction() {
        return this.initialDataTransaction;
    }

    public long getMaxNumberOfActiveVersions() {
        return this.maxNumberOfActiveVersions;
    }

    public RealmMigration getMigration() {
        return this.migration;
    }

    public String getPath() {
        return this.canonicalPath;
    }

    public File getRealmDirectory() {
        return this.realmDirectory;
    }

    public String getRealmFileName() {
        return this.realmFileName;
    }

    public Set<Class<? extends RealmModel>> getRealmObjectClasses() {
        return this.schemaMediator.getModelClasses();
    }

    public RxObservableFactory getRxFactory() {
        RxObservableFactory rxObservableFactory = this.rxObservableFactory;
        if (rxObservableFactory != null) {
            return rxObservableFactory;
        }
        throw new UnsupportedOperationException("RxJava seems to be missing from the classpath. Remember to add it as an implementation dependency. See https://github.com/realm/realm-java/tree/master/examples/rxJavaExample for more details.");
    }

    public final RealmProxyMediator getSchemaMediator() {
        return this.schemaMediator;
    }

    public long getSchemaVersion() {
        return this.schemaVersion;
    }

    public boolean hasAssetFile() {
        return !Util.isEmptyString(this.assetFilePath);
    }

    public int hashCode() {
        File file = this.realmDirectory;
        int iHashCode = (file != null ? file.hashCode() : 0) * 31;
        String str = this.realmFileName;
        int iM = Insets$$ExternalSyntheticOutline0.m(this.canonicalPath, (iHashCode + (str != null ? str.hashCode() : 0)) * 31, 31);
        String str2 = this.assetFilePath;
        int iHashCode2 = (Arrays.hashCode(this.key) + ((iM + (str2 != null ? str2.hashCode() : 0)) * 31)) * 31;
        long j = this.schemaVersion;
        int i = (iHashCode2 + ((int) (j ^ (j >>> 32)))) * 31;
        RealmMigration realmMigration = this.migration;
        int iHashCode3 = (this.schemaMediator.hashCode() + ((this.durability.hashCode() + ((((i + (realmMigration != null ? realmMigration.hashCode() : 0)) * 31) + (this.deleteRealmIfMigrationNeeded ? 1 : 0)) * 31)) * 31)) * 31;
        RxObservableFactory rxObservableFactory = this.rxObservableFactory;
        int iHashCode4 = (iHashCode3 + (rxObservableFactory != null ? rxObservableFactory.hashCode() : 0)) * 31;
        Realm.Transaction transaction = this.initialDataTransaction;
        int iHashCode5 = (((iHashCode4 + (transaction != null ? transaction.hashCode() : 0)) * 31) + (this.readOnly ? 1 : 0)) * 31;
        CompactOnLaunchCallback compactOnLaunchCallback = this.compactOnLaunch;
        int iHashCode6 = (((iHashCode5 + (compactOnLaunchCallback != null ? compactOnLaunchCallback.hashCode() : 0)) * 31) + (this.isRecoveryConfiguration ? 1 : 0)) * 31;
        long j2 = this.maxNumberOfActiveVersions;
        return iHashCode6 + ((int) ((j2 >>> 32) ^ j2));
    }

    public boolean isAllowQueriesOnUiThread() {
        return this.allowQueriesOnUiThread;
    }

    public boolean isAllowWritesOnUiThread() {
        return this.allowWritesOnUiThread;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public boolean isRecoveryConfiguration() {
        return this.isRecoveryConfiguration;
    }

    public final boolean realmExists() {
        return new File(this.canonicalPath).exists();
    }

    public boolean shouldDeleteRealmIfMigrationNeeded() {
        return this.deleteRealmIfMigrationNeeded;
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("realmDirectory: ");
        File file = this.realmDirectory;
        sbM.append(file != null ? file.toString() : "");
        sbM.append("\n");
        sbM.append("realmFileName : ");
        sbM.append(this.realmFileName);
        sbM.append("\n");
        sbM.append("canonicalPath: ");
        Insets$$ExternalSyntheticOutline0.m29m(sbM, this.canonicalPath, "\n", "key: ", "[length: ");
        sbM.append(this.key == null ? 0 : 64);
        sbM.append("]");
        sbM.append("\n");
        sbM.append("schemaVersion: ");
        sbM.append(Long.toString(this.schemaVersion));
        sbM.append("\n");
        sbM.append("migration: ");
        sbM.append(this.migration);
        sbM.append("\n");
        sbM.append("deleteRealmIfMigrationNeeded: ");
        sbM.append(this.deleteRealmIfMigrationNeeded);
        sbM.append("\n");
        sbM.append("durability: ");
        sbM.append(this.durability);
        sbM.append("\n");
        sbM.append("schemaMediator: ");
        sbM.append(this.schemaMediator);
        sbM.append("\n");
        sbM.append("readOnly: ");
        sbM.append(this.readOnly);
        sbM.append("\n");
        sbM.append("compactOnLaunch: ");
        sbM.append(this.compactOnLaunch);
        sbM.append("\n");
        sbM.append("maxNumberOfActiveVersions: ");
        sbM.append(this.maxNumberOfActiveVersions);
        return sbM.toString();
    }
}
