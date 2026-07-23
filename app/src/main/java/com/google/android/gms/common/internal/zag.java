package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.common.api.internal.LifecycleFragment;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
public abstract class zag implements DialogInterface.OnClickListener {
    public static zag zab(Activity activity, @Nullable Intent intent, int i) {
        return new zad(intent, activity, i);
    }

    public static zag zac(@NonNull Fragment fragment, @Nullable Intent intent, int i) {
        return new zae(intent, fragment, i);
    }

    public static zag zad(@NonNull LifecycleFragment lifecycleFragment, @Nullable Intent intent, int i) {
        return new zaf(intent, lifecycleFragment);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        try {
            zaa();
        } catch (ActivityNotFoundException e) {
            Log.e("DialogRedirect", true == Build.FINGERPRINT.contains("generic") ? "Failed to start resolution intent. This may occur when resolving Google Play services connection issues on emulators with Google APIs but not Google Play Store." : "Failed to start resolution intent.", e);
        } finally {
            dialogInterface.dismiss();
        }
    }

    public abstract void zaa();
}
