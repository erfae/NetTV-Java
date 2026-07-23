package com.google.android.gms.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
@ShowFirstParty
@KeepForSdk
public class GoogleSignatureVerifier {

    @Nullable
    private static GoogleSignatureVerifier zza;
    private final Context zzb;
    private volatile String zzc;

    public GoogleSignatureVerifier(@NonNull Context context) {
        this.zzb = context.getApplicationContext();
    }

    @NonNull
    @KeepForSdk
    public static GoogleSignatureVerifier getInstance(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        synchronized (GoogleSignatureVerifier.class) {
            if (zza == null) {
                zzm.zzd(context);
                zza = new GoogleSignatureVerifier(context);
            }
        }
        return zza;
    }

    @Nullable
    public static final zzi zza(PackageInfo packageInfo, zzi... zziVarArr) {
        Signature[] signatureArr = packageInfo.signatures;
        if (signatureArr == null) {
            return null;
        }
        if (signatureArr.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        zzj zzjVar = new zzj(packageInfo.signatures[0].toByteArray());
        for (int i = 0; i < zziVarArr.length; i++) {
            if (zziVarArr[i].equals(zzjVar)) {
                return zziVarArr[i];
            }
        }
        return null;
    }

    public static final boolean zzb(@NonNull PackageInfo packageInfo, boolean z) {
        if (packageInfo != null && packageInfo.signatures != null) {
            if ((z ? zza(packageInfo, zzl.zza) : zza(packageInfo, zzl.zza[0])) != null) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint({"PackageManagerGetSignatures"})
    private final zzw zzc(String str, boolean z, boolean z2) {
        zzw zzwVarZzc;
        ApplicationInfo applicationInfo;
        if (str == null) {
            return zzw.zzc("null pkg");
        }
        if (str.equals(this.zzc)) {
            return zzw.zzb();
        }
        if (zzm.zze()) {
            zzwVarZzc = zzm.zzb(str, GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb));
        } else {
            try {
                PackageInfo packageInfo = this.zzb.getPackageManager().getPackageInfo(str, 64);
                boolean zHonorsDebugCertificates = GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb);
                if (packageInfo == null) {
                    zzwVarZzc = zzw.zzc("null pkg");
                } else {
                    Signature[] signatureArr = packageInfo.signatures;
                    if (signatureArr == null || signatureArr.length != 1) {
                        zzwVarZzc = zzw.zzc("single cert required");
                    } else {
                        zzj zzjVar = new zzj(packageInfo.signatures[0].toByteArray());
                        String str2 = packageInfo.packageName;
                        zzw zzwVarZza = zzm.zza(str2, zzjVar, zHonorsDebugCertificates, false);
                        zzwVarZzc = (!zzwVarZza.zza || (applicationInfo = packageInfo.applicationInfo) == null || (applicationInfo.flags & 2) == 0 || !zzm.zza(str2, zzjVar, false, true).zza) ? zzwVarZza : zzw.zzc("debuggable release cert app rejected");
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                return zzw.zzd(str.length() != 0 ? "no pkg ".concat(str) : new String("no pkg "), e);
            }
        }
        if (zzwVarZzc.zza) {
            this.zzc = str;
        }
        return zzwVarZzc;
    }

    @KeepForSdk
    public boolean isGooglePublicSignedPackage(@NonNull PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zzb(packageInfo, false)) {
            return true;
        }
        if (zzb(packageInfo, true)) {
            if (GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb)) {
                return true;
            }
            Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        }
        return false;
    }

    @ShowFirstParty
    @KeepForSdk
    public boolean isPackageGoogleSigned(@NonNull String str) {
        zzw zzwVarZzc = zzc(str, false, false);
        zzwVarZzc.zze();
        return zzwVarZzc.zza;
    }

    @ShowFirstParty
    @KeepForSdk
    public boolean isUidGoogleSigned(int i) {
        zzw zzwVarZzc;
        String[] packagesForUid = this.zzb.getPackageManager().getPackagesForUid(i);
        if (packagesForUid == null || (packagesForUid.length) == 0) {
            zzwVarZzc = zzw.zzc("no pkgs");
        } else {
            zzwVarZzc = null;
            for (String str : packagesForUid) {
                zzwVarZzc = zzc(str, false, false);
                if (!zzwVarZzc.zza) {
                }
            }
            Preconditions.checkNotNull(zzwVarZzc);
        }
        zzwVarZzc.zze();
        return zzwVarZzc.zza;
    }
}
