package org.androidannotations.api.sharedpreferences;

import org.androidannotations.api.sharedpreferences.EditorHelper;

/* JADX INFO: loaded from: classes2.dex */
public final class FloatPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {
    public T put(float f) {
        this.editorHelper.getEditor().putFloat(this.key, f);
        return this.editorHelper;
    }
}
