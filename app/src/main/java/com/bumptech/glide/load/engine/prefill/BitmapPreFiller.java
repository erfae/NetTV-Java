package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.util.Util;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class BitmapPreFiller {
    private final BitmapPool bitmapPool;
    private BitmapPreFillRunner current;
    private final DecodeFormat defaultFormat;
    private final MemoryCache memoryCache;

    public BitmapPreFiller(MemoryCache memoryCache, BitmapPool bitmapPool, DecodeFormat decodeFormat) {
        this.memoryCache = memoryCache;
        this.bitmapPool = bitmapPool;
        this.defaultFormat = decodeFormat;
    }

    private static int getSizeInBytes(PreFillType preFillType) {
        return Util.getBitmapByteSize(preFillType.getWidth(), preFillType.getHeight(), preFillType.getConfig());
    }

    public void preFill(PreFillType.Builder... builderArr) {
        BitmapPreFillRunner bitmapPreFillRunner = this.current;
        if (bitmapPreFillRunner != null) {
            bitmapPreFillRunner.cancel();
        }
        int length = builderArr.length;
        PreFillType[] preFillTypeArr = new PreFillType[length];
        for (int i = 0; i < builderArr.length; i++) {
            PreFillType.Builder builder = builderArr[i];
            if (builder.getConfig() == null) {
                builder.setConfig(this.defaultFormat == DecodeFormat.PREFER_ARGB_8888 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            }
            preFillTypeArr[i] = builder.build();
        }
        long maxSize = this.bitmapPool.getMaxSize() + (this.memoryCache.getMaxSize() - this.memoryCache.getCurrentSize());
        int weight = 0;
        for (int i2 = 0; i2 < length; i2++) {
            weight += preFillTypeArr[i2].getWeight();
        }
        float f = maxSize / weight;
        HashMap map = new HashMap();
        for (int i3 = 0; i3 < length; i3++) {
            PreFillType preFillType = preFillTypeArr[i3];
            map.put(preFillType, Integer.valueOf(Math.round(preFillType.getWeight() * f) / getSizeInBytes(preFillType)));
        }
        BitmapPreFillRunner bitmapPreFillRunner2 = new BitmapPreFillRunner(this.bitmapPool, this.memoryCache, new PreFillQueue(map));
        this.current = bitmapPreFillRunner2;
        Util.postOnUiThread(bitmapPreFillRunner2);
    }
}
