package org.androidannotations.api.sharedpreferences;

import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes2.dex */
public abstract class SharedPreferencesHelper {
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public final void clear() {
        SharedPreferencesCompat.apply(this.sharedPreferences.edit().clear());
    }

    public final SharedPreferences getSharedPreferences() {
        return this.sharedPreferences;
    }
}
