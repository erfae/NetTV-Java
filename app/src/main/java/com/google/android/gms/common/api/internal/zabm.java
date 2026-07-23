package com.google.android.gms.common.api.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* JADX INFO: loaded from: classes.dex */
final class zabm implements Runnable {
    public final /* synthetic */ zabq zaa;

    public zabm(zabq zabqVar) {
        this.zaa = zabqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zaa.zaG();
    }
}
