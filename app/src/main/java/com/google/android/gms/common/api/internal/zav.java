package com.google.android.gms.common.api.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zav implements Runnable {
    public final /* synthetic */ zaaa zaa;

    public zav(zaaa zaaaVar) {
        this.zaa = zaaaVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zaa.zam.lock();
        try {
            zaaa.zap(this.zaa);
        } finally {
            this.zaa.zam.unlock();
        }
    }
}
