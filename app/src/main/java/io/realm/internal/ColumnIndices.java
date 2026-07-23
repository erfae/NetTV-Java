package io.realm.internal;

import io.realm.RealmModel;
import io.realm.exceptions.RealmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;

/* JADX INFO: loaded from: classes2.dex */
public final class ColumnIndices {
    private final RealmProxyMediator mediator;
    private final OsSchemaInfo osSchemaInfo;
    private final Map<Class<? extends RealmModel>, ColumnInfo> classToColumnInfoMap = new ConcurrentHashMap();
    private final Map<String, ColumnInfo> simpleClassNameToColumnInfoMap = new HashMap();

    public ColumnIndices(RealmProxyMediator realmProxyMediator, OsSchemaInfo osSchemaInfo) {
        this.mediator = realmProxyMediator;
        this.osSchemaInfo = osSchemaInfo;
    }

    @Nonnull
    public ColumnInfo getColumnInfo(Class<? extends RealmModel> cls) {
        ColumnInfo columnInfo = this.classToColumnInfoMap.get(cls);
        if (columnInfo != null) {
            return columnInfo;
        }
        ColumnInfo columnInfoCreateColumnInfo = this.mediator.createColumnInfo(cls, this.osSchemaInfo);
        this.classToColumnInfoMap.put(cls, columnInfoCreateColumnInfo);
        return columnInfoCreateColumnInfo;
    }

    public void refresh() {
        for (Map.Entry<Class<? extends RealmModel>, ColumnInfo> entry : this.classToColumnInfoMap.entrySet()) {
            entry.getValue().copyFrom(this.mediator.createColumnInfo(entry.getKey(), this.osSchemaInfo));
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ColumnIndices[");
        boolean z = false;
        for (Map.Entry<Class<? extends RealmModel>, ColumnInfo> entry : this.classToColumnInfoMap.entrySet()) {
            if (z) {
                sb.append(",");
            }
            sb.append(entry.getKey().getSimpleName());
            sb.append("->");
            sb.append(entry.getValue());
            z = true;
        }
        sb.append("]");
        return sb.toString();
    }

    @Nonnull
    public ColumnInfo getColumnInfo(String str) {
        ColumnInfo columnInfo = this.simpleClassNameToColumnInfoMap.get(str);
        if (columnInfo == null) {
            for (Class<? extends RealmModel> cls : this.mediator.getModelClasses()) {
                if (this.mediator.getSimpleClassName(cls).equals(str)) {
                    columnInfo = getColumnInfo(cls);
                    this.simpleClassNameToColumnInfoMap.put(str, columnInfo);
                    break;
                }
            }
        }
        if (columnInfo != null) {
            return columnInfo;
        }
        throw new RealmException(String.format(Locale.US, "'%s' doesn't exist in current schema.", str));
    }
}
