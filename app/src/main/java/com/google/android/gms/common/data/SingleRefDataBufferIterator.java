package com.google.android.gms.common.data;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
@KeepForSdk
public class SingleRefDataBufferIterator<T> extends DataBufferIterator<T> {
    private T zac;

    public SingleRefDataBufferIterator(@NonNull DataBuffer<T> dataBuffer) {
        super(dataBuffer);
    }

    @Override // com.google.android.gms.common.data.DataBufferIterator, java.util.Iterator
    @NonNull
    public final T next() {
        if (!hasNext()) {
            throw new NoSuchElementException(Insets$$ExternalSyntheticOutline0.m(46, "Cannot advance the iterator beyond ", this.zab));
        }
        int i = this.zab + 1;
        this.zab = i;
        if (i == 0) {
            T t = (T) Preconditions.checkNotNull(this.zaa.get(0));
            this.zac = t;
            if (!(t instanceof DataBufferRef)) {
                String strValueOf = String.valueOf(t.getClass());
                throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m(new StringBuilder(strValueOf.length() + 44), "DataBuffer reference of type ", strValueOf, " is not movable"));
            }
        } else {
            ((DataBufferRef) Preconditions.checkNotNull(this.zac)).zaa(this.zab);
        }
        return this.zac;
    }
}
