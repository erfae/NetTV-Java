package com.google.android.gms.internal.base;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
public final class zai extends Drawable implements Drawable.Callback {
    private int zaa;
    private long zab;
    private int zac;
    private int zad;
    private int zae;
    private int zaf;
    private boolean zag;
    private boolean zah;
    private zah zai;
    private Drawable zaj;
    private Drawable zak;
    private boolean zal;
    private boolean zam;
    private boolean zan;
    private int zao;

    public zai(@Nullable Drawable drawable, @Nullable Drawable drawable2) {
        this(null);
        drawable = drawable == null ? zag.zaa : drawable;
        this.zaj = drawable;
        drawable.setCallback(this);
        zah zahVar = this.zai;
        zahVar.zab = drawable.getChangingConfigurations() | zahVar.zab;
        drawable2 = drawable2 == null ? zag.zaa : drawable2;
        this.zak = drawable2;
        drawable2.setCallback(this);
        zah zahVar2 = this.zai;
        zahVar2.zab = drawable2.getChangingConfigurations() | zahVar2.zab;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x005a  */
    /* JADX WARN: Code duplicated, block: B:36:? A[RETURN, SYNTHETIC] */
    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        int i;
        int i2 = this.zaa;
        int i3 = 0;
        boolean z = true;
        if (i2 == 1) {
            this.zab = SystemClock.uptimeMillis();
            this.zaa = 2;
            z = false;
        } else if (i2 == 2 && this.zab >= 0) {
            float fUptimeMillis = (SystemClock.uptimeMillis() - this.zab) / this.zae;
            z = fUptimeMillis >= 1.0f;
            if (z) {
                this.zaa = 0;
            }
            this.zaf = (int) ((this.zac * Math.min(fUptimeMillis, 1.0f)) + 0.0f);
        }
        int i4 = this.zaf;
        boolean z2 = this.zag;
        Drawable drawable = this.zaj;
        Drawable drawable2 = this.zak;
        if (!z) {
            if (z2) {
                drawable.setAlpha(this.zad - i4);
            }
            drawable.draw(canvas);
            if (z2) {
                drawable.setAlpha(this.zad);
            }
            if (i4 > 0) {
                drawable2.setAlpha(i4);
                drawable2.draw(canvas);
                drawable2.setAlpha(this.zad);
            }
            invalidateSelf();
            return;
        }
        if (z2) {
            if (i4 == 0) {
            }
            i = this.zad;
            if (i4 == i) {
                drawable2.setAlpha(i);
                drawable2.draw(canvas);
            }
        }
        i3 = i4;
        drawable.draw(canvas);
        i4 = i3;
        i = this.zad;
        if (i4 == i) {
            drawable2.setAlpha(i);
            drawable2.draw(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getChangingConfigurations() {
        int changingConfigurations = super.getChangingConfigurations();
        zah zahVar = this.zai;
        return changingConfigurations | zahVar.zaa | zahVar.zab;
    }

    @Override // android.graphics.drawable.Drawable
    @Nullable
    public final Drawable.ConstantState getConstantState() {
        if (!zac()) {
            return null;
        }
        this.zai.zaa = getChangingConfigurations();
        return this.zai;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return Math.max(this.zaj.getIntrinsicHeight(), this.zak.getIntrinsicHeight());
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return Math.max(this.zaj.getIntrinsicWidth(), this.zak.getIntrinsicWidth());
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        if (!this.zan) {
            this.zao = Drawable.resolveOpacity(this.zaj.getOpacity(), this.zak.getOpacity());
            this.zan = true;
        }
        return this.zao;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable mutate() {
        if (!this.zah && super.mutate() == this) {
            if (!zac()) {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
            this.zaj.mutate();
            this.zak.mutate();
            this.zah = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        this.zaj.setBounds(rect);
        this.zak.setBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        if (this.zaf == this.zad) {
            this.zaf = i;
        }
        this.zad = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.zaj.setColorFilter(colorFilter);
        this.zak.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public final Drawable zaa() {
        return this.zak;
    }

    public final void zab(int i) {
        this.zac = this.zad;
        this.zaf = 0;
        this.zae = ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
        this.zaa = 1;
        invalidateSelf();
    }

    public final boolean zac() {
        if (!this.zal) {
            boolean z = false;
            if (this.zaj.getConstantState() != null && this.zak.getConstantState() != null) {
                z = true;
            }
            this.zam = z;
            this.zal = true;
        }
        return this.zam;
    }

    public zai(@Nullable zah zahVar) {
        this.zaa = 0;
        this.zad = 255;
        this.zaf = 0;
        this.zag = true;
        this.zai = new zah(zahVar);
    }
}
