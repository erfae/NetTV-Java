package org.androidannotations.api.sharedpreferences;

import org.androidannotations.api.sharedpreferences.EditorHelper;

/* JADX INFO: loaded from: classes2.dex */
public final class BooleanPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {
    public T put(boolean z) {
        this.editorHelper.getEditor().putBoolean(this.key, z);
        return this.editorHelper;
    }
}
