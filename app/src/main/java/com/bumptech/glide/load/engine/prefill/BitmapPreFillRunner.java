package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.util.Util;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
final class BitmapPreFillRunner implements Runnable {
    private static final Clock DEFAULT_CLOCK = new Clock();
    public static final long MAX_BACKOFF_MS = TimeUnit.SECONDS.toMillis(1);
    private final BitmapPool bitmapPool;
    private final Clock clock;
    private long currentDelay;
    private final Handler handler;
    private boolean isCancelled;
    private final MemoryCache memoryCache;
    private final Set<PreFillType> seenTypes;
    private final PreFillQueue toPrefill;

    @VisibleForTesting
    public static class Clock {
    }

    public static final class UniqueKey implements Key {
        @Override // com.bumptech.glide.load.Key
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            throw new UnsupportedOperationException();
        }
    }

    public BitmapPreFillRunner(BitmapPool bitmapPool, MemoryCache memoryCache, PreFillQueue preFillQueue) {
        Clock clock = DEFAULT_CLOCK;
        Handler handler = new Handler(Looper.getMainLooper());
        this.seenTypes = new HashSet();
        this.currentDelay = 40L;
        this.bitmapPool = bitmapPool;
        this.memoryCache = memoryCache;
        this.toPrefill = preFillQueue;
        this.clock = clock;
        this.handler = handler;
    }

    private long getFreeMemoryCacheBytes() {
        return this.memoryCache.getMaxSize() - this.memoryCache.getCurrentSize();
    }

    private long getNextDelay() {
        long j = this.currentDelay;
        this.currentDelay = Math.min(4 * j, MAX_BACKOFF_MS);
        return j;
    }

    private boolean isGcDetected(long j) {
        Objects.requireNonNull(this.clock);
        return SystemClock.currentThreadTimeMillis() - j >= 32;
    }

    public void cancel() {
        this.isCancelled = true;
    }

    @Override // java.lang.Runnable
    public void run() {
        Bitmap bitmapCreateBitmap;
        Objects.requireNonNull(this.clock);
        long jCurrentThreadTimeMillis = SystemClock.currentThreadTimeMillis();
        while (!this.toPrefill.isEmpty() && !isGcDetected(jCurrentThreadTimeMillis)) {
            PreFillType preFillTypeRemove = this.toPrefill.remove();
            if (this.seenTypes.contains(preFillTypeRemove)) {
                bitmapCreateBitmap = Bitmap.createBitmap(preFillTypeRemove.getWidth(), preFillTypeRemove.getHeight(), preFillTypeRemove.getConfig());
            } else {
                this.seenTypes.add(preFillTypeRemove);
                bitmapCreateBitmap = this.bitmapPool.getDirty(preFillTypeRemove.getWidth(), preFillTypeRemove.getHeight(), preFillTypeRemove.getConfig());
            }
            int bitmapByteSize = Util.getBitmapByteSize(bitmapCreateBitmap);
            if (getFreeMemoryCacheBytes() >= bitmapByteSize) {
                this.memoryCache.put(new UniqueKey(), BitmapResource.obtain(bitmapCreateBitmap, this.bitmapPool));
            } else {
                this.bitmapPool.put(bitmapCreateBitmap);
            }
            if (Log.isLoggable("PreFillRunner", 3)) {
                StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("allocated [");
                sbM.append(preFillTypeRemove.getWidth());
                sbM.append("x");
                sbM.append(preFillTypeRemove.getHeight());
                sbM.append("] ");
                sbM.append(preFillTypeRemove.getConfig());
                sbM.append(" size: ");
                sbM.append(bitmapByteSize);
                Log.d("PreFillRunner", sbM.toString());
            }
        }
        if ((this.isCancelled || this.toPrefill.isEmpty()) ? false : true) {
            this.handler.postDelayed(this, getNextDelay());
        }
    }
}
