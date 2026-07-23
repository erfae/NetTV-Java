package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zacy implements Runnable {
    public final /* synthetic */ Result zaa;
    public final /* synthetic */ zada zab;

    public zacy(zada zadaVar, Result result) {
        this.zab = zadaVar;
        this.zaa = result;
    }

    @Override // java.lang.Runnable
    @WorkerThread
    public final void run() {
        GoogleApiClient googleApiClient;
        try {
            try {
                ThreadLocal<Boolean> threadLocal = BasePendingResult.zaa;
                threadLocal.set(Boolean.TRUE);
                PendingResult pendingResultOnSuccess = ((ResultTransform) Preconditions.checkNotNull(this.zab.zaa)).onSuccess(this.zaa);
                zada zadaVar = this.zab;
                zadaVar.zah.sendMessage(zadaVar.zah.obtainMessage(0, pendingResultOnSuccess));
                threadLocal.set(Boolean.FALSE);
                zada.zan(this.zaa);
                googleApiClient = (GoogleApiClient) this.zab.zag.get();
                if (googleApiClient == null) {
                    return;
                }
            } catch (RuntimeException e) {
                zada zadaVar2 = this.zab;
                zadaVar2.zah.sendMessage(zadaVar2.zah.obtainMessage(1, e));
                BasePendingResult.zaa.set(Boolean.FALSE);
                zada.zan(this.zaa);
                googleApiClient = (GoogleApiClient) this.zab.zag.get();
                if (googleApiClient == null) {
                    return;
                }
            }
            googleApiClient.zap(this.zab);
        } catch (Throwable th) {
            BasePendingResult.zaa.set(Boolean.FALSE);
            zada.zan(this.zaa);
            GoogleApiClient googleApiClient2 = (GoogleApiClient) this.zab.zag.get();
            if (googleApiClient2 != null) {
                googleApiClient2.zap(this.zab);
            }
            throw th;
        }
    }
}
