package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.StrictMode;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzae;
import com.google.android.gms.common.internal.zzaf;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.gms.common.util.Hex;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import java.security.MessageDigest;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzm {
    private static volatile zzaf zze;
    private static Context zzg;
    public static final zze zza = new zze(zzi.zze("0\u0082\u0005È0\u0082\u0003° \u0003\u0002\u0001\u0002\u0002\u0014\u0010\u008ae\bsù/\u008eQí"));
    public static final zzf zzb = new zzf(zzi.zze("0\u0082\u0006\u00040\u0082\u0003ì \u0003\u0002\u0001\u0002\u0002\u0014\u0003£²\u00ad×árÊkì"));
    public static final zzg zzc = new zzg(zzi.zze("0\u0082\u0004C0\u0082\u0003+ \u0003\u0002\u0001\u0002\u0002\t\u0000Âà\u0087FdJ0\u008d0"));
    public static final zzh zzd = new zzh(zzi.zze("0\u0082\u0004¨0\u0082\u0003\u0090 \u0003\u0002\u0001\u0002\u0002\t\u0000Õ\u0085¸l}ÓNõ0"));
    private static final Object zzf = new Object();

    public static zzw zza(String str, zzi zziVar, boolean z, boolean z2) {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            return zzf(str, zziVar, z, z2);
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }

    /* JADX WARN: Type inference failed for: r8v0, types: [android.os.IBinder, com.google.android.gms.dynamic.IObjectWrapper] */
    public static zzw zzb(String str, boolean z) {
        zzw zzwVarZzd;
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            Preconditions.checkNotNull(zzg);
            try {
                zzg();
                try {
                    zzq zzqVarZze = zze.zze(new zzn(str, z, false, ObjectWrapper.wrap(zzg), false));
                    if (zzqVarZze.zzb()) {
                        zzwVarZzd = zzw.zzb();
                    } else {
                        String strZza = zzqVarZze.zza();
                        if (strZza == null) {
                            strZza = "error checking package certificate";
                        }
                        zzwVarZzd = zzqVarZze.zzc() == 4 ? zzw.zzd(strZza, new PackageManager.NameNotFoundException()) : zzw.zzc(strZza);
                    }
                } catch (RemoteException e) {
                    Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                    zzwVarZzd = zzw.zzd("module call", e);
                }
            } catch (DynamiteModule.LoadingException e2) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e2);
                String strValueOf = String.valueOf(e2.getMessage());
                zzwVarZzd = zzw.zzd(strValueOf.length() != 0 ? "module init: ".concat(strValueOf) : new String("module init: "), e2);
            }
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            return zzwVarZzd;
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            throw th;
        }
    }

    public static /* synthetic */ String zzc(boolean z, String str, zzi zziVar) throws Exception {
        String str2 = true != (!z && zzf(str, zziVar, true, false).zza) ? "not allowed" : "debug cert rejected";
        MessageDigest messageDigestZza = AndroidUtilsLight.zza("SHA-1");
        Preconditions.checkNotNull(messageDigestZza);
        return String.format("%s: pkg=%s, sha1=%s, atk=%s, ver=%s", str2, str, Hex.bytesToStringLowercase(messageDigestZza.digest(zziVar.zzf())), Boolean.valueOf(z), "12451000.false");
    }

    public static synchronized void zzd(Context context) {
        if (zzg != null) {
            Log.w("GoogleCertificates", "GoogleCertificates has been initialized already");
        } else if (context != null) {
            zzg = context.getApplicationContext();
        }
    }

    public static boolean zze() {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            try {
                zzg();
                return zze.zzg();
            } catch (RemoteException | DynamiteModule.LoadingException e) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                return false;
            }
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }

    private static zzw zzf(final String str, final zzi zziVar, final boolean z, boolean z2) {
        try {
            zzg();
            Preconditions.checkNotNull(zzg);
            try {
                return zze.zzf(new zzs(str, zziVar, z, z2), ObjectWrapper.wrap(zzg.getPackageManager())) ? zzw.zzb() : new zzv(new Callable() { // from class: com.google.android.gms.common.zzd
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        return zzm.zzc(z, str, zziVar);
                    }
                });
            } catch (RemoteException e) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                return zzw.zzd("module call", e);
            }
        } catch (DynamiteModule.LoadingException e2) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e2);
            String strValueOf = String.valueOf(e2.getMessage());
            return zzw.zzd(strValueOf.length() != 0 ? "module init: ".concat(strValueOf) : new String("module init: "), e2);
        }
    }

    private static void zzg() throws DynamiteModule.LoadingException {
        if (zze != null) {
            return;
        }
        Preconditions.checkNotNull(zzg);
        synchronized (zzf) {
            if (zze == null) {
                zze = zzae.zzb(DynamiteModule.load(zzg, DynamiteModule.PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING, "com.google.android.gms.googlecertificates").instantiate("com.google.android.gms.common.GoogleCertificatesImpl"));
            }
        }
    }
}
