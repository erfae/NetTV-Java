package io.realm.internal;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.RealmFieldType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ColumnInfo {
    private final Map<String, ColumnDetails> columnKeysFromColumnNames;
    private final Map<String, ColumnDetails> columnkeysFromJavaFieldNames;
    private final Map<String, String> javaFieldNameToInternalNames;
    private final boolean mutable;

    public ColumnInfo(int i) {
        this(i, true);
    }

    public final long addColumnDetails(String str, String str2, OsObjectSchemaInfo osObjectSchemaInfo) {
        Property property = osObjectSchemaInfo.getProperty(str2);
        ColumnDetails columnDetails = new ColumnDetails(property);
        this.columnkeysFromJavaFieldNames.put(str, columnDetails);
        this.columnKeysFromColumnNames.put(str2, columnDetails);
        this.javaFieldNameToInternalNames.put(str, str2);
        return property.getColumnKey();
    }

    public abstract void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2);

    public void copyFrom(ColumnInfo columnInfo) {
        if (!this.mutable) {
            throw new UnsupportedOperationException("Attempt to modify an immutable ColumnInfo");
        }
        Objects.requireNonNull(columnInfo, "Attempt to copy null ColumnInfo");
        this.columnkeysFromJavaFieldNames.clear();
        this.columnkeysFromJavaFieldNames.putAll(columnInfo.columnkeysFromJavaFieldNames);
        this.columnKeysFromColumnNames.clear();
        this.columnKeysFromColumnNames.putAll(columnInfo.columnKeysFromColumnNames);
        this.javaFieldNameToInternalNames.clear();
        this.javaFieldNameToInternalNames.putAll(columnInfo.javaFieldNameToInternalNames);
        copy(columnInfo, this);
    }

    @Nullable
    public ColumnDetails getColumnDetails(String str) {
        return this.columnkeysFromJavaFieldNames.get(str);
    }

    public long getColumnKey(String str) {
        ColumnDetails columnDetails = this.columnkeysFromJavaFieldNames.get(str);
        if (columnDetails == null) {
            return -1L;
        }
        return columnDetails.columnKey;
    }

    public Map<String, ColumnDetails> getColumnKeysMap() {
        return this.columnkeysFromJavaFieldNames;
    }

    @Nullable
    public String getInternalFieldName(String str) {
        return this.javaFieldNameToInternalNames.get(str);
    }

    public final boolean isMutable() {
        return this.mutable;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ColumnInfo[");
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("mutable=");
        sbM.append(this.mutable);
        sb.append(sbM.toString());
        sb.append(",");
        boolean z = false;
        if (this.columnkeysFromJavaFieldNames != null) {
            sb.append("JavaFieldNames=[");
            boolean z2 = false;
            for (Map.Entry<String, ColumnDetails> entry : this.columnkeysFromJavaFieldNames.entrySet()) {
                if (z2) {
                    sb.append(",");
                }
                sb.append(entry.getKey());
                sb.append("->");
                sb.append(entry.getValue());
                z2 = true;
            }
            sb.append("]");
        }
        if (this.columnKeysFromColumnNames != null) {
            sb.append(", InternalFieldNames=[");
            for (Map.Entry<String, ColumnDetails> entry2 : this.columnKeysFromColumnNames.entrySet()) {
                if (z) {
                    sb.append(",");
                }
                sb.append(entry2.getKey());
                sb.append("->");
                sb.append(entry2.getValue());
                z = true;
            }
            sb.append("]");
        }
        sb.append("]");
        return sb.toString();
    }

    public ColumnInfo() {
        this(0, false);
    }

    private ColumnInfo(int i, boolean z) {
        this.columnkeysFromJavaFieldNames = new HashMap(i);
        this.columnKeysFromColumnNames = new HashMap(i);
        this.javaFieldNameToInternalNames = new HashMap(i);
        this.mutable = z;
    }

    public static final class ColumnDetails {
        public final long columnKey;
        public final RealmFieldType columnType;
        public final String linkedClassName;

        private ColumnDetails(long j, RealmFieldType realmFieldType, @Nullable String str) {
            this.columnKey = j;
            this.columnType = realmFieldType;
            this.linkedClassName = str;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("ColumnDetails[");
            sb.append(this.columnKey);
            sb.append(", ");
            sb.append(this.columnType);
            sb.append(", ");
            return Insets$$ExternalSyntheticOutline0.m(sb, this.linkedClassName, "]");
        }

        public ColumnDetails(Property property) {
            this(property.getColumnKey(), property.getType(), property.getLinkedObjectName());
        }
    }
}
