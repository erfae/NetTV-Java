package io.realm;

/* JADX INFO: loaded from: classes2.dex */
public enum Sort {
    ASCENDING(true),
    DESCENDING(false);

    private final boolean value;

    Sort(boolean z) {
        this.value = z;
    }

    public boolean getValue() {
        return this.value;
    }
}
