package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.internal.OsObjectStore;
import io.realm.internal.Table;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
class MutableRealmSchema extends RealmSchema {
    public MutableRealmSchema(BaseRealm baseRealm) {
        super(baseRealm, null);
    }

    private String checkAndGetTableNameFromClassName(String str) {
        int length = str.length();
        int i = Table.CLASS_NAME_MAX_LENGTH;
        if (length <= i) {
            return Table.getTableNameForClass(str);
        }
        throw new IllegalArgumentException(String.format(Locale.US, "Class name is too long. Limit is %1$d characters: %2$s", Integer.valueOf(i), Integer.valueOf(str.length())));
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema create(String str) {
        checkNotEmpty(str, "Null or empty class names are not allowed");
        String tableNameForClass = Table.getTableNameForClass(str);
        int length = str.length();
        int i = Table.CLASS_NAME_MAX_LENGTH;
        if (length > i) {
            throw new IllegalArgumentException(String.format(Locale.US, "Class name is too long. Limit is %1$d characters: %2$s", Integer.valueOf(i), Integer.valueOf(str.length())));
        }
        BaseRealm baseRealm = this.realm;
        return new MutableRealmObjectSchema(baseRealm, this, baseRealm.sharedRealm.createTable(tableNameForClass));
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema createWithPrimaryKeyField(String str, String str2, Class<?> cls, FieldAttribute... fieldAttributeArr) {
        RealmFieldType realmFieldType;
        checkNotEmpty(str, "Null or empty class names are not allowed");
        RealmObjectSchema.checkLegalName(str2);
        String strCheckAndGetTableNameFromClassName = checkAndGetTableNameFromClassName(str);
        RealmObjectSchema.FieldMetaData fieldMetaData = RealmObjectSchema.SUPPORTED_LIST_SIMPLE_FIELDS.get(cls);
        if (fieldMetaData == null || !((realmFieldType = fieldMetaData.fieldType) == RealmFieldType.STRING || realmFieldType == RealmFieldType.INTEGER || realmFieldType == RealmFieldType.OBJECT_ID)) {
            throw new IllegalArgumentException(String.format("Realm doesn't support primary key field type '%s'.", cls));
        }
        boolean z = MutableRealmObjectSchema.containsAttribute(fieldAttributeArr, FieldAttribute.REQUIRED) ? false : fieldMetaData.defaultNullable;
        BaseRealm baseRealm = this.realm;
        return new MutableRealmObjectSchema(baseRealm, this, baseRealm.sharedRealm.createTableWithPrimaryKey(strCheckAndGetTableNameFromClassName, str2, fieldMetaData.fieldType, z));
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema get(String str) {
        checkNotEmpty(str, "Null or empty class names are not allowed");
        String tableNameForClass = Table.getTableNameForClass(str);
        if (!this.realm.sharedRealm.hasTable(tableNameForClass)) {
            return null;
        }
        return new MutableRealmObjectSchema(this.realm, this, this.realm.sharedRealm.getTable(tableNameForClass));
    }

    @Override // io.realm.RealmSchema
    public Set<RealmObjectSchema> getAll() {
        String[] tablesNames = this.realm.sharedRealm.getTablesNames();
        LinkedHashSet linkedHashSet = new LinkedHashSet(tablesNames.length);
        for (String str : tablesNames) {
            RealmObjectSchema realmObjectSchema = get(Table.getClassNameForTable(str));
            if (realmObjectSchema != null) {
                linkedHashSet.add(realmObjectSchema);
            }
        }
        return linkedHashSet;
    }

    @Override // io.realm.RealmSchema
    public void remove(String str) {
        this.realm.checkNotInSync();
        checkNotEmpty(str, "Null or empty class names are not allowed");
        String tableNameForClass = Table.getTableNameForClass(str);
        if (!OsObjectStore.deleteTableForObject(this.realm.sharedRealm, str)) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Cannot remove class because it is not in this Realm: ", str));
        }
        removeFromClassNameToSchemaMap(tableNameForClass);
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema rename(String str, String str2) {
        this.realm.checkNotInSync();
        checkNotEmpty(str, "Class names cannot be empty or null");
        checkNotEmpty(str2, "Class names cannot be empty or null");
        String tableNameForClass = Table.getTableNameForClass(str);
        String tableNameForClass2 = Table.getTableNameForClass(str2);
        String strM = Insets$$ExternalSyntheticOutline0.m("Cannot rename class because it doesn't exist in this Realm: ", str);
        if (!this.realm.sharedRealm.hasTable(Table.getTableNameForClass(str))) {
            throw new IllegalArgumentException(strM);
        }
        if (this.realm.sharedRealm.hasTable(tableNameForClass2)) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m(str, " cannot be renamed because the new class already exists: ", str2));
        }
        this.realm.sharedRealm.renameTable(tableNameForClass, tableNameForClass2);
        Table table = this.realm.sharedRealm.getTable(tableNameForClass2);
        RealmObjectSchema realmObjectSchemaRemoveFromClassNameToSchemaMap = removeFromClassNameToSchemaMap(tableNameForClass);
        if (realmObjectSchemaRemoveFromClassNameToSchemaMap == null || !realmObjectSchemaRemoveFromClassNameToSchemaMap.table.isValid() || !realmObjectSchemaRemoveFromClassNameToSchemaMap.getClassName().equals(str2)) {
            realmObjectSchemaRemoveFromClassNameToSchemaMap = new MutableRealmObjectSchema(this.realm, this, table);
        }
        putToClassNameToSchemaMap(tableNameForClass2, realmObjectSchemaRemoveFromClassNameToSchemaMap);
        return realmObjectSchemaRemoveFromClassNameToSchemaMap;
    }
}
