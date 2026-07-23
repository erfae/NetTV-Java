package com.google.android.material.color;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.core.os.BuildCompat;
import com.google.android.material.R;
import com.google.android.material.color.utilities.Scheme;
import com.google.android.material.resources.MaterialAttributes;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class DynamicColors {
    private static final DeviceSupportCondition DEFAULT_DEVICE_SUPPORT_CONDITION;
    private static final Map<String, DeviceSupportCondition> DYNAMIC_COLOR_SUPPORTED_BRANDS;
    private static final Map<String, DeviceSupportCondition> DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS;
    private static final int[] DYNAMIC_COLOR_THEME_OVERLAY_ATTRIBUTE = {R.attr.dynamicColorThemeOverlay};

    @SuppressLint({"PrivateApi"})
    private static final DeviceSupportCondition SAMSUNG_DEVICE_SUPPORT_CONDITION;
    private static final int USE_DEFAULT_THEME_OVERLAY = 0;

    public interface DeviceSupportCondition {
        boolean isSupported();
    }

    public static class DynamicColorsActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        private final DynamicColorsOptions dynamicColorsOptions;

        public DynamicColorsActivityLifecycleCallbacks(@NonNull DynamicColorsOptions dynamicColorsOptions) {
            this.dynamicColorsOptions = dynamicColorsOptions;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(@NonNull Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(@NonNull Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
            DynamicColors.applyToActivityIfAvailable(activity, this.dynamicColorsOptions);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(@NonNull Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(@NonNull Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(@NonNull Activity activity) {
        }
    }

    public interface OnAppliedCallback {
        void onApplied(@NonNull Activity activity);
    }

    public interface Precondition {
        boolean shouldApplyDynamicColors(@NonNull Activity activity, @StyleRes int i);
    }

    static {
        DeviceSupportCondition deviceSupportCondition = new DeviceSupportCondition() { // from class: com.google.android.material.color.DynamicColors.1
            @Override // com.google.android.material.color.DynamicColors.DeviceSupportCondition
            public boolean isSupported() {
                return true;
            }
        };
        DEFAULT_DEVICE_SUPPORT_CONDITION = deviceSupportCondition;
        DeviceSupportCondition deviceSupportCondition2 = new DeviceSupportCondition() { // from class: com.google.android.material.color.DynamicColors.2
            private Long version;

            @Override // com.google.android.material.color.DynamicColors.DeviceSupportCondition
            public boolean isSupported() {
                if (this.version == null) {
                    try {
                        Method declaredMethod = Build.class.getDeclaredMethod("getLong", String.class);
                        declaredMethod.setAccessible(true);
                        this.version = Long.valueOf(((Long) declaredMethod.invoke(null, "ro.build.version.oneui")).longValue());
                    } catch (Exception unused) {
                        this.version = -1L;
                    }
                }
                return this.version.longValue() >= 40100;
            }
        };
        SAMSUNG_DEVICE_SUPPORT_CONDITION = deviceSupportCondition2;
        HashMap map = new HashMap();
        map.put("fcnt", deviceSupportCondition);
        map.put("google", deviceSupportCondition);
        map.put("hmd global", deviceSupportCondition);
        map.put("infinix", deviceSupportCondition);
        map.put("infinix mobility limited", deviceSupportCondition);
        map.put("itel", deviceSupportCondition);
        map.put("kyocera", deviceSupportCondition);
        map.put("lenovo", deviceSupportCondition);
        map.put("lge", deviceSupportCondition);
        map.put("motorola", deviceSupportCondition);
        map.put("nothing", deviceSupportCondition);
        map.put("oneplus", deviceSupportCondition);
        map.put("oppo", deviceSupportCondition);
        map.put("realme", deviceSupportCondition);
        map.put("robolectric", deviceSupportCondition);
        map.put("samsung", deviceSupportCondition2);
        map.put("sharp", deviceSupportCondition);
        map.put("sony", deviceSupportCondition);
        map.put("tcl", deviceSupportCondition);
        map.put("tecno", deviceSupportCondition);
        map.put("tecno mobile limited", deviceSupportCondition);
        map.put("vivo", deviceSupportCondition);
        map.put("wingtech", deviceSupportCondition);
        map.put("xiaomi", deviceSupportCondition);
        DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS = Collections.unmodifiableMap(map);
        HashMap map2 = new HashMap();
        map2.put("asus", deviceSupportCondition);
        map2.put("jio", deviceSupportCondition);
        DYNAMIC_COLOR_SUPPORTED_BRANDS = Collections.unmodifiableMap(map2);
    }

    private DynamicColors() {
    }

    @Deprecated
    public static void applyIfAvailable(@NonNull Activity activity) {
        applyToActivityIfAvailable(activity);
    }

    public static void applyToActivitiesIfAvailable(@NonNull Application application) {
        applyToActivitiesIfAvailable(application, new DynamicColorsOptions.Builder().build());
    }

    public static void applyToActivityIfAvailable(@NonNull Activity activity) {
        applyToActivityIfAvailable(activity, new DynamicColorsOptions.Builder().build());
    }

    private static int getDefaultThemeOverlay(@NonNull Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(DYNAMIC_COLOR_THEME_OVERLAY_ATTRIBUTE);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        return resourceId;
    }

    @ChecksSdkIntAtLeast(api = 31)
    @SuppressLint({"DefaultLocale"})
    public static boolean isDynamicColorAvailable() {
        if (Build.VERSION.SDK_INT < 31) {
            return false;
        }
        if (BuildCompat.isAtLeastT()) {
            return true;
        }
        DeviceSupportCondition deviceSupportCondition = DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS.get(Build.MANUFACTURER.toLowerCase());
        if (deviceSupportCondition == null) {
            deviceSupportCondition = DYNAMIC_COLOR_SUPPORTED_BRANDS.get(Build.BRAND.toLowerCase());
        }
        return deviceSupportCondition != null && deviceSupportCondition.isSupported();
    }

    @NonNull
    public static Context wrapContextIfAvailable(@NonNull Context context) {
        return wrapContextIfAvailable(context, 0);
    }

    @Deprecated
    public static void applyIfAvailable(@NonNull Activity activity, @StyleRes int i) {
        applyToActivityIfAvailable(activity, new DynamicColorsOptions.Builder().setThemeOverlay(i).build());
    }

    @Deprecated
    public static void applyToActivitiesIfAvailable(@NonNull Application application, @StyleRes int i) {
        applyToActivitiesIfAvailable(application, new DynamicColorsOptions.Builder().setThemeOverlay(i).build());
    }

    public static void applyToActivityIfAvailable(@NonNull Activity activity, @NonNull DynamicColorsOptions dynamicColorsOptions) {
        if (isDynamicColorAvailable()) {
            int defaultThemeOverlay = 0;
            if (dynamicColorsOptions.getContentBasedSeedColor() == null) {
                defaultThemeOverlay = dynamicColorsOptions.getThemeOverlay() == 0 ? getDefaultThemeOverlay(activity) : dynamicColorsOptions.getThemeOverlay();
            }
            if (dynamicColorsOptions.getPrecondition().shouldApplyDynamicColors(activity, defaultThemeOverlay)) {
                if (dynamicColorsOptions.getContentBasedSeedColor() != null) {
                    Scheme schemeLightContent = MaterialAttributes.resolveBoolean(activity, R.attr.isLightTheme, true) ? Scheme.lightContent(dynamicColorsOptions.getContentBasedSeedColor().intValue()) : Scheme.darkContent(dynamicColorsOptions.getContentBasedSeedColor().intValue());
                    ColorResourcesOverride cc = ColorResourcesOverride.CC.getInstance();
                    if (cc == null) {
                        return;
                    }
                    HashMap map = new HashMap();
                    map.put(Integer.valueOf(R.color.material_personalized_color_primary), Integer.valueOf(schemeLightContent.getPrimary()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_on_primary), Integer.valueOf(schemeLightContent.getOnPrimary()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_primary_inverse), Integer.valueOf(schemeLightContent.getInversePrimary()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_primary_container), Integer.valueOf(schemeLightContent.getPrimaryContainer()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_on_primary_container), Integer.valueOf(schemeLightContent.getOnPrimaryContainer()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_secondary), Integer.valueOf(schemeLightContent.getSecondary()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_on_secondary), Integer.valueOf(schemeLightContent.getOnSecondary()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_secondary_container), Integer.valueOf(schemeLightContent.getSecondaryContainer()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_on_secondary_container), Integer.valueOf(schemeLightContent.getOnSecondaryContainer()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_tertiary), Integer.valueOf(schemeLightContent.getTertiary()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_on_tertiary), Integer.valueOf(schemeLightContent.getOnTertiary()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_tertiary_container), Integer.valueOf(schemeLightContent.getTertiaryContainer()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_on_tertiary_container), Integer.valueOf(schemeLightContent.getOnTertiaryContainer()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_background), Integer.valueOf(schemeLightContent.getBackground()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_on_background), Integer.valueOf(schemeLightContent.getOnBackground()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_surface), Integer.valueOf(schemeLightContent.getSurface()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_on_surface), Integer.valueOf(schemeLightContent.getOnSurface()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_surface_variant), Integer.valueOf(schemeLightContent.getSurfaceVariant()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_on_surface_variant), Integer.valueOf(schemeLightContent.getOnSurfaceVariant()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_surface_inverse), Integer.valueOf(schemeLightContent.getInverseSurface()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_on_surface_inverse), Integer.valueOf(schemeLightContent.getInverseOnSurface()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_surface_outline), Integer.valueOf(schemeLightContent.getOutline()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_error), Integer.valueOf(schemeLightContent.getError()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_on_error), Integer.valueOf(schemeLightContent.getOnError()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_error_container), Integer.valueOf(schemeLightContent.getErrorContainer()));
                    map.put(Integer.valueOf(R.color.material_personalized_color_on_error_container), Integer.valueOf(schemeLightContent.getOnErrorContainer()));
                    if (!cc.applyIfPossible(activity, map)) {
                        return;
                    }
                } else {
                    ThemeUtils.applyThemeOverlay(activity, defaultThemeOverlay);
                }
                dynamicColorsOptions.getOnAppliedCallback().onApplied(activity);
            }
        }
    }

    @NonNull
    public static Context wrapContextIfAvailable(@NonNull Context context, @StyleRes int i) {
        if (!isDynamicColorAvailable()) {
            return context;
        }
        if (i == 0) {
            i = getDefaultThemeOverlay(context);
        }
        return i == 0 ? context : new ContextThemeWrapper(context, i);
    }

    @Deprecated
    public static void applyIfAvailable(@NonNull Activity activity, @NonNull Precondition precondition) {
        applyToActivityIfAvailable(activity, new DynamicColorsOptions.Builder().setPrecondition(precondition).build());
    }

    @Deprecated
    public static void applyToActivitiesIfAvailable(@NonNull Application application, @NonNull Precondition precondition) {
        applyToActivitiesIfAvailable(application, new DynamicColorsOptions.Builder().setPrecondition(precondition).build());
    }

    @Deprecated
    public static void applyToActivitiesIfAvailable(@NonNull Application application, @StyleRes int i, @NonNull Precondition precondition) {
        applyToActivitiesIfAvailable(application, new DynamicColorsOptions.Builder().setThemeOverlay(i).setPrecondition(precondition).build());
    }

    public static void applyToActivitiesIfAvailable(@NonNull Application application, @NonNull DynamicColorsOptions dynamicColorsOptions) {
        application.registerActivityLifecycleCallbacks(new DynamicColorsActivityLifecycleCallbacks(dynamicColorsOptions));
    }
}
