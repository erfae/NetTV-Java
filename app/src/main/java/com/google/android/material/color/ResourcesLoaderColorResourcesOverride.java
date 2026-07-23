package com.google.android.material.color;

import android.content.Context;
import androidx.annotation.RequiresApi;
import com.google.android.material.R;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi(api = 30)
class ResourcesLoaderColorResourcesOverride implements ColorResourcesOverride {

    public static class ResourcesLoaderColorResourcesOverrideSingleton {
        private static final ResourcesLoaderColorResourcesOverride INSTANCE = new ResourcesLoaderColorResourcesOverride();

        private ResourcesLoaderColorResourcesOverrideSingleton() {
        }
    }

    @Override // com.google.android.material.color.ColorResourcesOverride
    public boolean applyIfPossible(Context context, Map<Integer, Integer> map) {
        if (!ResourcesLoaderUtils.addResourcesLoaderToContext(context, map)) {
            return false;
        }
        ThemeUtils.applyThemeOverlay(context, R.style.ThemeOverlay_Material3_PersonalizedColors);
        return true;
    }

    private ResourcesLoaderColorResourcesOverride() {
    }
}
