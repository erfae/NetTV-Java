package org.androidannotations.api;

import android.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public class SdkVersionHelper {

    public static class HelperInternal {
        private HelperInternal() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int getSdkIntInternal() {
            return Build.VERSION.SDK_INT;
        }
    }

    public static int getSdkInt() {
        if (Build.VERSION.RELEASE.startsWith("1.5")) {
            return 3;
        }
        return HelperInternal.getSdkIntInternal();
    }
}
