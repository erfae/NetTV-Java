package com.google.android.play.core.splitinstall;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzw implements Runnable {
    public final /* synthetic */ SplitInstallSessionState zza;
    public final /* synthetic */ int zzb;
    public final /* synthetic */ int zzc;
    public final /* synthetic */ zzx zzd;

    public zzw(zzx zzxVar, SplitInstallSessionState splitInstallSessionState, int i, int i2) {
        this.zzd = zzxVar;
        this.zza = splitInstallSessionState;
        this.zzb = i;
        this.zzc = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzx zzxVar = this.zzd;
        SplitInstallSessionState splitInstallSessionState = this.zza;
        zzxVar.zzm(new zza(splitInstallSessionState.sessionId(), this.zzb, this.zzc, splitInstallSessionState.bytesDownloaded(), splitInstallSessionState.totalBytesToDownload(), splitInstallSessionState.zzb(), splitInstallSessionState.zza(), splitInstallSessionState.resolutionIntent(), splitInstallSessionState.zzc()));
    }
}
