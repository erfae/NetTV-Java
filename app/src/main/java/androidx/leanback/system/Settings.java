package androidx.leanback.system;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import androidx.annotation.RestrictTo;
import androidx.leanback.widget.ShadowOverlayContainer;

/* JADX INFO: loaded from: classes.dex */
public class Settings {
    private static final String ACTION_PARTNER_CUSTOMIZATION = "android.support.v17.leanback.action.PARTNER_CUSTOMIZATION";
    private static final boolean DEBUG = false;
    public static final String OUTLINE_CLIPPING_DISABLED = "OUTLINE_CLIPPING_DISABLED";
    public static final String PREFER_STATIC_SHADOWS = "PREFER_STATIC_SHADOWS";
    private static final String TAG = "Settings";
    private static Settings sInstance;
    private boolean mOutlineClippingDisabled;
    private boolean mPreferStaticShadows;

    public static class Customizations {
        public String mPackageName;
        public Resources mResources;

        public Customizations(Resources resources, String str) {
            this.mResources = resources;
            this.mPackageName = str;
        }

        public boolean getBoolean(String str, boolean z) {
            int identifier = this.mResources.getIdentifier(str, "bool", this.mPackageName);
            return identifier > 0 ? this.mResources.getBoolean(identifier) : z;
        }
    }

    private Settings(Context context) {
        generateSetting(getCustomizations(context));
    }

    private void generateSetting(Customizations customizations) {
        if (ShadowOverlayContainer.supportsDynamicShadow()) {
            this.mPreferStaticShadows = false;
            if (customizations != null) {
                this.mPreferStaticShadows = customizations.getBoolean("leanback_prefer_static_shadows", false);
            }
        } else {
            this.mPreferStaticShadows = true;
        }
        this.mOutlineClippingDisabled = false;
        if (customizations != null) {
            this.mOutlineClippingDisabled = customizations.getBoolean("leanback_outline_clipping_disabled", false);
        }
    }

    private Customizations getCustomizations(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Resources resourcesForApplication = null;
        String str = null;
        for (ResolveInfo resolveInfo : packageManager.queryBroadcastReceivers(new Intent(ACTION_PARTNER_CUSTOMIZATION), 0)) {
            String str2 = resolveInfo.activityInfo.packageName;
            if (str2 != null && isSystemApp(resolveInfo)) {
                try {
                    resourcesForApplication = packageManager.getResourcesForApplication(str2);
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
            if (resourcesForApplication != null) {
                str = str2;
                break;
            }
            str = str2;
        }
        if (resourcesForApplication == null) {
            return null;
        }
        return new Customizations(resourcesForApplication, str);
    }

    public static Settings getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Settings(context);
        }
        return sInstance;
    }

    private static boolean isSystemApp(ResolveInfo resolveInfo) {
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        return (activityInfo == null || (activityInfo.applicationInfo.flags & 1) == 0) ? false : true;
    }

    public boolean getBoolean(String str) {
        return getOrSetBoolean(str, false, false);
    }

    public final boolean getOrSetBoolean(String str, boolean z, boolean z2) {
        if (str.compareTo(PREFER_STATIC_SHADOWS) == 0) {
            if (!z) {
                return this.mPreferStaticShadows;
            }
            this.mPreferStaticShadows = z2;
            return z2;
        }
        if (str.compareTo(OUTLINE_CLIPPING_DISABLED) != 0) {
            throw new IllegalArgumentException("Invalid key");
        }
        if (!z) {
            return this.mOutlineClippingDisabled;
        }
        this.mOutlineClippingDisabled = z2;
        return z2;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isOutlineClippingDisabled() {
        return this.mOutlineClippingDisabled;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean preferStaticShadows() {
        return this.mPreferStaticShadows;
    }

    public void setBoolean(String str, boolean z) {
        getOrSetBoolean(str, true, z);
    }
}
