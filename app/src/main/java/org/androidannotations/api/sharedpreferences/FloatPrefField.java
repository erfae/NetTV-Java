package org.androidannotations.api.sharedpreferences;

/* JADX INFO: loaded from: classes2.dex */
public final class FloatPrefField extends AbstractPrefField {
    private final float defaultValue;

    public float get() {
        return getOr(this.defaultValue);
    }

    public float getOr(float f) {
        try {
            return this.sharedPreferences.getFloat(this.key, f);
        } catch (ClassCastException e) {
            try {
                return Float.parseFloat(this.sharedPreferences.getString(this.key, "" + f));
            } catch (Exception unused) {
                throw e;
            }
        }
    }

    public void put(float f) {
        SharedPreferencesCompat.apply(edit().putFloat(this.key, f));
    }
}
