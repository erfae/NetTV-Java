package io.realm;

import io.realm.internal.OsMapChangeSet;

/* JADX INFO: compiled from: MapChangeSet.java */
/* JADX INFO: loaded from: classes2.dex */
class StringMapChangeSet implements MapChangeSet<String> {
    private final OsMapChangeSet osMapChangeSet;

    public StringMapChangeSet(long j) {
        this.osMapChangeSet = new OsMapChangeSet(j);
    }

    @Override // io.realm.MapChangeSet
    public boolean isEmpty() {
        return this.osMapChangeSet.isEmpty();
    }

    @Override // io.realm.MapChangeSet
    public String[] getChanges() {
        return this.osMapChangeSet.getStringKeyModifications();
    }

    @Override // io.realm.MapChangeSet
    public String[] getDeletions() {
        return this.osMapChangeSet.getStringKeyDeletions();
    }

    @Override // io.realm.MapChangeSet
    public String[] getInsertions() {
        return this.osMapChangeSet.getStringKeyInsertions();
    }
}
