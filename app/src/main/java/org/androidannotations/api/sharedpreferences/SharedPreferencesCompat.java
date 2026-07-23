package org.androidannotations.api.sharedpreferences;

import android.content.SharedPreferences;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public abstract class SharedPreferencesCompat {
    private static final Method sApplyMethod = findMethod(SharedPreferences.Editor.class, "apply", new Class[0]);
    private static final Method sGetStringSetMethod = findMethod(SharedPreferences.class, "getStringSet", String.class, Set.class);
    private static final Method sPutStringSetMethod = findMethod(SharedPreferences.Editor.class, "putStringSet", String.class, Set.class);

    private SharedPreferencesCompat() {
    }

    public static void apply(SharedPreferences.Editor editor) {
        try {
            invoke(sApplyMethod, editor, new Object[0]);
        } catch (NoSuchMethodException unused) {
            editor.commit();
        }
    }

    private static Method findMethod(Class<?> cls, String str, Class<?>... clsArr) {
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static Set<String> getStringSet(SharedPreferences sharedPreferences, String str, Set<String> set) {
        try {
            return (Set) invoke(sGetStringSetMethod, sharedPreferences, str, set);
        } catch (NoSuchMethodException unused) {
            return SetXmlSerializer.deserialize(sharedPreferences.getString(str, null));
        }
    }

    public static <T> T invoke(Method method, Object obj, Object... objArr) throws NoSuchMethodException {
        if (method == null) {
            throw new NoSuchMethodException();
        }
        try {
            return (T) method.invoke(obj, objArr);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
            throw new NoSuchMethodException(method.getName());
        }
    }

    public static void putStringSet(SharedPreferences.Editor editor, String str, Set<String> set) {
        try {
            invoke(sPutStringSetMethod, editor, str, set);
        } catch (NoSuchMethodException unused) {
            editor.putString(str, SetXmlSerializer.serialize(set));
        }
    }
}
