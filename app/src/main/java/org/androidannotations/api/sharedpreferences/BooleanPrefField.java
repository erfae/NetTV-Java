package org.androidannotations.api.sharedpreferences;

/* JADX INFO: loaded from: classes2.dex */
public final class BooleanPrefField extends AbstractPrefField {
    private final boolean defaultValue;

    public boolean get() {
        return getOr(this.defaultValue);
    }

    public boolean getOr(boolean z) {
        return this.sharedPreferences.getBoolean(this.key, z);
    }

    public void put(boolean z) {
        SharedPreferencesCompat.apply(edit().putBoolean(this.key, z));
    }
}
