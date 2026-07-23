package com.google.android.play.core.splitcompat;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.StrictMode;
import android.util.Log;
import com.google.android.play.core.internal.zzbw;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zza {
    private final zze zza;

    public zza(zze zzeVar) {
        this.zza = zzeVar;
    }

    public static final int zzc(AssetManager assetManager, File file) {
        int iIntValue = ((Integer) zzbw.zzd(assetManager, "addAssetPath", Integer.class, String.class, file.getPath())).intValue();
        StringBuilder sb = new StringBuilder(39);
        sb.append("addAssetPath completed with ");
        sb.append(iIntValue);
        Log.d("SplitCompat", sb.toString());
        return iIntValue;
    }

    public final synchronized void zza(Context context, Set set) {
        AssetManager assets = context.getAssets();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            zzc(assets, (File) it.next());
        }
    }

    public final synchronized boolean zzb(Context context, Set set) {
        StrictMode.ThreadPolicy threadPolicy;
        boolean z;
        try {
            threadPolicy = StrictMode.getThreadPolicy();
            try {
                StrictMode.allowThreadDiskReads();
                StrictMode.allowThreadDiskWrites();
            } catch (Exception e) {
                e = e;
                Log.i("SplitCompat", "Unable to set up strict mode.", e);
            }
        } catch (Exception e2) {
            e = e2;
            threadPolicy = null;
        }
        try {
            try {
                HashSet hashSet = new HashSet();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    hashSet.add(this.zza.zzg((String) it.next()));
                }
                zza(context, hashSet);
                if (threadPolicy != null) {
                    StrictMode.setThreadPolicy(threadPolicy);
                }
                z = true;
            } catch (Exception e3) {
                Log.e("SplitCompat", "Error installing additional splits", e3);
                if (threadPolicy != null) {
                    StrictMode.setThreadPolicy(threadPolicy);
                }
                z = false;
            }
        } catch (Throwable th) {
            if (threadPolicy != null) {
                StrictMode.setThreadPolicy(threadPolicy);
            }
            throw th;
        }
        return z;
    }
}
