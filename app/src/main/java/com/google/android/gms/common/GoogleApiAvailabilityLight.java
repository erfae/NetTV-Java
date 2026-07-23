package com.google.android.gms.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.C;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.HideFirstParty;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.wrappers.Wrappers;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* JADX INFO: loaded from: classes.dex */
@ShowFirstParty
@KeepForSdk
public class GoogleApiAvailabilityLight {

    @NonNull
    @KeepForSdk
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";

    @NonNull
    @KeepForSdk
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    @KeepForSdk
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final GoogleApiAvailabilityLight zza = new GoogleApiAvailabilityLight();

    @KeepForSdk
    public GoogleApiAvailabilityLight() {
    }

    @NonNull
    @KeepForSdk
    public static GoogleApiAvailabilityLight getInstance() {
        return zza;
    }

    @KeepForSdk
    public void cancelAvailabilityErrorNotifications(@NonNull Context context) {
        GooglePlayServicesUtilLight.cancelAvailabilityErrorNotifications(context);
    }

    @ShowFirstParty
    @KeepForSdk
    public int getApkVersion(@NonNull Context context) {
        return GooglePlayServicesUtilLight.getApkVersion(context);
    }

    @ShowFirstParty
    @KeepForSdk
    public int getClientVersion(@NonNull Context context) {
        return GooglePlayServicesUtilLight.getClientVersion(context);
    }

    @Deprecated
    @Nullable
    @ShowFirstParty
    @KeepForSdk
    public Intent getErrorResolutionIntent(int i) {
        return getErrorResolutionIntent(null, i, null);
    }

    @Nullable
    @KeepForSdk
    public PendingIntent getErrorResolutionPendingIntent(@NonNull Context context, int i, int i2) {
        return getErrorResolutionPendingIntent(context, i, i2, null);
    }

    @NonNull
    @KeepForSdk
    public String getErrorString(int i) {
        return GooglePlayServicesUtilLight.getErrorString(i);
    }

    @HideFirstParty
    @KeepForSdk
    public int isGooglePlayServicesAvailable(@NonNull Context context) {
        return isGooglePlayServicesAvailable(context, GOOGLE_PLAY_SERVICES_VERSION_CODE);
    }

    @ShowFirstParty
    @KeepForSdk
    public boolean isPlayServicesPossiblyUpdating(@NonNull Context context, int i) {
        return GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(context, i);
    }

    @ShowFirstParty
    @KeepForSdk
    public boolean isPlayStorePossiblyUpdating(@NonNull Context context, int i) {
        return GooglePlayServicesUtilLight.isPlayStorePossiblyUpdating(context, i);
    }

    @KeepForSdk
    public boolean isUninstalledAppPossiblyUpdating(@NonNull Context context, @NonNull String str) {
        return GooglePlayServicesUtilLight.zza(context, str);
    }

    @KeepForSdk
    public boolean isUserResolvableError(int i) {
        return GooglePlayServicesUtilLight.isUserRecoverableError(i);
    }

    @KeepForSdk
    public void verifyGooglePlayServicesIsAvailable(@NonNull Context context, int i) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        GooglePlayServicesUtilLight.ensurePlayServicesAvailable(context, i);
    }

    @Nullable
    @ShowFirstParty
    @KeepForSdk
    public Intent getErrorResolutionIntent(@Nullable Context context, int i, @Nullable String str) {
        if (i != 1 && i != 2) {
            if (i != 3) {
                return null;
            }
            return com.google.android.gms.common.internal.zzt.zzc("com.google.android.gms");
        }
        if (context != null && DeviceProperties.isWearableWithoutPlayStore(context)) {
            return com.google.android.gms.common.internal.zzt.zza();
        }
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("gcore_");
        sbM.append(GOOGLE_PLAY_SERVICES_VERSION_CODE);
        sbM.append("-");
        if (!TextUtils.isEmpty(str)) {
            sbM.append(str);
        }
        sbM.append("-");
        if (context != null) {
            sbM.append(context.getPackageName());
        }
        sbM.append("-");
        if (context != null) {
            try {
                sbM.append(Wrappers.packageManager(context).getPackageInfo(context.getPackageName(), 0).versionCode);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return com.google.android.gms.common.internal.zzt.zzb("com.google.android.gms", sbM.toString());
    }

    @Nullable
    @ShowFirstParty
    @KeepForSdk
    public PendingIntent getErrorResolutionPendingIntent(@NonNull Context context, int i, int i2, @Nullable String str) {
        Intent errorResolutionIntent = getErrorResolutionIntent(context, i, str);
        if (errorResolutionIntent == null) {
            return null;
        }
        return com.google.android.gms.internal.common.zzd.zza(context, i2, errorResolutionIntent, com.google.android.gms.internal.common.zzd.zza | C.BUFFER_FLAG_FIRST_SAMPLE);
    }

    @KeepForSdk
    public int isGooglePlayServicesAvailable(@NonNull Context context, int i) {
        int iIsGooglePlayServicesAvailable = GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(context, i);
        if (GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(context, iIsGooglePlayServicesAvailable)) {
            return 18;
        }
        return iIsGooglePlayServicesAvailable;
    }
}
