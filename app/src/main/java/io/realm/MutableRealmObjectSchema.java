package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.internal.CheckedRow;
import io.realm.internal.OsObjectStore;
import io.realm.internal.OsResults;
import io.realm.internal.Table;
import io.realm.internal.Util;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
class MutableRealmObjectSchema extends RealmObjectSchema {

    /* JADX INFO: renamed from: io.realm.MutableRealmObjectSchema$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$io$realm$RealmFieldType;

        static {
            int[] iArr = new int[RealmFieldType.values().length];
            $SwitchMap$io$realm$RealmFieldType = iArr;
            try {
                iArr[RealmFieldType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public MutableRealmObjectSchema(BaseRealm baseRealm, RealmSchema realmSchema, Table table) {
        super(baseRealm, table, new RealmObjectSchema.DynamicColumnIndices(table));
    }

    private void addModifiers(String str, FieldAttribute[] fieldAttributeArr) {
        if (fieldAttributeArr != null) {
            boolean z = false;
            try {
                if (fieldAttributeArr.length > 0) {
                    if (containsAttribute(fieldAttributeArr, FieldAttribute.INDEXED)) {
                        addIndex(str);
                        z = true;
                    }
                    if (containsAttribute(fieldAttributeArr, FieldAttribute.PRIMARY_KEY)) {
                        addPrimaryKey(str);
                    }
                }
            } catch (Exception e) {
                long columnKey = getColumnKey(str);
                if (z) {
                    this.table.removeSearchIndex(columnKey);
                }
                throw ((RuntimeException) e);
            }
        }
    }

    private void checkAddPrimaryKeyForSync() {
        Objects.requireNonNull(this.realm.configuration);
    }

    private void checkEmpty(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Null or empty class names are not allowed");
        }
    }

    private void checkFieldNameIsAvailable(String str) {
        if (this.table.getColumnKey(str) == -1) {
            return;
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Field already exists in '");
        sbM.append(getClassName());
        sbM.append("': ");
        sbM.append(str);
        throw new IllegalArgumentException(sbM.toString());
    }

    private void checkForObjectStoreInvalidPrimaryKeyTypes(String str, Class<?> cls) {
        if (cls == Boolean.TYPE || cls == Boolean.class) {
            checkForObjectStoreInvalidPrimaryKeyTypes(str, RealmFieldType.BOOLEAN);
        }
        if (cls == Date.class) {
            checkForObjectStoreInvalidPrimaryKeyTypes(str, RealmFieldType.DATE);
        }
    }

    private void checkNewFieldName(String str) {
        RealmObjectSchema.checkLegalName(str);
        checkFieldNameIsAvailable(str);
    }

    public static boolean containsAttribute(FieldAttribute[] fieldAttributeArr, FieldAttribute fieldAttribute) {
        if (fieldAttributeArr != null && fieldAttributeArr.length != 0) {
            for (FieldAttribute fieldAttribute2 : fieldAttributeArr) {
                if (fieldAttribute2 == fieldAttribute) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addField(String str, Class<?> cls, FieldAttribute... fieldAttributeArr) throws Exception {
        RealmObjectSchema.FieldMetaData fieldMetaData = RealmObjectSchema.SUPPORTED_LIST_SIMPLE_FIELDS.get(cls);
        if (fieldMetaData == null) {
            if (RealmObjectSchema.SUPPORTED_LINKED_FIELDS.containsKey(cls)) {
                throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Use addRealmObjectField() instead to add fields that link to other RealmObjects: ", str));
            }
            if (RealmModel.class.isAssignableFrom(cls)) {
                throw new IllegalArgumentException(String.format(Locale.US, "Use 'addRealmObjectField()' instead to add fields that link to other RealmObjects: %s(%s)", str, cls));
            }
            throw new IllegalArgumentException(String.format(Locale.US, "Realm doesn't support this field type: %s(%s)", str, cls));
        }
        if (containsAttribute(fieldAttributeArr, FieldAttribute.PRIMARY_KEY)) {
            checkAddPrimaryKeyForSync();
            checkForObjectStoreInvalidPrimaryKeyTypes(str, cls);
        }
        checkNewFieldName(str);
        long jAddColumn = this.table.addColumn(fieldMetaData.fieldType, str, containsAttribute(fieldAttributeArr, FieldAttribute.REQUIRED) ? false : fieldMetaData.defaultNullable);
        try {
            addModifiers(str, fieldAttributeArr);
            return this;
        } catch (Exception e) {
            this.table.removeColumn(jAddColumn);
            throw e;
        }
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addIndex(String str) {
        RealmObjectSchema.checkLegalName(str);
        checkFieldExists(str);
        long columnKey = getColumnKey(str);
        if (this.table.hasSearchIndex(columnKey)) {
            throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m(str, " already has an index."));
        }
        this.table.addSearchIndex(columnKey);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addPrimaryKey(String str) {
        checkAddPrimaryKeyForSync();
        RealmObjectSchema.checkLegalName(str);
        checkFieldExists(str);
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.realm.sharedRealm, getClassName());
        if (primaryKeyForObject != null) {
            throw new IllegalStateException(String.format(Locale.ENGLISH, "Field '%s' has been already defined as primary key.", primaryKeyForObject));
        }
        long columnKey = getColumnKey(str);
        RealmFieldType fieldType = getFieldType(str);
        checkForObjectStoreInvalidPrimaryKeyTypes(str, fieldType);
        if (fieldType != RealmFieldType.STRING && !this.table.hasSearchIndex(columnKey)) {
            this.table.addSearchIndex(columnKey);
        }
        OsObjectStore.setPrimaryKeyForObject(this.realm.sharedRealm, getClassName(), str);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmDictionaryField(String str, Class<?> cls) {
        RealmObjectSchema.checkLegalName(str);
        checkFieldNameIsAvailable(str);
        RealmObjectSchema.FieldMetaData fieldMetaData = RealmObjectSchema.SUPPORTED_DICTIONARY_SIMPLE_FIELDS.get(cls);
        if (fieldMetaData != null) {
            this.table.addColumn(fieldMetaData.collectionType, str, fieldMetaData.defaultNullable);
            return this;
        }
        if (cls.equals(RealmObjectSchema.class) || RealmModel.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Use 'addRealmDictionaryField(String name, RealmObjectSchema schema)' instead to add dictionaries that link to other RealmObjects: ", str));
        }
        throw new IllegalArgumentException(String.format(Locale.US, "RealmDictionary does not support dictionaries with this type: %s(%s)", str, cls));
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmListField(String str, RealmObjectSchema realmObjectSchema) {
        RealmObjectSchema.checkLegalName(str);
        checkFieldNameIsAvailable(str);
        this.table.addColumnLink(RealmFieldType.LIST, str, this.realm.sharedRealm.getTable(Table.getTableNameForClass(realmObjectSchema.getClassName())));
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmObjectField(String str, RealmObjectSchema realmObjectSchema) {
        RealmObjectSchema.checkLegalName(str);
        checkFieldNameIsAvailable(str);
        this.table.addColumnLink(RealmFieldType.OBJECT, str, this.realm.sharedRealm.getTable(Table.getTableNameForClass(realmObjectSchema.getClassName())));
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmSetField(String str, RealmObjectSchema realmObjectSchema) {
        RealmObjectSchema.checkLegalName(str);
        checkFieldNameIsAvailable(str);
        this.table.addColumnSetLink(RealmFieldType.LINK_SET, str, this.realm.sharedRealm.getTable(Table.getTableNameForClass(realmObjectSchema.getClassName())));
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public final String getPropertyClassName(String str) {
        String className = this.table.getLinkTarget(getColumnKey(str)).getClassName();
        if (Util.isEmptyString(className)) {
            throw new IllegalArgumentException(String.format("Property '%s' not found.", str));
        }
        return className;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema removeField(String str) {
        this.realm.checkNotInSync();
        RealmObjectSchema.checkLegalName(str);
        if (!hasField(str)) {
            throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m(str, " does not exist."));
        }
        long columnKey = getColumnKey(str);
        String className = getClassName();
        if (str.equals(OsObjectStore.getPrimaryKeyForObject(this.realm.sharedRealm, className))) {
            OsObjectStore.setPrimaryKeyForObject(this.realm.sharedRealm, className, str);
        }
        this.table.removeColumn(columnKey);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema removeIndex(String str) {
        this.realm.checkNotInSync();
        RealmObjectSchema.checkLegalName(str);
        checkFieldExists(str);
        long columnKey = getColumnKey(str);
        if (!this.table.hasSearchIndex(columnKey)) {
            throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m("Field is not indexed: ", str));
        }
        this.table.removeSearchIndex(columnKey);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema removePrimaryKey() {
        this.realm.checkNotInSync();
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.realm.sharedRealm, getClassName());
        if (primaryKeyForObject == null) {
            throw new IllegalStateException(getClassName() + " doesn't have a primary key.");
        }
        long columnKey = this.table.getColumnKey(primaryKeyForObject);
        if (this.table.hasSearchIndex(columnKey)) {
            this.table.removeSearchIndex(columnKey);
        }
        OsObjectStore.setPrimaryKeyForObject(this.realm.sharedRealm, getClassName(), null);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema renameField(String str, String str2) {
        this.realm.checkNotInSync();
        RealmObjectSchema.checkLegalName(str);
        checkFieldExists(str);
        RealmObjectSchema.checkLegalName(str2);
        checkFieldNameIsAvailable(str2);
        this.table.renameColumn(getColumnKey(str), str2);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema setClassName(String str) throws Exception {
        this.realm.checkNotInSync();
        checkEmpty(str);
        String tableNameForClass = Table.getTableNameForClass(str);
        int length = str.length();
        int i = Table.CLASS_NAME_MAX_LENGTH;
        if (length > i) {
            throw new IllegalArgumentException(String.format(Locale.US, "Class name is too long. Limit is %1$d characters: '%2$s' (%3$d)", Integer.valueOf(i), str, Integer.valueOf(str.length())));
        }
        if (this.realm.sharedRealm.hasTable(tableNameForClass)) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Class already exists: ", str));
        }
        String name = this.table.getName();
        String className = this.table.getClassName();
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.realm.sharedRealm, className);
        if (primaryKeyForObject != null) {
            OsObjectStore.setPrimaryKeyForObject(this.realm.sharedRealm, className, null);
        }
        this.realm.sharedRealm.renameTable(name, tableNameForClass);
        if (primaryKeyForObject != null) {
            try {
                OsObjectStore.setPrimaryKeyForObject(this.realm.sharedRealm, str, primaryKeyForObject);
            } catch (Exception e) {
                this.realm.sharedRealm.renameTable(this.table.getName(), name);
                throw e;
            }
        }
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema setNullable(String str, boolean z) {
        setRequired(str, !z);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema setRequired(String str, boolean z) {
        long columnKey = this.table.getColumnKey(str);
        boolean zIsRequired = isRequired(str);
        RealmFieldType columnType = this.table.getColumnType(columnKey);
        if (columnType == RealmFieldType.OBJECT) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Cannot modify the required state for RealmObject references: ", str));
        }
        if (columnType == RealmFieldType.LIST) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Cannot modify the required state for RealmList references: ", str));
        }
        if (z && zIsRequired) {
            throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m("Field is already required: ", str));
        }
        if (!z && !zIsRequired) {
            throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m("Field is already nullable: ", str));
        }
        if (z) {
            try {
                this.table.convertColumnToNotNullable(columnKey);
            } catch (RuntimeException e) {
                if (e.getMessage().contains("has null value(s) in property")) {
                    throw new IllegalStateException(e.getMessage());
                }
                throw e;
            }
        } else {
            this.table.convertColumnToNullable(columnKey);
        }
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema transform(RealmObjectSchema.Function function) {
        if (function != null) {
            OsResults osResultsCreateSnapshot = OsResults.createFromQuery(this.realm.sharedRealm, this.table.where()).createSnapshot();
            long size = osResultsCreateSnapshot.size();
            if (size > 2147483647L) {
                throw new UnsupportedOperationException(Insets$$ExternalSyntheticOutline0.m("Too many results to iterate: ", size));
            }
            int size2 = (int) osResultsCreateSnapshot.size();
            for (int i = 0; i < size2; i++) {
                DynamicRealmObject dynamicRealmObject = new DynamicRealmObject(this.realm, new CheckedRow(osResultsCreateSnapshot.getUncheckedRow(i)));
                if (dynamicRealmObject.isValid()) {
                    function.apply(dynamicRealmObject);
                }
            }
        }
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmListField(String str, Class<?> cls) {
        RealmObjectSchema.checkLegalName(str);
        checkFieldNameIsAvailable(str);
        RealmObjectSchema.FieldMetaData fieldMetaData = RealmObjectSchema.SUPPORTED_LIST_SIMPLE_FIELDS.get(cls);
        if (fieldMetaData == null) {
            if (!cls.equals(RealmObjectSchema.class) && !RealmModel.class.isAssignableFrom(cls)) {
                throw new IllegalArgumentException(String.format(Locale.US, "RealmList does not support lists with this type: %s(%s)", str, cls));
            }
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Use 'addRealmListField(String name, RealmObjectSchema schema)' instead to add lists that link to other RealmObjects: ", str));
        }
        this.table.addColumn(fieldMetaData.collectionType, str, fieldMetaData.defaultNullable);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmSetField(String str, Class<?> cls) {
        RealmObjectSchema.checkLegalName(str);
        checkFieldNameIsAvailable(str);
        RealmObjectSchema.FieldMetaData fieldMetaData = RealmObjectSchema.SUPPORTED_SET_SIMPLE_FIELDS.get(cls);
        if (fieldMetaData == null) {
            if (!cls.equals(RealmObjectSchema.class) && !RealmModel.class.isAssignableFrom(cls)) {
                throw new IllegalArgumentException(String.format(Locale.US, "RealmSet does not support sets with this type: %s(%s)", str, cls));
            }
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Use 'addRealmSetField(String name, RealmObjectSchema schema)' instead to add sets that link to other RealmObjects: ", str));
        }
        this.table.addColumn(fieldMetaData.collectionType, str, fieldMetaData.defaultNullable);
        return this;
    }

    private void checkForObjectStoreInvalidPrimaryKeyTypes(String str, RealmFieldType realmFieldType) {
        int i = AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[realmFieldType.ordinal()];
        if (i == 1) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Boolean fields cannot be marked as primary keys: ", str));
        }
        if (i == 2) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Date fields cannot be marked as primary keys: ", str));
        }
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmDictionaryField(String str, RealmObjectSchema realmObjectSchema) {
        RealmObjectSchema.checkLegalName(str);
        checkFieldNameIsAvailable(str);
        this.table.addColumnDictionaryLink(RealmFieldType.STRING_TO_LINK_MAP, str, this.realm.sharedRealm.getTable(Table.getTableNameForClass(realmObjectSchema.getClassName())));
        return this;
    }
}
