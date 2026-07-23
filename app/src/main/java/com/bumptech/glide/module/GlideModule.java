package com.bumptech.glide.module;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public interface GlideModule extends RegistersComponents, AppliesOptions {
    /* synthetic */ void applyOptions(@NonNull Context context, @NonNull GlideBuilder glideBuilder);

    @Override // com.bumptech.glide.module.RegistersComponents
    /* synthetic */ void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry);
}
