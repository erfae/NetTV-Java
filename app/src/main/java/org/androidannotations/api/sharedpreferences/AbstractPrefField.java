package org.androidannotations.api.sharedpreferences;

import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractPrefField {
    public final String key;
    public final SharedPreferences sharedPreferences;

    public AbstractPrefField(SharedPreferences sharedPreferences, String str) {
        this.sharedPreferences = sharedPreferences;
        this.key = str;
    }

    public final SharedPreferences.Editor edit() {
        return this.sharedPreferences.edit();
    }

    public final boolean exists() {
        return this.sharedPreferences.contains(this.key);
    }

    public String key() {
        return this.key;
    }

    public final void remove() {
        SharedPreferencesCompat.apply(edit().remove(this.key));
    }
}
