package io.realm;

/* JADX INFO: loaded from: classes2.dex */
public interface ObjectChangeSet {
    String[] getChangedFields();

    boolean isDeleted();

    boolean isFieldChanged(String str);
}
