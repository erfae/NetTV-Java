package org.androidannotations.api.sharedpreferences;

import android.content.SharedPreferences;
import org.androidannotations.api.sharedpreferences.EditorHelper;

/* JADX INFO: loaded from: classes2.dex */
public abstract class EditorHelper<T extends EditorHelper<T>> {
    private final SharedPreferences.Editor editor;

    public EditorHelper(SharedPreferences sharedPreferences) {
        this.editor = sharedPreferences.edit();
    }

    private T cast() {
        return this;
    }

    public final void apply() {
        SharedPreferencesCompat.apply(this.editor);
    }

    public final T clear() {
        this.editor.clear();
        return (T) cast();
    }

    public final SharedPreferences.Editor getEditor() {
        return this.editor;
    }
}
