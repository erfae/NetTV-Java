package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public class SplitInstallHelper {
    private static final com.google.android.play.core.internal.zzag zza = new com.google.android.play.core.internal.zzag("SplitInstallHelper");

    private SplitInstallHelper() {
    }

    public static void loadLibrary(@NonNull Context context, @NonNull String str) throws UnsatisfiedLinkError {
        synchronized (zzn.class) {
            try {
                System.loadLibrary(str);
            } catch (UnsatisfiedLinkError e) {
                String str2 = context.getApplicationInfo().nativeLibraryDir;
                String strMapLibraryName = System.mapLibraryName(str);
                StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 1 + String.valueOf(strMapLibraryName).length());
                sb.append(str2);
                sb.append("/");
                sb.append(strMapLibraryName);
                String string = sb.toString();
                if (!new File(string).exists()) {
                    throw e;
                }
                System.load(string);
            }
        }
    }

    public static void updateAppInfo(@NonNull Context context) {
        int i = Build.VERSION.SDK_INT;
        if (i <= 25 || i >= 28) {
            return;
        }
        com.google.android.play.core.internal.zzag zzagVar = zza;
        zzagVar.zzd("Calling dispatchPackageBroadcast", new Object[0]);
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Method method = cls.getMethod("currentActivityThread", new Class[0]);
            method.setAccessible(true);
            Object objInvoke = method.invoke(null, new Object[0]);
            Field declaredField = cls.getDeclaredField("mAppThread");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(objInvoke);
            obj.getClass().getMethod("dispatchPackageBroadcast", Integer.TYPE, String[].class).invoke(obj, 3, new String[]{context.getPackageName()});
            zzagVar.zzd("Called dispatchPackageBroadcast", new Object[0]);
        } catch (Exception e) {
            zza.zzc(e, "Update app info with dispatchPackageBroadcast failed!", new Object[0]);
        }
    }
}
