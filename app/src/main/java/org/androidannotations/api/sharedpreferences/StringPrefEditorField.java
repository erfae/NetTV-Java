package org.androidannotations.api.sharedpreferences;

import org.androidannotations.api.sharedpreferences.EditorHelper;

/* JADX INFO: loaded from: classes2.dex */
public final class StringPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {
    public T put(String str) {
        this.editorHelper.getEditor().putString(this.key, str);
        return this.editorHelper;
    }
}
