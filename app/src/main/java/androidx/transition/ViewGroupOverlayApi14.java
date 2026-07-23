package androidx.transition;

import android.view.View;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes.dex */
class ViewGroupOverlayApi14 extends ViewOverlayApi14 implements ViewGroupOverlayImpl {
    @Override // androidx.transition.ViewGroupOverlayImpl
    public void add(@NonNull View view) {
        this.mOverlayViewGroup.add(view);
    }

    @Override // androidx.transition.ViewGroupOverlayImpl
    public void remove(@NonNull View view) {
        this.mOverlayViewGroup.remove(view);
    }
}
