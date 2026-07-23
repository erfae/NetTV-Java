package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import androidx.annotation.VisibleForTesting;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.bumptech.glide.util.Util;

/* JADX INFO: loaded from: classes.dex */
class AttributeStrategy implements LruPoolStrategy {
    private final KeyPool keyPool = new KeyPool();
    private final GroupedLinkedMap<Key, Bitmap> groupedMap = new GroupedLinkedMap<>();

    @VisibleForTesting
    public static class Key implements Poolable {
        private Bitmap.Config config;
        private int height;
        private final KeyPool pool;
        private int width;

        public Key(KeyPool keyPool) {
            this.pool = keyPool;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Key)) {
                return false;
            }
            Key key = (Key) obj;
            return this.width == key.width && this.height == key.height && this.config == key.config;
        }

        public int hashCode() {
            int i = ((this.width * 31) + this.height) * 31;
            Bitmap.Config config = this.config;
            return i + (config != null ? config.hashCode() : 0);
        }

        public void init(int i, int i2, Bitmap.Config config) {
            this.width = i;
            this.height = i2;
            this.config = config;
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.Poolable
        public void offer() {
            this.pool.offer(this);
        }

        public String toString() {
            return AttributeStrategy.getBitmapString(this.width, this.height, this.config);
        }
    }

    @VisibleForTesting
    public static class KeyPool extends BaseKeyPool<Key> {
        @Override // com.bumptech.glide.load.engine.bitmap_recycle.BaseKeyPool
        public final Poolable create() {
            return new Key(this);
        }
    }

    public static String getBitmapString(int i, int i2, Bitmap.Config config) {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("[", i, "x", i2, "], ");
        sbM.append(config);
        return sbM.toString();
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public Bitmap get(int i, int i2, Bitmap.Config config) {
        Key key = this.keyPool.get();
        key.init(i, i2, config);
        return this.groupedMap.get(key);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public int getSize(Bitmap bitmap) {
        return Util.getBitmapByteSize(bitmap);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public String logBitmap(Bitmap bitmap) {
        return getBitmapString(bitmap);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public void put(Bitmap bitmap) {
        KeyPool keyPool = this.keyPool;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap.Config config = bitmap.getConfig();
        Key key = keyPool.get();
        key.init(width, height, config);
        this.groupedMap.put(key, bitmap);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public Bitmap removeLast() {
        return this.groupedMap.removeLast();
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("AttributeStrategy:\n  ");
        sbM.append(this.groupedMap);
        return sbM.toString();
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public String logBitmap(int i, int i2, Bitmap.Config config) {
        return getBitmapString(i, i2, config);
    }

    private static String getBitmapString(Bitmap bitmap) {
        return getBitmapString(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
    }
}
