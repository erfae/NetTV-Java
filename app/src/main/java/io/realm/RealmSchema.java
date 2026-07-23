package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.internal.ColumnIndices;
import io.realm.internal.ColumnInfo;
import io.realm.internal.Table;
import io.realm.internal.Util;
import io.realm.internal.objectstore.OsKeyPathMapping;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public abstract class RealmSchema {
    private final ColumnIndices columnIndices;
    public final BaseRealm realm;
    private final Map<String, Table> dynamicClassToTable = new HashMap();
    private final Map<Class<? extends RealmModel>, Table> classToTable = new HashMap();
    private final Map<Class<? extends RealmModel>, RealmObjectSchema> classToSchema = new HashMap();
    private final Map<String, RealmObjectSchema> dynamicClassToSchema = new HashMap();
    private OsKeyPathMapping keyPathMapping = null;

    public RealmSchema(BaseRealm baseRealm, @Nullable ColumnIndices columnIndices) {
        this.realm = baseRealm;
        this.columnIndices = columnIndices;
    }

    private void checkColumnKeys() {
        if (!(this.columnIndices != null)) {
            throw new IllegalStateException("Attempt to use column key before set.");
        }
    }

    private boolean isProxyClass(Class<? extends RealmModel> cls, Class<? extends RealmModel> cls2) {
        return cls.equals(cls2);
    }

    public final void checkNotEmpty(String str, String str2) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException(str2);
        }
    }

    public boolean contains(String str) {
        return this.realm.sharedRealm.hasTable(Table.getTableNameForClass(str));
    }

    public abstract RealmObjectSchema create(String str);

    public void createKeyPathMapping() {
        this.keyPathMapping = new OsKeyPathMapping(this.realm.sharedRealm.getNativePtr());
    }

    public abstract RealmObjectSchema createWithPrimaryKeyField(String str, String str2, Class<?> cls, FieldAttribute... fieldAttributeArr);

    @Nullable
    public abstract RealmObjectSchema get(String str);

    public abstract Set<RealmObjectSchema> getAll();

    public final ColumnInfo getColumnInfo(Class<? extends RealmModel> cls) {
        checkColumnKeys();
        return this.columnIndices.getColumnInfo(cls);
    }

    public final OsKeyPathMapping getKeyPathMapping() {
        return this.keyPathMapping;
    }

    public final RealmObjectSchema getSchemaForClass(Class<? extends RealmModel> cls) {
        RealmObjectSchema realmObjectSchema = this.classToSchema.get(cls);
        if (realmObjectSchema != null) {
            return realmObjectSchema;
        }
        Class<? extends RealmModel> originalModelClass = Util.getOriginalModelClass(cls);
        if (isProxyClass(originalModelClass, cls)) {
            realmObjectSchema = this.classToSchema.get(originalModelClass);
        }
        if (realmObjectSchema == null) {
            ImmutableRealmObjectSchema immutableRealmObjectSchema = new ImmutableRealmObjectSchema(this.realm, this, getTable(cls), getColumnInfo(originalModelClass));
            this.classToSchema.put(originalModelClass, immutableRealmObjectSchema);
            realmObjectSchema = immutableRealmObjectSchema;
        }
        if (isProxyClass(originalModelClass, cls)) {
            this.classToSchema.put(cls, realmObjectSchema);
        }
        return realmObjectSchema;
    }

    public final Table getTable(String str) {
        String tableNameForClass = Table.getTableNameForClass(str);
        Table table = this.dynamicClassToTable.get(tableNameForClass);
        if (table != null) {
            return table;
        }
        Table table2 = this.realm.sharedRealm.getTable(tableNameForClass);
        this.dynamicClassToTable.put(tableNameForClass, table2);
        return table2;
    }

    public final void putToClassNameToSchemaMap(String str, RealmObjectSchema realmObjectSchema) {
        this.dynamicClassToSchema.put(str, realmObjectSchema);
    }

    public final void refresh() {
        ColumnIndices columnIndices = this.columnIndices;
        if (columnIndices != null) {
            columnIndices.refresh();
        }
        this.dynamicClassToTable.clear();
        this.classToTable.clear();
        this.classToSchema.clear();
        this.dynamicClassToSchema.clear();
    }

    public abstract void remove(String str);

    public final RealmObjectSchema removeFromClassNameToSchemaMap(String str) {
        return this.dynamicClassToSchema.remove(str);
    }

    public abstract RealmObjectSchema rename(String str, String str2);

    public final ColumnInfo getColumnInfo(String str) {
        checkColumnKeys();
        return this.columnIndices.getColumnInfo(str);
    }

    public final Table getTable(Class<? extends RealmModel> cls) {
        Table table = this.classToTable.get(cls);
        if (table != null) {
            return table;
        }
        Class<? extends RealmModel> originalModelClass = Util.getOriginalModelClass(cls);
        if (isProxyClass(originalModelClass, cls)) {
            table = this.classToTable.get(originalModelClass);
        }
        if (table == null) {
            table = this.realm.sharedRealm.getTable(Table.getTableNameForClass(this.realm.getConfiguration().getSchemaMediator().getSimpleClassName(originalModelClass)));
            this.classToTable.put(originalModelClass, table);
        }
        if (isProxyClass(originalModelClass, cls)) {
            this.classToTable.put(cls, table);
        }
        return table;
    }

    public final RealmObjectSchema getSchemaForClass(String str) {
        String tableNameForClass = Table.getTableNameForClass(str);
        RealmObjectSchema realmObjectSchema = this.dynamicClassToSchema.get(tableNameForClass);
        if (realmObjectSchema != null && realmObjectSchema.table.isValid() && realmObjectSchema.getClassName().equals(str)) {
            return realmObjectSchema;
        }
        if (this.realm.sharedRealm.hasTable(tableNameForClass)) {
            BaseRealm baseRealm = this.realm;
            ImmutableRealmObjectSchema immutableRealmObjectSchema = new ImmutableRealmObjectSchema(baseRealm, this, baseRealm.sharedRealm.getTable(tableNameForClass));
            this.dynamicClassToSchema.put(tableNameForClass, immutableRealmObjectSchema);
            return immutableRealmObjectSchema;
        }
        throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("The class ", str, " doesn't exist in this Realm."));
    }
}
