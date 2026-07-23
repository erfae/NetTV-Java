package org.androidannotations.api.sharedpreferences;

import java.util.Set;
import org.androidannotations.api.sharedpreferences.EditorHelper;

/* JADX INFO: loaded from: classes2.dex */
public final class StringSetPrefEditorField<T extends EditorHelper<T>> extends AbstractPrefEditorField<T> {
    public T put(Set<String> set) {
        SharedPreferencesCompat.putStringSet(this.editorHelper.getEditor(), this.key, set);
        return this.editorHelper;
    }
}
