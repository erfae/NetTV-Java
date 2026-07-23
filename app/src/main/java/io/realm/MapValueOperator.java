package io.realm;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import io.realm.internal.OsMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: ManagedMapManager.java */
/* JADX INFO: loaded from: classes2.dex */
abstract class MapValueOperator<K, V> {
    public final BaseRealm baseRealm;
    public final RealmMapEntrySet.IteratorType iteratorType;
    public final OsMap osMap;
    public final TypeSelectorForMap<K, V> typeSelectorForMap;
    public final Class<V> valueClass;

    public MapValueOperator(Class<V> cls, BaseRealm baseRealm, OsMap osMap, TypeSelectorForMap<K, V> typeSelectorForMap, RealmMapEntrySet.IteratorType iteratorType) {
        this.valueClass = cls;
        this.baseRealm = baseRealm;
        this.osMap = osMap;
        this.typeSelectorForMap = typeSelectorForMap;
        this.iteratorType = iteratorType;
    }

    public boolean containsValue(@Nullable Object obj) {
        if (obj == null || obj.getClass() == this.valueClass) {
            return containsValueInternal(obj);
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Only '");
        sbM.append(this.valueClass.getSimpleName());
        sbM.append("'  values can be used with 'containsValue'.");
        throw new ClassCastException(sbM.toString());
    }

    public abstract boolean containsValueInternal(@Nullable Object obj);

    public abstract Set<Map.Entry<K, V>> entrySet();

    @Nullable
    public abstract V get(K k);

    @Nullable
    public abstract V put(K k, @Nullable V v);
}
