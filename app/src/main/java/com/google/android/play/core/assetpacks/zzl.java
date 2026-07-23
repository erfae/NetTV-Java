package com.google.android.play.core.assetpacks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.NotificationCompat;
import com.google.android.play.core.assetpacks.model.AssetPackStatus;
import com.google.android.play.core.common.PlayCoreDialogWrapperActivity;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzl implements AssetPackManager {
    private static final com.google.android.play.core.internal.zzag zza = new com.google.android.play.core.internal.zzag("AssetPackManager");
    private final zzbh zzb;
    private final com.google.android.play.core.internal.zzco zzc;
    private final zzbb zzd;
    private final com.google.android.play.core.splitinstall.zzs zze;
    private final zzde zzf;
    private final zzco zzg;
    private final zzbx zzh;
    private final com.google.android.play.core.internal.zzco zzi;
    private final com.google.android.play.core.common.zza zzj;
    private final zzeb zzk;
    private final Handler zzl = new Handler(Looper.getMainLooper());
    private boolean zzm;

    public zzl(zzbh zzbhVar, com.google.android.play.core.internal.zzco zzcoVar, zzbb zzbbVar, com.google.android.play.core.splitinstall.zzs zzsVar, zzde zzdeVar, zzco zzcoVar2, zzbx zzbxVar, com.google.android.play.core.internal.zzco zzcoVar3, com.google.android.play.core.common.zza zzaVar, zzeb zzebVar) {
        this.zzb = zzbhVar;
        this.zzc = zzcoVar;
        this.zzd = zzbbVar;
        this.zze = zzsVar;
        this.zzf = zzdeVar;
        this.zzg = zzcoVar2;
        this.zzh = zzbxVar;
        this.zzi = zzcoVar3;
        this.zzj = zzaVar;
        this.zzk = zzebVar;
    }

    private final void zzh() {
        ((Executor) this.zzi.zza()).execute(new Runnable() { // from class: com.google.android.play.core.assetpacks.zzi
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzf();
            }
        });
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackManager
    public final AssetPackStates cancel(List<String> list) {
        Map mapZzf = this.zzf.zzf(list);
        HashMap map = new HashMap();
        for (String str : list) {
            Integer num = (Integer) mapZzf.get(str);
            map.put(str, AssetPackState.zzb(str, num == null ? 0 : num.intValue(), 0, 0L, 0L, 0.0d, 0, "", ""));
        }
        ((zzy) this.zzc.zza()).zze(list);
        return new zzbo(0L, map);
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackManager
    public final void clearListeners() {
        this.zzd.zze();
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackManager
    public final Task<AssetPackStates> fetch(List<String> list) {
        Map mapZzu = this.zzb.zzu();
        ArrayList arrayList = new ArrayList(list);
        ArrayList arrayList2 = new ArrayList();
        if (!this.zzj.zza("assetOnlyUpdates")) {
            arrayList.removeAll(((HashMap) mapZzu).keySet());
            arrayList2.addAll(list);
            arrayList2.removeAll(arrayList);
        }
        if (!arrayList.isEmpty()) {
            return ((zzy) this.zzc.zza()).zzc(arrayList2, arrayList, mapZzu);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("session_id", 0);
        bundle.putInt("error_code", 0);
        for (String str : list) {
            bundle.putInt(com.google.android.play.core.assetpacks.model.zzb.zza(NotificationCompat.CATEGORY_STATUS, str), 4);
            bundle.putInt(com.google.android.play.core.assetpacks.model.zzb.zza("error_code", str), 0);
            bundle.putLong(com.google.android.play.core.assetpacks.model.zzb.zza("total_bytes_to_download", str), 0L);
            bundle.putLong(com.google.android.play.core.assetpacks.model.zzb.zza("bytes_downloaded", str), 0L);
        }
        bundle.putStringArrayList("pack_names", new ArrayList<>(list));
        bundle.putLong("total_bytes_to_download", 0L);
        bundle.putLong("bytes_downloaded", 0L);
        return Tasks.zzb(AssetPackStates.zza(bundle, this.zzg, this.zzk));
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackManager
    @Nullable
    public final AssetLocation getAssetLocation(String str, String str2) {
        AssetPackLocation assetPackLocationZzf;
        if (!this.zzm) {
            ((Executor) this.zzi.zza()).execute(new zzh(this));
            this.zzm = true;
        }
        if (this.zzb.zzG(str)) {
            try {
                assetPackLocationZzf = this.zzb.zzf(str);
            } catch (IOException unused) {
            }
        } else {
            assetPackLocationZzf = this.zze.zzc().contains(str) ? AssetPackLocation.zza() : null;
        }
        if (assetPackLocationZzf == null) {
            return null;
        }
        if (assetPackLocationZzf.packStorageMethod() == 1) {
            zzbh zzbhVar = this.zzb;
            return zzbhVar.zzd(str, str2, zzbhVar.zzs(str));
        }
        if (assetPackLocationZzf.packStorageMethod() == 0) {
            return this.zzb.zze(str, str2, assetPackLocationZzf);
        }
        zza.zza("The asset %s is not present in Asset Pack %s", str2, str);
        return null;
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackManager
    @Nullable
    public final AssetPackLocation getPackLocation(String str) {
        if (!this.zzm) {
            ((Executor) this.zzi.zza()).execute(new zzh(this));
            this.zzm = true;
        }
        if (this.zzb.zzG(str)) {
            try {
                return this.zzb.zzf(str);
            } catch (IOException unused) {
                return null;
            }
        }
        if (this.zze.zzc().contains(str)) {
            return AssetPackLocation.zza();
        }
        return null;
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackManager
    public final Map<String, AssetPackLocation> getPackLocations() {
        Map<String, AssetPackLocation> mapZzv = this.zzb.zzv();
        HashMap map = new HashMap();
        Iterator it = this.zze.zzc().iterator();
        while (it.hasNext()) {
            map.put((String) it.next(), AssetPackLocation.zza());
        }
        ((HashMap) mapZzv).putAll(map);
        return mapZzv;
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackManager
    public final Task<AssetPackStates> getPackStates(List<String> list) {
        return ((zzy) this.zzc.zza()).zzb(list, new zze(this), this.zzb.zzu());
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackManager
    public final synchronized void registerListener(AssetPackStateUpdateListener assetPackStateUpdateListener) {
        boolean zZzj = this.zzd.zzj();
        this.zzd.zzf(assetPackStateUpdateListener);
        if (zZzj) {
            return;
        }
        zzh();
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackManager
    public final Task<Void> removePack(final String str) {
        final com.google.android.play.core.tasks.zzi zziVar = new com.google.android.play.core.tasks.zzi();
        ((Executor) this.zzi.zza()).execute(new Runnable() { // from class: com.google.android.play.core.assetpacks.zzj
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzd(str, zziVar);
            }
        });
        return zziVar.zza();
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackManager
    public final Task<Integer> showCellularDataConfirmation(Activity activity) {
        if (activity == null) {
            return Tasks.zza(new AssetPackException(-3));
        }
        if (this.zzh.zza() == null) {
            return Tasks.zza(new AssetPackException(-12));
        }
        Intent intent = new Intent(activity, (Class<?>) PlayCoreDialogWrapperActivity.class);
        intent.putExtra("confirmation_intent", this.zzh.zza());
        com.google.android.play.core.tasks.zzi zziVar = new com.google.android.play.core.tasks.zzi();
        intent.putExtra("result_receiver", new zzk(this, this.zzl, zziVar));
        activity.startActivity(intent);
        return zziVar.zza();
    }

    @Override // com.google.android.play.core.assetpacks.AssetPackManager
    public final void unregisterListener(AssetPackStateUpdateListener assetPackStateUpdateListener) {
        this.zzd.zzh(assetPackStateUpdateListener);
    }

    @AssetPackStatus
    @VisibleForTesting
    public final int zza(@AssetPackStatus int i, String str) {
        if (!this.zzb.zzG(str) && i == 4) {
            return 8;
        }
        if (!this.zzb.zzG(str) || i == 4) {
            return i;
        }
        return 4;
    }

    public final /* synthetic */ void zzc() {
        this.zzb.zzy();
        this.zzb.zzw();
        this.zzb.zzx();
    }

    public final /* synthetic */ void zzd(String str, com.google.android.play.core.tasks.zzi zziVar) {
        if (!this.zzb.zzD(str)) {
            zziVar.zzb(new IOException(String.format("Failed to remove pack %s.", str)));
        } else {
            zziVar.zzc(null);
            ((zzy) this.zzc.zza()).zzj(str);
        }
    }

    public final /* synthetic */ void zzf() {
        Task taskZzd = ((zzy) this.zzc.zza()).zzd(this.zzb.zzu());
        Executor executor = (Executor) this.zzi.zza();
        final zzbh zzbhVar = this.zzb;
        Objects.requireNonNull(zzbhVar);
        taskZzd.addOnSuccessListener(executor, new OnSuccessListener() { // from class: com.google.android.play.core.assetpacks.zzg
            @Override // com.google.android.play.core.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                zzbhVar.zzC((List) obj);
            }
        });
        taskZzd.addOnFailureListener((Executor) this.zzi.zza(), new OnFailureListener() { // from class: com.google.android.play.core.assetpacks.zzf
            @Override // com.google.android.play.core.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                zzl.zza.zze(String.format("Could not sync active asset packs. %s", exc), new Object[0]);
            }
        });
    }

    public final void zzg(boolean z) {
        boolean zZzj = this.zzd.zzj();
        this.zzd.zzg(z);
        if (!z || zZzj) {
            return;
        }
        zzh();
    }
}
