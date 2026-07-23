package com.google.android.play.core.assetpacks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzaw implements zzy {
    private static final com.google.android.play.core.internal.zzag zza = new com.google.android.play.core.internal.zzag("AssetPackServiceImpl");
    private static final Intent zzb = new Intent("com.google.android.play.core.assetmoduleservice.BIND_ASSET_MODULE_SERVICE").setPackage("com.android.vending");
    private final String zzc;
    private final zzco zzd;
    private final zzeb zze;

    @Nullable
    private com.google.android.play.core.internal.zzas zzf;

    @Nullable
    private com.google.android.play.core.internal.zzas zzg;
    private final AtomicBoolean zzh = new AtomicBoolean();

    public zzaw(Context context, zzco zzcoVar, zzeb zzebVar) {
        this.zzc = context.getPackageName();
        this.zzd = zzcoVar;
        this.zze = zzebVar;
        if (com.google.android.play.core.internal.zzch.zzb(context)) {
            Context contextZza = com.google.android.play.core.internal.zzce.zza(context);
            com.google.android.play.core.internal.zzag zzagVar = zza;
            Intent intent = zzb;
            zzz zzzVar = new com.google.android.play.core.internal.zzan() { // from class: com.google.android.play.core.assetpacks.zzz
                @Override // com.google.android.play.core.internal.zzan
                public final Object zza(IBinder iBinder) {
                    return com.google.android.play.core.internal.zzt.zzb(iBinder);
                }
            };
            this.zzf = new com.google.android.play.core.internal.zzas(contextZza, zzagVar, "AssetPackService", intent, zzzVar, null);
            this.zzg = new com.google.android.play.core.internal.zzas(com.google.android.play.core.internal.zzce.zza(context), zzagVar, "AssetPackService-keepAlive", intent, zzzVar, null);
        }
        zza.zza("AssetPackService initiated.", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bundle zzA() {
        Bundle bundle = new Bundle();
        bundle.putInt("playcore_version_code", 11003);
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(0);
        arrayList.add(1);
        bundle.putIntegerArrayList("supported_compression_formats", arrayList);
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        arrayList2.add(1);
        arrayList2.add(2);
        bundle.putIntegerArrayList("supported_patch_formats", arrayList2);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bundle zzB(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("session_id", i);
        return bundle;
    }

    private static Task zzC() {
        zza.zzb("onError(%d)", -11);
        return Tasks.zza(new AssetPackException(-11));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzD(int i, String str, int i2) {
        if (this.zzf == null) {
            throw new zzck("The Play Store app is not installed or is an unofficial version.", i);
        }
        com.google.android.play.core.tasks.zzi zziVarM = Insets$$ExternalSyntheticOutline0.m(zza, "notifyModuleCompleted", new Object[0]);
        this.zzf.zzq(new zzah(this, zziVarM, i, str, zziVarM, i2), zziVarM);
    }

    public static /* bridge */ /* synthetic */ Bundle zzk(int i, String str, String str2, int i2) {
        Bundle bundleZzz = zzz(i, str);
        bundleZzz.putString("slice_id", str2);
        bundleZzz.putInt("chunk_number", i2);
        return bundleZzz;
    }

    public static /* bridge */ /* synthetic */ Bundle zzn(Map map) {
        Bundle bundleZzA = zzA();
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        for (Map.Entry entry : map.entrySet()) {
            Bundle bundle = new Bundle();
            bundle.putString("installed_asset_module_name", (String) entry.getKey());
            bundle.putLong("installed_asset_module_version", ((Long) entry.getValue()).longValue());
            arrayList.add(bundle);
        }
        bundleZzA.putParcelableArrayList("installed_asset_module", arrayList);
        return bundleZzA;
    }

    public static /* bridge */ /* synthetic */ ArrayList zzv(Collection collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            Bundle bundle = new Bundle();
            bundle.putString("module_name", str);
            arrayList.add(bundle);
        }
        return arrayList;
    }

    public static /* bridge */ /* synthetic */ List zzw(zzaw zzawVar, List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AssetPackState next = AssetPackStates.zza((Bundle) it.next(), zzawVar.zzd, zzawVar.zze).packStates().values().iterator().next();
            if (next == null) {
                zza.zzb("onGetSessionStates: Bundle contained no pack.", new Object[0]);
            }
            if (zzbg.zza(next.status())) {
                arrayList.add(next.name());
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bundle zzz(int i, String str) {
        Bundle bundleZzB = zzB(i);
        bundleZzB.putString("module_name", str);
        return bundleZzB;
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final Task zza(int i, String str, String str2, int i2) {
        if (this.zzf == null) {
            return zzC();
        }
        com.google.android.play.core.tasks.zzi zziVarM = Insets$$ExternalSyntheticOutline0.m(zza, "getChunkFileDescriptor(%s, %s, %d, session=%d)", new Object[]{str, str2, Integer.valueOf(i2), Integer.valueOf(i)});
        this.zzf.zzq(new zzaj(this, zziVarM, i, str, str2, i2, zziVarM), zziVarM);
        return zziVarM.zza();
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final Task zzb(List list, zzbe zzbeVar, Map map) {
        if (this.zzf == null) {
            return zzC();
        }
        com.google.android.play.core.tasks.zzi zziVarM = Insets$$ExternalSyntheticOutline0.m(zza, "getPackStates(%s)", new Object[]{list});
        this.zzf.zzq(new zzaf(this, zziVarM, list, map, zziVarM, zzbeVar), zziVarM);
        return zziVarM.zza();
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final Task zzc(List list, List list2, Map map) {
        if (this.zzf == null) {
            return zzC();
        }
        com.google.android.play.core.tasks.zzi zziVarM = Insets$$ExternalSyntheticOutline0.m(zza, "startDownload(%s)", new Object[]{list2});
        this.zzf.zzq(new zzac(this, zziVarM, list2, map, zziVarM, list), zziVarM);
        zziVarM.zza().addOnSuccessListener(new OnSuccessListener() { // from class: com.google.android.play.core.assetpacks.zzaa
            @Override // com.google.android.play.core.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                this.zza.zzf();
            }
        });
        return zziVarM.zza();
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final Task zzd(Map map) {
        if (this.zzf == null) {
            return zzC();
        }
        com.google.android.play.core.tasks.zzi zziVarM = Insets$$ExternalSyntheticOutline0.m(zza, "syncPacks", new Object[0]);
        this.zzf.zzq(new zzae(this, zziVarM, map, zziVarM), zziVarM);
        return zziVarM.zza();
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final void zze(List list) {
        if (this.zzf == null) {
            return;
        }
        com.google.android.play.core.tasks.zzi zziVarM = Insets$$ExternalSyntheticOutline0.m(zza, "cancelDownloads(%s)", new Object[]{list});
        this.zzf.zzq(new zzad(this, zziVarM, list, zziVarM), zziVarM);
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final synchronized void zzf() {
        if (this.zzg == null) {
            zza.zze("Keep alive connection manager is not initialized.", new Object[0]);
            return;
        }
        com.google.android.play.core.internal.zzag zzagVar = zza;
        zzagVar.zzd("keepAlive", new Object[0]);
        if (!this.zzh.compareAndSet(false, true)) {
            zzagVar.zzd("Service is already kept alive.", new Object[0]);
        } else {
            com.google.android.play.core.tasks.zzi zziVar = new com.google.android.play.core.tasks.zzi();
            this.zzg.zzq(new zzak(this, zziVar, zziVar), zziVar);
        }
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final void zzg(int i, String str, String str2, int i2) {
        if (this.zzf == null) {
            throw new zzck("The Play Store app is not installed or is an unofficial version.", i);
        }
        com.google.android.play.core.tasks.zzi zziVarM = Insets$$ExternalSyntheticOutline0.m(zza, "notifyChunkTransferred", new Object[0]);
        this.zzf.zzq(new zzag(this, zziVarM, i, str, str2, i2, zziVarM), zziVarM);
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final void zzh(int i, String str) {
        zzD(i, str, 10);
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final void zzi(int i) {
        if (this.zzf == null) {
            throw new zzck("The Play Store app is not installed or is an unofficial version.", i);
        }
        com.google.android.play.core.tasks.zzi zziVarM = Insets$$ExternalSyntheticOutline0.m(zza, "notifySessionFailed", new Object[0]);
        this.zzf.zzq(new zzai(this, zziVarM, i, zziVarM), zziVarM);
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final void zzj(String str) {
        if (this.zzf == null) {
            return;
        }
        com.google.android.play.core.tasks.zzi zziVarM = Insets$$ExternalSyntheticOutline0.m(zza, "removePack(%s)", new Object[]{str});
        this.zzf.zzq(new zzab(this, zziVarM, str, zziVarM), zziVarM);
    }
}
