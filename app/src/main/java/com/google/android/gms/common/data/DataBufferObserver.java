package com.google.android.gms.common.data;

import androidx.annotation.NonNull;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
public interface DataBufferObserver {

    /* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
    public interface Observable {
        void addObserver(@NonNull DataBufferObserver dataBufferObserver);

        void removeObserver(@NonNull DataBufferObserver dataBufferObserver);
    }

    void onDataChanged();

    void onDataRangeChanged(int i, int i2);

    void onDataRangeInserted(int i, int i2);

    void onDataRangeMoved(int i, int i2, int i3);

    void onDataRangeRemoved(int i, int i2);
}
