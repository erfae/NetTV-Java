package com.google.android.material.internal;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroupOverlay;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi(18)
class ViewGroupOverlayApi18 implements ViewGroupOverlayImpl {
    private final ViewGroupOverlay viewGroupOverlay;

    @Override // com.google.android.material.internal.ViewOverlayImpl
    public void add(@NonNull Drawable drawable) {
        this.viewGroupOverlay.add(drawable);
    }

    @Override // com.google.android.material.internal.ViewOverlayImpl
    public void remove(@NonNull Drawable drawable) {
        this.viewGroupOverlay.remove(drawable);
    }

    @Override // com.google.android.material.internal.ViewGroupOverlayImpl
    public void add(@NonNull View view) {
        this.viewGroupOverlay.add(view);
    }

    @Override // com.google.android.material.internal.ViewGroupOverlayImpl
    public void remove(@NonNull View view) {
        this.viewGroupOverlay.remove(view);
    }
}
