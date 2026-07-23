package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import java.util.concurrent.locks.Lock;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zaz implements zabz {
    public final /* synthetic */ zaaa zaa;

    public /* synthetic */ zaz(zaaa zaaaVar) {
        this.zaa = zaaaVar;
    }

    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zaa(@NonNull ConnectionResult connectionResult) {
        this.zaa.zam.lock();
        try {
            this.zaa.zak = connectionResult;
            zaaa.zap(this.zaa);
        } finally {
            this.zaa.zam.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zab(@Nullable Bundle bundle) {
        this.zaa.zam.lock();
        try {
            this.zaa.zak = ConnectionResult.RESULT_SUCCESS;
            zaaa.zap(this.zaa);
        } finally {
            this.zaa.zam.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zac(int i, boolean z) {
        Lock lock;
        this.zaa.zam.lock();
        try {
            zaaa zaaaVar = this.zaa;
            if (zaaaVar.zal) {
                zaaaVar.zal = false;
                zaaa.zan(this.zaa, i, z);
                lock = this.zaa.zam;
            } else {
                zaaaVar.zal = true;
                this.zaa.zad.onConnectionSuspended(i);
                lock = this.zaa.zam;
            }
            lock.unlock();
        } catch (Throwable th) {
            this.zaa.zam.unlock();
            throw th;
        }
    }
}
