package com.google.android.material.internal;

import android.view.View;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes.dex */
class ViewGroupOverlayApi14 extends ViewOverlayApi14 implements ViewGroupOverlayImpl {
    @Override // com.google.android.material.internal.ViewGroupOverlayImpl
    public void add(@NonNull View view) {
        this.overlayViewGroup.add(view);
    }

    @Override // com.google.android.material.internal.ViewGroupOverlayImpl
    public void remove(@NonNull View view) {
        this.overlayViewGroup.remove(view);
    }
}
