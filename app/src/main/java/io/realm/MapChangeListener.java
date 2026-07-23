package io.realm;

/* JADX INFO: loaded from: classes2.dex */
public interface MapChangeListener<K, V> {
    void onChange(RealmMap<K, V> realmMap, MapChangeSet<K> mapChangeSet);
}
