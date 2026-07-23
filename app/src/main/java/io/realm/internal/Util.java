package io.realm.internal;

import android.os.Build;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.os.EnvironmentCompat;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.internal.android.AndroidCapabilities;
import io.realm.log.RealmLog;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public class Util {
    private static Boolean coroutinesAvailable;
    private static Boolean rxJavaAvailable;

    public static void checkContainsKey(String str, Map<String, ?> map, String str2) {
        if (map.containsKey(str)) {
            return;
        }
        throw new IllegalArgumentException("Key '" + str + "' required in '" + str2 + "'.");
    }

    public static void checkEmpty(String str, String str2) {
        if (isEmptyString(str)) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Non-empty '", str2, "' required."));
        }
    }

    public static void checkLooperThread(String str) {
        new AndroidCapabilities().checkCanDeliverNotification(str);
    }

    public static void checkNotOnMainThread(String str) {
        if (new AndroidCapabilities().isMainThread()) {
            throw new IllegalStateException(str);
        }
    }

    public static void checkNull(@Nullable Object obj, String str) {
        if (obj == null) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Nonnull '", str, "' required."));
        }
    }

    public static boolean deleteRealm(String str, File file, String str2) {
        boolean zDelete;
        File file2 = new File(file, Insets$$ExternalSyntheticOutline0.m(str2, ".management"));
        File file3 = new File(str);
        File file4 = new File(Insets$$ExternalSyntheticOutline0.m(str, ".note"));
        File[] fileArrListFiles = file2.listFiles();
        if (fileArrListFiles != null) {
            for (File file5 : fileArrListFiles) {
                if (!file5.delete()) {
                    RealmLog.warn(String.format(Locale.ENGLISH, "Realm temporary file at %s cannot be deleted", file5.getAbsolutePath()), new Object[0]);
                }
            }
        }
        if (file2.exists() && !file2.delete()) {
            RealmLog.warn(String.format(Locale.ENGLISH, "Realm temporary folder at %s cannot be deleted", file2.getAbsolutePath()), new Object[0]);
        }
        if (file3.exists()) {
            zDelete = file3.delete();
            if (!zDelete) {
                RealmLog.warn(String.format(Locale.ENGLISH, "Realm file at %s cannot be deleted", file3.getAbsolutePath()), new Object[0]);
            }
        } else {
            zDelete = true;
        }
        if (file4.exists() && !file4.delete()) {
            RealmLog.warn(String.format(Locale.ENGLISH, ".note file at %s cannot be deleted", file4.getAbsolutePath()), new Object[0]);
        }
        return zDelete;
    }

    public static Class<?> getClassForName(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m("Class '", str, "' does not exist."));
        }
    }

    public static Class<? extends RealmModel> getOriginalModelClass(Class<? extends RealmModel> cls) {
        if (cls.equals(RealmModel.class) || cls.equals(RealmObject.class)) {
            throw new IllegalArgumentException("RealmModel or RealmObject was passed as an argument. Only subclasses of these can be used as arguments to methods that accept a Realm model class.");
        }
        Class superclass = cls.getSuperclass();
        return (superclass.equals(Object.class) || superclass.equals(RealmObject.class)) ? cls : superclass;
    }

    public static String getStackTrace(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter((Writer) stringWriter, true));
        return stringWriter.getBuffer().toString();
    }

    public static String getTablePrefix() {
        return nativeGetTablePrefix();
    }

    public static synchronized boolean isCoroutinesAvailable() {
        if (coroutinesAvailable == null) {
            try {
                coroutinesAvailable = Boolean.TRUE;
            } catch (ClassNotFoundException unused) {
                coroutinesAvailable = Boolean.FALSE;
            }
        }
        return coroutinesAvailable.booleanValue();
    }

    public static boolean isEmptyString(@Nullable String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmulator() {
        String str = Build.FINGERPRINT;
        if (!str.startsWith("generic") && !str.startsWith(EnvironmentCompat.MEDIA_UNKNOWN)) {
            String str2 = Build.MODEL;
            if (!str2.contains("google_sdk") && !str2.contains("Emulator") && !str2.contains("Android SDK built for x86") && !Build.MANUFACTURER.contains("Genymotion") && ((!Build.BRAND.startsWith("generic") || !Build.DEVICE.startsWith("generic")) && !"google_sdk".equals(Build.PRODUCT))) {
                return false;
            }
        }
        return true;
    }

    public static synchronized boolean isRxJavaAvailable() {
        if (rxJavaAvailable == null) {
            try {
                Class.forName("io.reactivex.Flowable");
                rxJavaAvailable = Boolean.TRUE;
            } catch (ClassNotFoundException unused) {
                rxJavaAvailable = Boolean.FALSE;
            }
        }
        return rxJavaAvailable.booleanValue();
    }

    public static native String nativeGetTablePrefix();

    public static <T> Set<T> toSet(T... tArr) {
        if (tArr == null) {
            return Collections.emptySet();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (T t : tArr) {
            if (t != null) {
                linkedHashSet.add(t);
            }
        }
        return linkedHashSet;
    }
}
