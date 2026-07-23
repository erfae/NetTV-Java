package com.google.android.material.color;

import android.content.Context;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.core.os.BuildCompat;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
interface ColorResourcesOverride {

    /* JADX INFO: renamed from: com.google.android.material.color.ColorResourcesOverride$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        @Nullable
        public static ColorResourcesOverride getInstance() {
            int i = Build.VERSION.SDK_INT;
            if (30 <= i && i <= 33) {
                return ResourcesLoaderColorResourcesOverride.ResourcesLoaderColorResourcesOverrideSingleton.INSTANCE;
            }
            if (BuildCompat.isAtLeastU()) {
                return ResourcesLoaderColorResourcesOverride.ResourcesLoaderColorResourcesOverrideSingleton.INSTANCE;
            }
            return null;
        }
    }

    boolean applyIfPossible(Context context, Map<Integer, Integer> map);
}
