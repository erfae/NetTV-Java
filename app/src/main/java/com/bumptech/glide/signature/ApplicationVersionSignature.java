package com.bumptech.glide.signature;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.bumptech.glide.load.Key;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes.dex */
public final class ApplicationVersionSignature {
    private static final ConcurrentMap<String, Key> PACKAGE_NAME_TO_KEY = new ConcurrentHashMap();
    private static final String TAG = "AppVersionSignature";

    private ApplicationVersionSignature() {
    }

    @Nullable
    private static PackageInfo getPackageInfo(@NonNull Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Cannot resolve info for");
            sbM.append(context.getPackageName());
            Log.e(TAG, sbM.toString(), e);
            return null;
        }
    }

    @NonNull
    private static String getVersionCode(@Nullable PackageInfo packageInfo) {
        return packageInfo != null ? String.valueOf(packageInfo.versionCode) : UUID.randomUUID().toString();
    }

    @NonNull
    public static Key obtain(@NonNull Context context) {
        String packageName = context.getPackageName();
        ConcurrentMap<String, Key> concurrentMap = PACKAGE_NAME_TO_KEY;
        Key key = concurrentMap.get(packageName);
        if (key != null) {
            return key;
        }
        Key keyObtainVersionSignature = obtainVersionSignature(context);
        Key keyPutIfAbsent = concurrentMap.putIfAbsent(packageName, keyObtainVersionSignature);
        return keyPutIfAbsent == null ? keyObtainVersionSignature : keyPutIfAbsent;
    }

    @NonNull
    private static Key obtainVersionSignature(@NonNull Context context) {
        return new ObjectKey(getVersionCode(getPackageInfo(context)));
    }
}
