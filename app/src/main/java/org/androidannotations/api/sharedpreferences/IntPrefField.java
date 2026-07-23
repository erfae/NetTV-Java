package org.androidannotations.api.sharedpreferences;

/* JADX INFO: loaded from: classes2.dex */
public final class IntPrefField extends AbstractPrefField {
    private final int defaultValue;

    public int get() {
        return getOr(this.defaultValue);
    }

    public int getOr(int i) {
        try {
            return this.sharedPreferences.getInt(this.key, i);
        } catch (ClassCastException e) {
            try {
                return Integer.parseInt(this.sharedPreferences.getString(this.key, "" + i));
            } catch (Exception unused) {
                throw e;
            }
        }
    }

    public void put(int i) {
        SharedPreferencesCompat.apply(edit().putInt(this.key, i));
    }
}
