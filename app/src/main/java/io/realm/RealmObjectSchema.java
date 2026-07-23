package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectStore;
import io.realm.internal.Table;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes2.dex */
public abstract class RealmObjectSchema {
    public static final Map<Class<?>, FieldMetaData> SUPPORTED_DICTIONARY_SIMPLE_FIELDS;
    public static final Map<Class<?>, FieldMetaData> SUPPORTED_LINKED_FIELDS;
    public static final Map<Class<?>, FieldMetaData> SUPPORTED_LIST_SIMPLE_FIELDS;
    public static final Map<Class<?>, FieldMetaData> SUPPORTED_SET_SIMPLE_FIELDS;
    public final ColumnInfo columnInfo;
    public final BaseRealm realm;
    public final Table table;

    public static final class DynamicColumnIndices extends ColumnInfo {
        private final Table table;

        public DynamicColumnIndices(Table table) {
            this.table = table;
        }

        @Override // io.realm.internal.ColumnInfo
        public final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            throw new UnsupportedOperationException("DynamicColumnIndices cannot copy");
        }

        @Override // io.realm.internal.ColumnInfo
        public void copyFrom(ColumnInfo columnInfo) {
            throw new UnsupportedOperationException("DynamicColumnIndices cannot be copied");
        }

        @Override // io.realm.internal.ColumnInfo
        public ColumnInfo.ColumnDetails getColumnDetails(String str) {
            throw new UnsupportedOperationException("DynamicColumnIndices do not support 'getColumnDetails'");
        }

        @Override // io.realm.internal.ColumnInfo
        public long getColumnKey(String str) {
            return this.table.getColumnKey(str);
        }
    }

    public static final class FieldMetaData {
        public final RealmFieldType collectionType;
        public final boolean defaultNullable;
        public final RealmFieldType fieldType;

        public FieldMetaData(RealmFieldType realmFieldType, @Nullable RealmFieldType realmFieldType2, boolean z) {
            this.fieldType = realmFieldType;
            this.collectionType = realmFieldType2;
            this.defaultNullable = z;
        }
    }

    public interface Function {
        void apply(DynamicRealmObject dynamicRealmObject);
    }

    static {
        HashMap map = new HashMap();
        RealmFieldType realmFieldType = RealmFieldType.STRING;
        map.put(String.class, new FieldMetaData(realmFieldType, RealmFieldType.STRING_LIST, true));
        Class cls = Short.TYPE;
        RealmFieldType realmFieldType2 = RealmFieldType.INTEGER;
        RealmFieldType realmFieldType3 = RealmFieldType.INTEGER_LIST;
        map.put(cls, new FieldMetaData(realmFieldType2, realmFieldType3, false));
        map.put(Short.class, new FieldMetaData(realmFieldType2, realmFieldType3, true));
        Class cls2 = Integer.TYPE;
        map.put(cls2, new FieldMetaData(realmFieldType2, realmFieldType3, false));
        map.put(Integer.class, new FieldMetaData(realmFieldType2, realmFieldType3, true));
        Class cls3 = Long.TYPE;
        map.put(cls3, new FieldMetaData(realmFieldType2, realmFieldType3, false));
        map.put(Long.class, new FieldMetaData(realmFieldType2, realmFieldType3, true));
        Class cls4 = Float.TYPE;
        RealmFieldType realmFieldType4 = RealmFieldType.FLOAT;
        RealmFieldType realmFieldType5 = RealmFieldType.FLOAT_LIST;
        map.put(cls4, new FieldMetaData(realmFieldType4, realmFieldType5, false));
        map.put(Float.class, new FieldMetaData(realmFieldType4, realmFieldType5, true));
        Class cls5 = Double.TYPE;
        RealmFieldType realmFieldType6 = RealmFieldType.DOUBLE;
        RealmFieldType realmFieldType7 = RealmFieldType.DOUBLE_LIST;
        map.put(cls5, new FieldMetaData(realmFieldType6, realmFieldType7, false));
        map.put(Double.class, new FieldMetaData(realmFieldType6, realmFieldType7, true));
        Class cls6 = Boolean.TYPE;
        RealmFieldType realmFieldType8 = RealmFieldType.BOOLEAN;
        RealmFieldType realmFieldType9 = RealmFieldType.BOOLEAN_LIST;
        map.put(cls6, new FieldMetaData(realmFieldType8, realmFieldType9, false));
        map.put(Boolean.class, new FieldMetaData(realmFieldType8, realmFieldType9, true));
        map.put(Byte.TYPE, new FieldMetaData(realmFieldType2, realmFieldType3, false));
        map.put(Byte.class, new FieldMetaData(realmFieldType2, realmFieldType3, true));
        RealmFieldType realmFieldType10 = RealmFieldType.BINARY;
        map.put(byte[].class, new FieldMetaData(realmFieldType10, RealmFieldType.BINARY_LIST, true));
        RealmFieldType realmFieldType11 = RealmFieldType.DATE;
        map.put(Date.class, new FieldMetaData(realmFieldType11, RealmFieldType.DATE_LIST, true));
        RealmFieldType realmFieldType12 = RealmFieldType.OBJECT_ID;
        map.put(ObjectId.class, new FieldMetaData(realmFieldType12, RealmFieldType.OBJECT_ID_LIST, true));
        RealmFieldType realmFieldType13 = RealmFieldType.DECIMAL128;
        map.put(Decimal128.class, new FieldMetaData(realmFieldType13, RealmFieldType.DECIMAL128_LIST, true));
        RealmFieldType realmFieldType14 = RealmFieldType.UUID;
        map.put(UUID.class, new FieldMetaData(realmFieldType14, RealmFieldType.UUID_LIST, true));
        RealmFieldType realmFieldType15 = RealmFieldType.MIXED;
        map.put(RealmAny.class, new FieldMetaData(realmFieldType15, RealmFieldType.MIXED_LIST, true));
        SUPPORTED_LIST_SIMPLE_FIELDS = Collections.unmodifiableMap(map);
        HashMap map2 = new HashMap();
        map2.put(String.class, new FieldMetaData(realmFieldType, RealmFieldType.STRING_TO_STRING_MAP, true));
        Class cls7 = Short.TYPE;
        RealmFieldType realmFieldType16 = RealmFieldType.STRING_TO_INTEGER_MAP;
        map2.put(cls7, new FieldMetaData(realmFieldType2, realmFieldType16, false));
        map2.put(Short.class, new FieldMetaData(realmFieldType2, realmFieldType16, true));
        map2.put(cls2, new FieldMetaData(realmFieldType2, realmFieldType16, false));
        map2.put(Integer.class, new FieldMetaData(realmFieldType2, realmFieldType16, true));
        map2.put(cls3, new FieldMetaData(realmFieldType2, realmFieldType16, false));
        map2.put(Long.class, new FieldMetaData(realmFieldType2, realmFieldType16, true));
        RealmFieldType realmFieldType17 = RealmFieldType.STRING_TO_FLOAT_MAP;
        map2.put(cls4, new FieldMetaData(realmFieldType4, realmFieldType17, false));
        map2.put(Float.class, new FieldMetaData(realmFieldType4, realmFieldType17, true));
        Class cls8 = Double.TYPE;
        RealmFieldType realmFieldType18 = RealmFieldType.STRING_TO_DOUBLE_MAP;
        map2.put(cls8, new FieldMetaData(realmFieldType6, realmFieldType18, false));
        map2.put(Double.class, new FieldMetaData(realmFieldType6, realmFieldType18, true));
        RealmFieldType realmFieldType19 = RealmFieldType.STRING_TO_BOOLEAN_MAP;
        map2.put(cls6, new FieldMetaData(realmFieldType8, realmFieldType19, false));
        map2.put(Boolean.class, new FieldMetaData(realmFieldType8, realmFieldType19, true));
        map2.put(Byte.TYPE, new FieldMetaData(realmFieldType2, realmFieldType16, false));
        map2.put(Byte.class, new FieldMetaData(realmFieldType2, realmFieldType16, true));
        map2.put(byte[].class, new FieldMetaData(realmFieldType10, RealmFieldType.STRING_TO_BINARY_MAP, true));
        map2.put(Date.class, new FieldMetaData(realmFieldType11, RealmFieldType.STRING_TO_DATE_MAP, true));
        map2.put(ObjectId.class, new FieldMetaData(realmFieldType12, RealmFieldType.STRING_TO_OBJECT_ID_MAP, true));
        map2.put(Decimal128.class, new FieldMetaData(realmFieldType13, RealmFieldType.STRING_TO_DECIMAL128_MAP, true));
        map2.put(UUID.class, new FieldMetaData(realmFieldType14, RealmFieldType.STRING_TO_UUID_MAP, true));
        map2.put(RealmAny.class, new FieldMetaData(realmFieldType15, RealmFieldType.STRING_TO_MIXED_MAP, true));
        SUPPORTED_DICTIONARY_SIMPLE_FIELDS = Collections.unmodifiableMap(map2);
        HashMap map3 = new HashMap();
        map3.put(String.class, new FieldMetaData(realmFieldType, RealmFieldType.STRING_SET, true));
        Class cls9 = Short.TYPE;
        RealmFieldType realmFieldType20 = RealmFieldType.INTEGER_SET;
        map3.put(cls9, new FieldMetaData(realmFieldType2, realmFieldType20, false));
        map3.put(Short.class, new FieldMetaData(realmFieldType2, realmFieldType20, true));
        map3.put(cls2, new FieldMetaData(realmFieldType2, realmFieldType20, false));
        map3.put(Integer.class, new FieldMetaData(realmFieldType2, realmFieldType20, true));
        map3.put(cls3, new FieldMetaData(realmFieldType2, realmFieldType20, false));
        map3.put(Long.class, new FieldMetaData(realmFieldType2, realmFieldType20, true));
        RealmFieldType realmFieldType21 = RealmFieldType.FLOAT_SET;
        map3.put(cls4, new FieldMetaData(realmFieldType4, realmFieldType21, false));
        map3.put(Float.class, new FieldMetaData(realmFieldType4, realmFieldType21, true));
        Class cls10 = Double.TYPE;
        RealmFieldType realmFieldType22 = RealmFieldType.DOUBLE_SET;
        map3.put(cls10, new FieldMetaData(realmFieldType6, realmFieldType22, false));
        map3.put(Double.class, new FieldMetaData(realmFieldType6, realmFieldType22, true));
        RealmFieldType realmFieldType23 = RealmFieldType.BOOLEAN_SET;
        map3.put(cls6, new FieldMetaData(realmFieldType8, realmFieldType23, false));
        map3.put(Boolean.class, new FieldMetaData(realmFieldType8, realmFieldType23, true));
        map3.put(Byte.TYPE, new FieldMetaData(realmFieldType2, realmFieldType20, false));
        map3.put(Byte.class, new FieldMetaData(realmFieldType2, realmFieldType20, true));
        map3.put(byte[].class, new FieldMetaData(realmFieldType10, RealmFieldType.BINARY_SET, true));
        map3.put(Date.class, new FieldMetaData(realmFieldType11, RealmFieldType.DATE_SET, true));
        map3.put(ObjectId.class, new FieldMetaData(realmFieldType12, RealmFieldType.OBJECT_ID_SET, true));
        map3.put(Decimal128.class, new FieldMetaData(realmFieldType13, RealmFieldType.DECIMAL128_SET, true));
        map3.put(UUID.class, new FieldMetaData(realmFieldType14, RealmFieldType.UUID_SET, true));
        map3.put(RealmAny.class, new FieldMetaData(realmFieldType15, RealmFieldType.MIXED_SET, true));
        SUPPORTED_SET_SIMPLE_FIELDS = Collections.unmodifiableMap(map3);
        HashMap map4 = new HashMap();
        map4.put(RealmObject.class, new FieldMetaData(RealmFieldType.OBJECT, null, false));
        map4.put(RealmList.class, new FieldMetaData(RealmFieldType.LIST, null, false));
        map4.put(RealmDictionary.class, new FieldMetaData(RealmFieldType.STRING_TO_LINK_MAP, null, false));
        map4.put(RealmSet.class, new FieldMetaData(RealmFieldType.LINK_SET, null, false));
        SUPPORTED_LINKED_FIELDS = Collections.unmodifiableMap(map4);
    }

    public RealmObjectSchema(BaseRealm baseRealm, Table table, ColumnInfo columnInfo) {
        this.realm = baseRealm;
        this.table = table;
        this.columnInfo = columnInfo;
    }

    public static void checkLegalName(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Field name can not be null or empty");
        }
        if (str.contains(".")) {
            throw new IllegalArgumentException("Field name can not contain '.'");
        }
        if (str.length() > 63) {
            throw new IllegalArgumentException("Field name is currently limited to max 63 characters.");
        }
    }

    public abstract RealmObjectSchema addField(String str, Class<?> cls, FieldAttribute... fieldAttributeArr);

    public abstract RealmObjectSchema addIndex(String str);

    public abstract RealmObjectSchema addPrimaryKey(String str);

    public abstract RealmObjectSchema addRealmDictionaryField(String str, RealmObjectSchema realmObjectSchema);

    public abstract RealmObjectSchema addRealmDictionaryField(String str, Class<?> cls);

    public abstract RealmObjectSchema addRealmListField(String str, RealmObjectSchema realmObjectSchema);

    public abstract RealmObjectSchema addRealmListField(String str, Class<?> cls);

    public abstract RealmObjectSchema addRealmObjectField(String str, RealmObjectSchema realmObjectSchema);

    public abstract RealmObjectSchema addRealmSetField(String str, RealmObjectSchema realmObjectSchema);

    public abstract RealmObjectSchema addRealmSetField(String str, Class<?> cls);

    public final void checkFieldExists(String str) {
        if (this.table.getColumnKey(str) != -1) {
            return;
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Field name doesn't exist on object '");
        sbM.append(getClassName());
        sbM.append("': ");
        sbM.append(str);
        throw new IllegalArgumentException(sbM.toString());
    }

    public final long getAndCheckFieldColumnKey(String str) {
        long columnKey = this.columnInfo.getColumnKey(str);
        if (columnKey >= 0) {
            return columnKey;
        }
        throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Field does not exist: ", str));
    }

    public String getClassName() {
        return this.table.getClassName();
    }

    public final long getColumnKey(String str) {
        long columnKey = this.table.getColumnKey(str);
        if (columnKey != -1) {
            return columnKey;
        }
        throw new IllegalArgumentException(String.format(Locale.US, "Field name '%s' does not exist on schema for '%s'", str, getClassName()));
    }

    public Set<String> getFieldNames() {
        LinkedHashSet linkedHashSet = new LinkedHashSet((int) this.table.getColumnCount());
        for (String str : this.table.getColumnNames()) {
            linkedHashSet.add(str);
        }
        return linkedHashSet;
    }

    public RealmFieldType getFieldType(String str) {
        return this.table.getColumnType(getColumnKey(str));
    }

    public String getPrimaryKey() {
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.realm.sharedRealm, getClassName());
        if (primaryKeyForObject != null) {
            return primaryKeyForObject;
        }
        throw new IllegalStateException(getClassName() + " doesn't have a primary key.");
    }

    public abstract String getPropertyClassName(String str);

    public boolean hasField(String str) {
        return this.table.getColumnKey(str) != -1;
    }

    public boolean hasIndex(String str) {
        checkLegalName(str);
        checkFieldExists(str);
        Table table = this.table;
        return table.hasSearchIndex(table.getColumnKey(str));
    }

    public boolean hasPrimaryKey() {
        return OsObjectStore.getPrimaryKeyForObject(this.realm.sharedRealm, getClassName()) != null;
    }

    public boolean isEmbedded() {
        return this.table.isEmbedded();
    }

    public boolean isNullable(String str) {
        return this.table.isColumnNullable(getColumnKey(str));
    }

    public boolean isPrimaryKey(String str) {
        checkFieldExists(str);
        return str.equals(OsObjectStore.getPrimaryKeyForObject(this.realm.sharedRealm, getClassName()));
    }

    public boolean isRequired(String str) {
        return !this.table.isColumnNullable(getColumnKey(str));
    }

    public abstract RealmObjectSchema removeField(String str);

    public abstract RealmObjectSchema removeIndex(String str);

    public abstract RealmObjectSchema removePrimaryKey();

    public abstract RealmObjectSchema renameField(String str, String str2);

    public abstract RealmObjectSchema setClassName(String str);

    public void setEmbedded(boolean z) {
        if (hasPrimaryKey()) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Embedded classes cannot have primary keys. This class has a primary key defined so cannot be marked as embedded: ");
            sbM.append(getClassName());
            throw new IllegalStateException(sbM.toString());
        }
        if (!this.table.setEmbedded(z) && z) {
            throw new IllegalStateException("The class could not be marked as embedded as some objects of this type break some of the Embedded Objects invariants. In order to convert all objects to be embedded, they must have one and exactly one parent objectpointing to them.");
        }
    }

    public abstract RealmObjectSchema setNullable(String str, boolean z);

    public abstract RealmObjectSchema setRequired(String str, boolean z);

    public abstract RealmObjectSchema transform(Function function);
}
