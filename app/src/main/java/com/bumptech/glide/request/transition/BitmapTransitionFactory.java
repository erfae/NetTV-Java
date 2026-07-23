package com.bumptech.glide.request.transition;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes.dex */
public class BitmapTransitionFactory extends BitmapContainerTransitionFactory<Bitmap> {
    public BitmapTransitionFactory(@NonNull TransitionFactory<Drawable> transitionFactory) {
        super(transitionFactory);
    }

    @Override // com.bumptech.glide.request.transition.BitmapContainerTransitionFactory
    @NonNull
    public final Bitmap getBitmap(@NonNull Bitmap bitmap) {
        return bitmap;
    }
}
