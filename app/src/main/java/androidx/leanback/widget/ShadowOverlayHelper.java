package androidx.leanback.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.R;

/* JADX INFO: loaded from: classes.dex */
public final class ShadowOverlayHelper {
    public static final int SHADOW_DYNAMIC = 3;
    public static final int SHADOW_NONE = 1;
    public static final int SHADOW_STATIC = 2;
    public float mFocusedZ;
    public boolean mNeedsOverlay;
    public boolean mNeedsRoundedCorner;
    public boolean mNeedsShadow;
    public boolean mNeedsWrapper;
    public int mRoundedCornerRadius;
    public int mShadowType = 1;
    public float mUnfocusedZ;

    public static final class Builder {
        private boolean keepForegroundDrawable;
        private boolean needsOverlay;
        private boolean needsRoundedCorner;
        private boolean needsShadow;
        private boolean preferZOrder = true;
        private Options options = Options.DEFAULT;

        public ShadowOverlayHelper build(Context context) {
            ShadowOverlayHelper shadowOverlayHelper = new ShadowOverlayHelper();
            shadowOverlayHelper.mNeedsOverlay = this.needsOverlay;
            boolean z = false;
            shadowOverlayHelper.mNeedsRoundedCorner = this.needsRoundedCorner && ShadowOverlayHelper.supportsRoundedCorner();
            shadowOverlayHelper.mNeedsShadow = this.needsShadow && ShadowOverlayHelper.supportsShadow();
            if (shadowOverlayHelper.mNeedsRoundedCorner) {
                Options options = this.options;
                if (options.getRoundedCornerRadius() == 0) {
                    shadowOverlayHelper.mRoundedCornerRadius = context.getResources().getDimensionPixelSize(R.dimen.lb_rounded_rect_corner_radius);
                } else {
                    shadowOverlayHelper.mRoundedCornerRadius = options.getRoundedCornerRadius();
                }
            }
            if (!shadowOverlayHelper.mNeedsShadow) {
                shadowOverlayHelper.mShadowType = 1;
                if ((!ShadowOverlayHelper.supportsForeground() || this.keepForegroundDrawable) && shadowOverlayHelper.mNeedsOverlay) {
                    z = true;
                }
                shadowOverlayHelper.mNeedsWrapper = z;
            } else if (this.preferZOrder && ShadowOverlayHelper.supportsDynamicShadow()) {
                shadowOverlayHelper.mShadowType = 3;
                Options options2 = this.options;
                if (options2.getDynamicShadowUnfocusedZ() < 0.0f) {
                    Resources resources = context.getResources();
                    shadowOverlayHelper.mFocusedZ = resources.getDimension(R.dimen.lb_material_shadow_focused_z);
                    shadowOverlayHelper.mUnfocusedZ = resources.getDimension(R.dimen.lb_material_shadow_normal_z);
                } else {
                    shadowOverlayHelper.mFocusedZ = options2.getDynamicShadowFocusedZ();
                    shadowOverlayHelper.mUnfocusedZ = options2.getDynamicShadowUnfocusedZ();
                }
                if ((!ShadowOverlayHelper.supportsForeground() || this.keepForegroundDrawable) && shadowOverlayHelper.mNeedsOverlay) {
                    z = true;
                }
                shadowOverlayHelper.mNeedsWrapper = z;
            } else {
                shadowOverlayHelper.mShadowType = 2;
                shadowOverlayHelper.mNeedsWrapper = true;
            }
            return shadowOverlayHelper;
        }

        public Builder keepForegroundDrawable(boolean z) {
            this.keepForegroundDrawable = z;
            return this;
        }

        public Builder needsOverlay(boolean z) {
            this.needsOverlay = z;
            return this;
        }

        public Builder needsRoundedCorner(boolean z) {
            this.needsRoundedCorner = z;
            return this;
        }

        public Builder needsShadow(boolean z) {
            this.needsShadow = z;
            return this;
        }

        public Builder options(Options options) {
            this.options = options;
            return this;
        }

        public Builder preferZOrder(boolean z) {
            this.preferZOrder = z;
            return this;
        }
    }

    public static final class Options {
        public static final Options DEFAULT = new Options();
        private int roundedCornerRadius = 0;
        private float dynamicShadowUnfocusedZ = -1.0f;
        private float dynamicShadowFocusedZ = -1.0f;

        public Options dynamicShadowZ(float f, float f2) {
            this.dynamicShadowUnfocusedZ = f;
            this.dynamicShadowFocusedZ = f2;
            return this;
        }

        public final float getDynamicShadowFocusedZ() {
            return this.dynamicShadowFocusedZ;
        }

        public final float getDynamicShadowUnfocusedZ() {
            return this.dynamicShadowUnfocusedZ;
        }

        public final int getRoundedCornerRadius() {
            return this.roundedCornerRadius;
        }

        public Options roundedCornerRadius(int i) {
            this.roundedCornerRadius = i;
            return this;
        }
    }

    public static void setNoneWrapperOverlayColor(View view, int i) {
        Drawable foreground = ForegroundHelper.getForeground(view);
        if (foreground instanceof ColorDrawable) {
            ((ColorDrawable) foreground).setColor(i);
        } else {
            ForegroundHelper.setForeground(view, new ColorDrawable(i));
        }
    }

    public static void setNoneWrapperShadowFocusLevel(View view, float f) {
        setShadowFocusLevel(view.getTag(R.id.lb_shadow_impl), 3, f);
    }

    public static void setShadowFocusLevel(Object obj, int i, float f) {
        if (obj != null) {
            if (f < 0.0f) {
                f = 0.0f;
            } else if (f > 1.0f) {
                f = 1.0f;
            }
            if (i != 2) {
                if (i != 3) {
                    return;
                }
                ShadowHelperApi21.setShadowFocusLevel(obj, f);
            } else {
                StaticShadowHelper.ShadowImpl shadowImpl = (StaticShadowHelper.ShadowImpl) obj;
                shadowImpl.mNormalShadow.setAlpha(1.0f - f);
                shadowImpl.mFocusShadow.setAlpha(f);
            }
        }
    }

    public static boolean supportsDynamicShadow() {
        return true;
    }

    public static boolean supportsForeground() {
        return Build.VERSION.SDK_INT >= 23;
    }

    public static boolean supportsRoundedCorner() {
        return true;
    }

    public static boolean supportsShadow() {
        return true;
    }

    public ShadowOverlayContainer createShadowOverlayContainer(Context context) {
        if (needsWrapper()) {
            return new ShadowOverlayContainer(context, this.mShadowType, this.mNeedsOverlay, this.mUnfocusedZ, this.mFocusedZ, this.mRoundedCornerRadius);
        }
        throw new IllegalArgumentException();
    }

    public int getShadowType() {
        return this.mShadowType;
    }

    public boolean needsOverlay() {
        return this.mNeedsOverlay;
    }

    public boolean needsRoundedCorner() {
        return this.mNeedsRoundedCorner;
    }

    public boolean needsWrapper() {
        return this.mNeedsWrapper;
    }

    public void onViewCreated(View view) {
        if (needsWrapper()) {
            return;
        }
        if (!this.mNeedsShadow) {
            if (this.mNeedsRoundedCorner) {
                RoundedRectHelperApi21.setClipToRoundedOutline(view, true, this.mRoundedCornerRadius);
            }
        } else if (this.mShadowType == 3) {
            view.setTag(R.id.lb_shadow_impl, ShadowHelperApi21.addDynamicShadow(view, this.mUnfocusedZ, this.mFocusedZ, this.mRoundedCornerRadius));
        } else if (this.mNeedsRoundedCorner) {
            RoundedRectHelperApi21.setClipToRoundedOutline(view, true, this.mRoundedCornerRadius);
        }
    }

    public void prepareParentForShadow(ViewGroup viewGroup) {
        if (this.mShadowType == 2) {
            viewGroup.setLayoutMode(1);
        }
    }

    public void setOverlayColor(View view, int i) {
        if (needsWrapper()) {
            ((ShadowOverlayContainer) view).setOverlayColor(i);
        } else {
            setNoneWrapperOverlayColor(view, i);
        }
    }

    public void setShadowFocusLevel(View view, float f) {
        if (needsWrapper()) {
            ((ShadowOverlayContainer) view).setShadowFocusLevel(f);
        } else {
            setShadowFocusLevel(view.getTag(R.id.lb_shadow_impl), 3, f);
        }
    }
}
