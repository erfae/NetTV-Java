package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.bumptech.glide.load.Key;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
final class DataCacheKey implements Key {
    private final Key signature;
    private final Key sourceKey;

    public DataCacheKey(Key key, Key key2) {
        this.sourceKey = key;
        this.signature = key2;
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (!(obj instanceof DataCacheKey)) {
            return false;
        }
        DataCacheKey dataCacheKey = (DataCacheKey) obj;
        return this.sourceKey.equals(dataCacheKey.sourceKey) && this.signature.equals(dataCacheKey.signature);
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return this.signature.hashCode() + (this.sourceKey.hashCode() * 31);
    }

    public String toString() {
        StringBuilder sbM = Insets$$ExternalSyntheticOutline0.m("DataCacheKey{sourceKey=");
        sbM.append(this.sourceKey);
        sbM.append(", signature=");
        sbM.append(this.signature);
        sbM.append('}');
        return sbM.toString();
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.sourceKey.updateDiskCacheKey(messageDigest);
        this.signature.updateDiskCacheKey(messageDigest);
    }
}
