package com.bumptech.glide.request.target;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
public class BitmapThumbnailImageViewTarget extends ThumbnailImageViewTarget<Bitmap> {
    public BitmapThumbnailImageViewTarget(ImageView imageView) {
        super(imageView);
    }

    @Override // com.bumptech.glide.request.target.ThumbnailImageViewTarget
    public final Drawable getDrawable(Bitmap bitmap) {
        return new BitmapDrawable(((ImageView) this.view).getResources(), bitmap);
    }

    @Deprecated
    public BitmapThumbnailImageViewTarget(ImageView imageView, boolean z) {
        super(imageView, z);
    }
}
