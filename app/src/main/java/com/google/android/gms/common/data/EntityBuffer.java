package com.google.android.gms.common.data;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
@KeepForSdk
public abstract class EntityBuffer<T> extends AbstractDataBuffer<T> {
    private boolean zaa;
    private ArrayList<Integer> zab;

    private final void zab() {
        synchronized (this) {
            if (!this.zaa) {
                int count = ((DataHolder) Preconditions.checkNotNull(this.mDataHolder)).getCount();
                ArrayList<Integer> arrayList = new ArrayList<>();
                this.zab = arrayList;
                if (count > 0) {
                    arrayList.add(0);
                    String primaryDataMarkerColumn = getPrimaryDataMarkerColumn();
                    String string = this.mDataHolder.getString(primaryDataMarkerColumn, 0, this.mDataHolder.getWindowIndex(0));
                    for (int i = 1; i < count; i++) {
                        int windowIndex = this.mDataHolder.getWindowIndex(i);
                        String string2 = this.mDataHolder.getString(primaryDataMarkerColumn, i, windowIndex);
                        if (string2 == null) {
                            StringBuilder sb = new StringBuilder(String.valueOf(primaryDataMarkerColumn).length() + 78);
                            sb.append("Missing value for markerColumn: ");
                            sb.append(primaryDataMarkerColumn);
                            sb.append(", at row: ");
                            sb.append(i);
                            sb.append(", for window: ");
                            sb.append(windowIndex);
                            throw new NullPointerException(sb.toString());
                        }
                        if (!string2.equals(string)) {
                            this.zab.add(Integer.valueOf(i));
                            string = string2;
                        }
                    }
                }
                this.zaa = true;
            }
        }
    }

    @Override // com.google.android.gms.common.data.AbstractDataBuffer, com.google.android.gms.common.data.DataBuffer
    @NonNull
    @KeepForSdk
    public final T get(int i) {
        int iIntValue;
        int iIntValue2;
        zab();
        zaa(i);
        if (i >= 0 && i != this.zab.size()) {
            if (i == this.zab.size() - 1) {
                iIntValue = ((DataHolder) Preconditions.checkNotNull(this.mDataHolder)).getCount();
                iIntValue2 = this.zab.get(i).intValue();
            } else {
                iIntValue = this.zab.get(i + 1).intValue();
                iIntValue2 = this.zab.get(i).intValue();
            }
            if (iIntValue - iIntValue2 == 1) {
                ((DataHolder) Preconditions.checkNotNull(this.mDataHolder)).getWindowIndex(zaa(i));
            }
        }
        return (T) getEntry();
    }

    @Override // com.google.android.gms.common.data.AbstractDataBuffer, com.google.android.gms.common.data.DataBuffer
    @KeepForSdk
    public int getCount() {
        zab();
        return this.zab.size();
    }

    @NonNull
    @KeepForSdk
    public abstract Object getEntry();

    @NonNull
    @KeepForSdk
    public abstract String getPrimaryDataMarkerColumn();

    public final int zaa(int i) {
        if (i >= 0 && i < this.zab.size()) {
            return this.zab.get(i).intValue();
        }
        StringBuilder sb = new StringBuilder(53);
        sb.append("Position ");
        sb.append(i);
        sb.append(" is out of bounds for this buffer");
        throw new IllegalArgumentException(sb.toString());
    }
}
