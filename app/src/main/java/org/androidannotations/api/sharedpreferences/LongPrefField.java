package org.androidannotations.api.sharedpreferences;

/* JADX INFO: loaded from: classes2.dex */
public final class LongPrefField extends AbstractPrefField {
    private final long defaultValue;

    public long get() {
        return getOr(this.defaultValue);
    }

    public long getOr(long j) {
        try {
            return this.sharedPreferences.getLong(this.key, j);
        } catch (ClassCastException e) {
            try {
                return Long.parseLong(this.sharedPreferences.getString(this.key, "" + j));
            } catch (Exception unused) {
                throw e;
            }
        }
    }

    public void put(long j) {
        SharedPreferencesCompat.apply(edit().putLong(this.key, j));
    }
}
