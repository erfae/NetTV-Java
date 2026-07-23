package com.google.android.gms.dynamite;

import android.os.Looper;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
public final class zzb {

    @Nullable
    @GuardedBy("DynamiteLoaderV2ClassLoader.class")
    private static volatile ClassLoader zza;

    @Nullable
    @GuardedBy("DynamiteLoaderV2ClassLoader.class")
    private static volatile Thread zzb;

    @Nullable
    public static synchronized ClassLoader zza() {
        if (zza == null) {
            zza = zzb();
        }
        return zza;
    }

    @Nullable
    private static synchronized ClassLoader zzb() {
        ClassLoader contextClassLoader = null;
        if (zzb == null) {
            zzb = zzc();
            if (zzb == null) {
                return null;
            }
        }
        synchronized (zzb) {
            try {
                contextClassLoader = zzb.getContextClassLoader();
            } catch (SecurityException e) {
                String strValueOf = String.valueOf(e.getMessage());
                Log.w("DynamiteLoaderV2CL", strValueOf.length() != 0 ? "Failed to get thread context classloader ".concat(strValueOf) : new String("Failed to get thread context classloader "));
            }
        }
        return contextClassLoader;
    }

    @Nullable
    private static synchronized Thread zzc() {
        SecurityException e;
        Thread zzaVar;
        Thread thread;
        ThreadGroup threadGroup;
        ThreadGroup threadGroup2 = Looper.getMainLooper().getThread().getThreadGroup();
        if (threadGroup2 == null) {
            return null;
        }
        synchronized (Void.class) {
            try {
                int iActiveGroupCount = threadGroup2.activeGroupCount();
                ThreadGroup[] threadGroupArr = new ThreadGroup[iActiveGroupCount];
                threadGroup2.enumerate(threadGroupArr);
                int i = 0;
                int i2 = 0;
                while (true) {
                    if (i2 >= iActiveGroupCount) {
                        threadGroup = null;
                        break;
                    }
                    threadGroup = threadGroupArr[i2];
                    if ("dynamiteLoader".equals(threadGroup.getName())) {
                        break;
                    }
                    i2++;
                }
                if (threadGroup == null) {
                    threadGroup = new ThreadGroup(threadGroup2, "dynamiteLoader");
                }
                int iActiveCount = threadGroup.activeCount();
                Thread[] threadArr = new Thread[iActiveCount];
                threadGroup.enumerate(threadArr);
                while (true) {
                    if (i >= iActiveCount) {
                        thread = null;
                        break;
                    }
                    thread = threadArr[i];
                    if ("GmsDynamite".equals(thread.getName())) {
                        break;
                    }
                    i++;
                }
                if (thread == null) {
                    try {
                        zzaVar = new zza(threadGroup);
                        try {
                            zzaVar.setContextClassLoader(null);
                            zzaVar.start();
                        } catch (SecurityException e2) {
                            e = e2;
                            String strValueOf = String.valueOf(e.getMessage());
                            Log.w("DynamiteLoaderV2CL", strValueOf.length() != 0 ? "Failed to enumerate thread/threadgroup ".concat(strValueOf) : new String("Failed to enumerate thread/threadgroup "));
                        }
                    } catch (SecurityException e3) {
                        e = e3;
                        zzaVar = thread;
                    }
                    thread = zzaVar;
                }
            } catch (SecurityException e4) {
                e = e4;
                zzaVar = null;
            }
        }
        return thread;
    }
}
