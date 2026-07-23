package io.realm;

import io.realm.internal.util.Pair;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: ManagedMapManager.java */
/* JADX INFO: loaded from: classes2.dex */
class DictionaryManager<V> extends ManagedMapManager<String, V> {
    public DictionaryManager(BaseRealm baseRealm, MapValueOperator<String, V> mapValueOperator, TypeSelectorForMap<String, V> typeSelectorForMap) {
        super(baseRealm, mapValueOperator, typeSelectorForMap);
    }

    @Override // io.realm.ManagedMapManager
    public final MapChangeSet<String> changeSetFactory(long j) {
        return new StringMapChangeSet(j);
    }

    @Override // io.realm.ManagedMapManager
    public final boolean containsKeyInternal(Object obj) {
        Objects.requireNonNull(obj, "Null keys are not allowed when calling 'containsKey'.");
        if (obj.getClass() == String.class) {
            return this.mapValueOperator.osMap.containsKey(obj);
        }
        throw new ClassCastException("Only String keys can be used with 'containsKey'.");
    }

    @Override // io.realm.ManagedMapManager, java.util.Map
    public Set<Map.Entry<String, V>> entrySet() {
        return this.mapValueOperator.entrySet();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.ManagedMapManager
    public final RealmMap freezeInternal(Pair pair) {
        return this.typeSelectorForMap.freeze((BaseRealm) pair.first);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // java.util.Map
    public V get(Object obj) {
        Objects.requireNonNull(obj, "Null keys are not allowed when calling 'get'.");
        if (obj.getClass() == String.class) {
            return this.mapValueOperator.get((K) ((String) obj));
        }
        throw new ClassCastException("Only String keys can be used with 'containsKey'.");
    }

    @Override // io.realm.ManagedMapManager
    public final void validateMap(Map<? extends String, ? extends V> map) {
        Iterator<Map.Entry<? extends String, ? extends V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Objects.requireNonNull(it.next().getKey(), "Null keys are not allowed.");
        }
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // io.realm.ManagedMapManager, java.util.Map
    public V put(String str, @Nullable V v) {
        Objects.requireNonNull(str, "Null keys are not allowed.");
        try {
            return this.mapValueOperator.put((K) str, v);
        } catch (IllegalStateException e) {
            String message = e.getMessage();
            Objects.requireNonNull(message);
            if (message.contains("Data type mismatch")) {
                throw new NullPointerException("Cannot insert null values in a dictionary marked with '@Required'.");
            }
            throw e;
        }
    }
}
