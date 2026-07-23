package com.bumptech.glide.load.engine.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.bumptech.glide.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public final class MemorySizeCalculator {
    private static final int LOW_MEMORY_BYTE_ARRAY_POOL_DIVISOR = 2;
    private static final String TAG = "MemorySizeCalculator";
    private final int arrayPoolSize;
    private final int bitmapPoolSize;
    private final Context context;
    private final int memoryCacheSize;

    public static final class Builder {
        public static final int BITMAP_POOL_TARGET_SCREENS;
        public ActivityManager activityManager;
        public float bitmapPoolScreens;
        public final Context context;
        public DisplayMetricsScreenDimensions screenDimensions;
        public float memoryCacheScreens = 2.0f;
        public float maxSizeMultiplier = 0.4f;
        public float lowMemoryMaxSizeMultiplier = 0.33f;
        public int arrayPoolSizeBytes = 4194304;

        static {
            BITMAP_POOL_TARGET_SCREENS = Build.VERSION.SDK_INT < 26 ? 4 : 1;
        }

        public Builder(Context context) {
            this.bitmapPoolScreens = BITMAP_POOL_TARGET_SCREENS;
            this.context = context;
            this.activityManager = (ActivityManager) context.getSystemService("activity");
            this.screenDimensions = new DisplayMetricsScreenDimensions(context.getResources().getDisplayMetrics());
            if (Build.VERSION.SDK_INT < 26 || !this.activityManager.isLowRamDevice()) {
                return;
            }
            this.bitmapPoolScreens = 0.0f;
        }

        public MemorySizeCalculator build() {
            return new MemorySizeCalculator(this);
        }

        public Builder setArrayPoolSize(int i) {
            this.arrayPoolSizeBytes = i;
            return this;
        }

        public Builder setBitmapPoolScreens(float f) {
            Preconditions.checkArgument(f >= 0.0f, "Bitmap pool screens must be greater than or equal to 0");
            this.bitmapPoolScreens = f;
            return this;
        }

        public Builder setLowMemoryMaxSizeMultiplier(float f) {
            Preconditions.checkArgument(f >= 0.0f && f <= 1.0f, "Low memory max size multiplier must be between 0 and 1");
            this.lowMemoryMaxSizeMultiplier = f;
            return this;
        }

        public Builder setMaxSizeMultiplier(float f) {
            Preconditions.checkArgument(f >= 0.0f && f <= 1.0f, "Size multiplier must be between 0 and 1");
            this.maxSizeMultiplier = f;
            return this;
        }

        public Builder setMemoryCacheScreens(float f) {
            Preconditions.checkArgument(f >= 0.0f, "Memory cache screens must be greater than or equal to 0");
            this.memoryCacheScreens = f;
            return this;
        }
    }

    public static final class DisplayMetricsScreenDimensions implements ScreenDimensions {
        private final DisplayMetrics displayMetrics;

        public DisplayMetricsScreenDimensions(DisplayMetrics displayMetrics) {
            this.displayMetrics = displayMetrics;
        }

        @Override // com.bumptech.glide.load.engine.cache.MemorySizeCalculator.ScreenDimensions
        public int getHeightPixels() {
            return this.displayMetrics.heightPixels;
        }

        @Override // com.bumptech.glide.load.engine.cache.MemorySizeCalculator.ScreenDimensions
        public int getWidthPixels() {
            return this.displayMetrics.widthPixels;
        }
    }

    public interface ScreenDimensions {
        int getHeightPixels();

        int getWidthPixels();
    }

    public MemorySizeCalculator(Builder builder) {
        this.context = builder.context;
        int i = builder.activityManager.isLowRamDevice() ? builder.arrayPoolSizeBytes / 2 : builder.arrayPoolSizeBytes;
        this.arrayPoolSize = i;
        int maxSize = getMaxSize(builder.activityManager, builder.maxSizeMultiplier, builder.lowMemoryMaxSizeMultiplier);
        float widthPixels = builder.screenDimensions.getWidthPixels() * builder.screenDimensions.getHeightPixels() * 4;
        int iRound = Math.round(builder.bitmapPoolScreens * widthPixels);
        int iRound2 = Math.round(widthPixels * builder.memoryCacheScreens);
        int i2 = maxSize - i;
        int i3 = iRound2 + iRound;
        if (i3 <= i2) {
            this.memoryCacheSize = iRound2;
            this.bitmapPoolSize = iRound;
        } else {
            float f = i2;
            float f2 = builder.bitmapPoolScreens;
            float f3 = builder.memoryCacheScreens;
            float f4 = f / (f2 + f3);
            this.memoryCacheSize = Math.round(f3 * f4);
            this.bitmapPoolSize = Math.round(f4 * builder.bitmapPoolScreens);
        }
        if (Log.isLoggable(TAG, 3)) {
            StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("Calculation complete, Calculated memory cache size: ");
            sbM.append(toMb(this.memoryCacheSize));
            sbM.append(", pool size: ");
            sbM.append(toMb(this.bitmapPoolSize));
            sbM.append(", byte array size: ");
            sbM.append(toMb(i));
            sbM.append(", memory class limited? ");
            sbM.append(i3 > maxSize);
            sbM.append(", max size: ");
            sbM.append(toMb(maxSize));
            sbM.append(", memoryClass: ");
            sbM.append(builder.activityManager.getMemoryClass());
            sbM.append(", isLowMemoryDevice: ");
            sbM.append(builder.activityManager.isLowRamDevice());
            Log.d(TAG, sbM.toString());
        }
    }

    private static int getMaxSize(ActivityManager activityManager, float f, float f2) {
        float memoryClass = activityManager.getMemoryClass() * 1024 * 1024;
        if (activityManager.isLowRamDevice()) {
            f = f2;
        }
        return Math.round(memoryClass * f);
    }

    private String toMb(int i) {
        return Formatter.formatFileSize(this.context, i);
    }

    public int getArrayPoolSizeInBytes() {
        return this.arrayPoolSize;
    }

    public int getBitmapPoolSize() {
        return this.bitmapPoolSize;
    }

    public int getMemoryCacheSize() {
        return this.memoryCacheSize;
    }
}
