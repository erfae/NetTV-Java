package org.androidannotations.api.sharedpreferences;

import android.content.SharedPreferences;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public final class StringSetPrefField extends AbstractPrefField {
    private final Set<String> defaultValue;

    public Set<String> get() {
        return getOr(this.defaultValue);
    }

    public Set<String> getOr(Set<String> set) {
        return SharedPreferencesCompat.getStringSet(this.sharedPreferences, this.key, set);
    }

    public void put(Set<String> set) {
        SharedPreferences.Editor editorEdit = this.sharedPreferences.edit();
        SharedPreferencesCompat.putStringSet(editorEdit, this.key, set);
        SharedPreferencesCompat.apply(editorEdit);
    }
}
