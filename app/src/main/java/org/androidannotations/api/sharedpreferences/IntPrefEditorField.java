package org.androidannotations.api.sharedpreferences;

import org.androidannotations.api.sharedpreferences.EditorHelper;

/* JADX INFO: loaded from: classes2.dex */
public final class IntPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {
    public T put(int i) {
        this.editorHelper.getEditor().putInt(this.key, i);
        return this.editorHelper;
    }
}
