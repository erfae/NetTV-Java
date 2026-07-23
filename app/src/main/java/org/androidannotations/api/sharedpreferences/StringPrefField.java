package org.androidannotations.api.sharedpreferences;

/* JADX INFO: loaded from: classes2.dex */
public final class StringPrefField extends AbstractPrefField {
    private final String defaultValue;

    public String get() {
        return getOr(this.defaultValue);
    }

    public String getOr(String str) {
        return this.sharedPreferences.getString(this.key, str);
    }

    public void put(String str) {
        SharedPreferencesCompat.apply(edit().putString(this.key, str));
    }
}
