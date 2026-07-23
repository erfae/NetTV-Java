package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.Nullable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
public abstract class zag {
    public final zad zaa;
    public int zab;

    public zag(Uri uri, int i) {
        this.zab = 0;
        this.zaa = new zad(uri);
        this.zab = i;
    }

    public abstract void zaa(@Nullable Drawable drawable, boolean z, boolean z2, boolean z3);

    public final void zab(Context context, boolean z) {
        int i = this.zab;
        zaa(i != 0 ? context.getResources().getDrawable(i) : null, z, false, false);
    }
}
