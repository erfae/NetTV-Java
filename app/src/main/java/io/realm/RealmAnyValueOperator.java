package io.realm;

import io.realm.internal.OsMap;
import io.realm.internal.core.NativeRealmAny;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: ManagedMapManager.java */
/* JADX INFO: loaded from: classes2.dex */
class RealmAnyValueOperator<K> extends MapValueOperator<K, RealmAny> {
    public RealmAnyValueOperator(BaseRealm baseRealm, OsMap osMap, TypeSelectorForMap<K, RealmAny> typeSelectorForMap) {
        super(RealmAny.class, baseRealm, osMap, typeSelectorForMap, RealmMapEntrySet.IteratorType.MIXED);
    }

    @Override // io.realm.MapValueOperator
    public final boolean containsValueInternal(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof RealmAny) {
            return this.osMap.containsRealmAnyValue(((RealmAny) obj).getNativePtr());
        }
        throw new IllegalArgumentException("This dictionary can only contain 'RealmAny' values.");
    }

    @Override // io.realm.MapValueOperator
    public final Set<Map.Entry<K, RealmAny>> entrySet() {
        return new RealmMapEntrySet(this.baseRealm, this.osMap, RealmMapEntrySet.IteratorType.MIXED, (TypeSelectorForMap) null);
    }

    @Override // io.realm.MapValueOperator
    @Nullable
    public final RealmAny put(Object obj, @Nullable RealmAny realmAny) {
        RealmAny realmAny2 = realmAny;
        RealmAny realmAny3 = get(obj);
        if (realmAny2 == null) {
            this.osMap.put(obj, null);
        } else {
            this.osMap.putRealmAny(obj, CollectionUtils.copyToRealmIfNeeded(this.baseRealm, realmAny2).getNativePtr());
        }
        return realmAny3;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.realm.MapValueOperator
    @Nullable
    public final RealmAny get(Object obj) {
        long realmAnyPtr = this.osMap.getRealmAnyPtr(obj);
        if (realmAnyPtr == -1) {
            return null;
        }
        return new RealmAny(RealmAnyOperator.fromNativeRealmAny(this.baseRealm, new NativeRealmAny(realmAnyPtr)));
    }
}
