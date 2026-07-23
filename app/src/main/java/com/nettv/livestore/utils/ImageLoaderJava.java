package com.nettv.livestore.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.card.MaterialCardViewHelper;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes2.dex */
public class ImageLoaderJava {
    public static void imageLoadUrlWithVodHolder(Context context, @NonNull final ImageView imageView, @NonNull String str, int i, @NonNull final ImageView imageView2) {
        try {
            GlideApp.with(context).load(str.trim()).override(MaterialCardViewHelper.DEFAULT_FADE_ANIM_DURATION, 450).listener(new RequestListener<Drawable>() { // from class: com.nettv.livestore.utils.ImageLoaderJava.2
                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    imageView2.setVisibility(0);
                    imageView.setVisibility(0);
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    imageView.setVisibility(0);
                    imageView2.setVisibility(8);
                    return false;
                }
            }).into(imageView);
        } catch (Exception unused) {
        }
    }

    public static void imageLoaderUrlWitPlaceHolder(Context context, @NotNull ImageView imageView, @org.jetbrains.annotations.Nullable String str, int i) {
        GlideApp.with(context).load(str.trim()).placeholder(i).override(Integer.MIN_VALUE, Integer.MIN_VALUE).listener(new RequestListener<Drawable>() { // from class: com.nettv.livestore.utils.ImageLoaderJava.1
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                return false;
            }
        }).into(imageView);
    }
}
