package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
class zzal extends com.google.android.play.core.internal.zzv {
    public final com.google.android.play.core.tasks.zzi zza;
    public final /* synthetic */ zzaw zzb;

    public zzal(zzaw zzawVar, com.google.android.play.core.tasks.zzi zziVar) {
        this.zzb = zzawVar;
        this.zza = zziVar;
    }

    @Override // com.google.android.play.core.internal.zzw
    public final void zzb(int i, Bundle bundle) {
        this.zzb.zzf.zzs(this.zza);
        zzaw.zza.zzd("onCancelDownload(%d)", Integer.valueOf(i));
    }

    @Override // com.google.android.play.core.internal.zzw
    public final void zzc(Bundle bundle) {
        this.zzb.zzf.zzs(this.zza);
        zzaw.zza.zzd("onCancelDownloads()", new Object[0]);
    }

    @Override // com.google.android.play.core.internal.zzw
    public void zzd(Bundle bundle) {
        this.zzb.zzf.zzs(this.zza);
        int i = bundle.getInt("error_code");
        zzaw.zza.zzb("onError(%d)", Integer.valueOf(i));
        this.zza.zzd(new AssetPackException(i));
    }

    @Override // com.google.android.play.core.internal.zzw
    public void zze(Bundle bundle, Bundle bundle2) throws RemoteException {
        this.zzb.zzf.zzs(this.zza);
        zzaw.zza.zzd("onGetChunkFileDescriptor", new Object[0]);
    }

    @Override // com.google.android.play.core.internal.zzw
    public final void zzf(int i, Bundle bundle) {
        this.zzb.zzf.zzs(this.zza);
        zzaw.zza.zzd("onGetSession(%d)", Integer.valueOf(i));
    }

    @Override // com.google.android.play.core.internal.zzw
    public void zzg(List list) {
        this.zzb.zzf.zzs(this.zza);
        zzaw.zza.zzd("onGetSessionStates", new Object[0]);
    }

    @Override // com.google.android.play.core.internal.zzw
    public void zzh(Bundle bundle, Bundle bundle2) {
        this.zzb.zzg.zzs(this.zza);
        zzaw.zza.zzd("onKeepAlive(%b)", Boolean.valueOf(bundle.getBoolean("keep_alive")));
    }

    @Override // com.google.android.play.core.internal.zzw
    public final void zzi(Bundle bundle, Bundle bundle2) {
        this.zzb.zzf.zzs(this.zza);
        zzaw.zza.zzd("onNotifyChunkTransferred(%s, %s, %d, session=%d)", bundle.getString("module_name"), bundle.getString("slice_id"), Integer.valueOf(bundle.getInt("chunk_number")), Integer.valueOf(bundle.getInt("session_id")));
    }

    @Override // com.google.android.play.core.internal.zzw
    public final void zzj(Bundle bundle, Bundle bundle2) {
        this.zzb.zzf.zzs(this.zza);
        zzaw.zza.zzd("onNotifyModuleCompleted(%s, sessionId=%d)", bundle.getString("module_name"), Integer.valueOf(bundle.getInt("session_id")));
    }

    @Override // com.google.android.play.core.internal.zzw
    public final void zzk(Bundle bundle, Bundle bundle2) {
        this.zzb.zzf.zzs(this.zza);
        zzaw.zza.zzd("onNotifySessionFailed(%d)", Integer.valueOf(bundle.getInt("session_id")));
    }

    @Override // com.google.android.play.core.internal.zzw
    public final void zzl(Bundle bundle, Bundle bundle2) {
        this.zzb.zzf.zzs(this.zza);
        zzaw.zza.zzd("onRemoveModule()", new Object[0]);
    }

    @Override // com.google.android.play.core.internal.zzw
    public void zzm(Bundle bundle, Bundle bundle2) {
        this.zzb.zzf.zzs(this.zza);
        zzaw.zza.zzd("onRequestDownloadInfo()", new Object[0]);
    }

    @Override // com.google.android.play.core.internal.zzw
    public void zzn(int i, Bundle bundle) {
        this.zzb.zzf.zzs(this.zza);
        zzaw.zza.zzd("onStartDownload(%d)", Integer.valueOf(i));
    }
}
