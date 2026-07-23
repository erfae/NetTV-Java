package org.androidannotations.api.sharedpreferences;

import org.androidannotations.api.sharedpreferences.EditorHelper;

/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractPrefEditorField<T extends EditorHelper<T>> {
    public final T editorHelper;
    public final String key;

    public AbstractPrefEditorField(T t, String str) {
        this.editorHelper = t;
        this.key = str;
    }

    public final T remove() {
        this.editorHelper.getEditor().remove(this.key);
        return this.editorHelper;
    }
}
