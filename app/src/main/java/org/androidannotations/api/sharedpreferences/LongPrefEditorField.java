package org.androidannotations.api.sharedpreferences;

import org.androidannotations.api.sharedpreferences.EditorHelper;

/* JADX INFO: loaded from: classes2.dex */
public final class LongPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {
    public T put(long j) {
        this.editorHelper.getEditor().putLong(this.key, j);
        return this.editorHelper;
    }
}
