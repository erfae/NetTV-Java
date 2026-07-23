package com.google.android.play.core.assetpacks;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzde {
    private static final com.google.android.play.core.internal.zzag zza = new com.google.android.play.core.internal.zzag("ExtractorSessionStoreView");
    private final zzbh zzb;
    private final com.google.android.play.core.internal.zzco zzc;
    private final zzco zzd;
    private final com.google.android.play.core.internal.zzco zze;
    private final Map zzf = new HashMap();
    private final ReentrantLock zzg = new ReentrantLock();

    public zzde(zzbh zzbhVar, com.google.android.play.core.internal.zzco zzcoVar, zzco zzcoVar2, com.google.android.play.core.internal.zzco zzcoVar3) {
        this.zzb = zzbhVar;
        this.zzc = zzcoVar;
        this.zzd = zzcoVar2;
        this.zze = zzcoVar3;
    }

    private final zzdb zzq(int i) {
        Map map = this.zzf;
        Integer numValueOf = Integer.valueOf(i);
        zzdb zzdbVar = (zzdb) map.get(numValueOf);
        if (zzdbVar != null) {
            return zzdbVar;
        }
        throw new zzck(String.format("Could not find session %d while trying to get it", numValueOf), i);
    }

    private final Object zzr(zzdd zzddVar) {
        try {
            this.zzg.lock();
            return zzddVar.zza();
        } finally {
            this.zzg.unlock();
        }
    }

    private static String zzs(Bundle bundle) {
        ArrayList<String> stringArrayList = bundle.getStringArrayList("pack_names");
        if (stringArrayList == null || stringArrayList.isEmpty()) {
            throw new zzck("Session without pack received.");
        }
        return stringArrayList.get(0);
    }

    private static List zzt(List list) {
        return list == null ? Collections.emptyList() : list;
    }

    private final Map zzu(final List list) {
        return (Map) zzr(new zzdd() { // from class: com.google.android.play.core.assetpacks.zzcx
            @Override // com.google.android.play.core.assetpacks.zzdd
            public final Object zza() {
                return this.zza.zzi(list);
            }
        });
    }

    public final /* synthetic */ Boolean zza(Bundle bundle) {
        int i = bundle.getInt("session_id");
        if (i == 0) {
            return Boolean.TRUE;
        }
        Map map = this.zzf;
        Integer numValueOf = Integer.valueOf(i);
        if (!map.containsKey(numValueOf)) {
            return Boolean.TRUE;
        }
        zzdb zzdbVar = (zzdb) this.zzf.get(numValueOf);
        if (zzdbVar.zzc.zzd == 6) {
            return Boolean.FALSE;
        }
        return Boolean.valueOf(!zzbg.zzc(zzdbVar.zzc.zzd, bundle.getInt(com.google.android.play.core.assetpacks.model.zzb.zza(NotificationCompat.CATEGORY_STATUS, zzs(bundle)))));
    }

    public final /* synthetic */ Boolean zzb(Bundle bundle) {
        int i = bundle.getInt("session_id");
        if (i == 0) {
            return Boolean.FALSE;
        }
        Map map = this.zzf;
        Integer numValueOf = Integer.valueOf(i);
        boolean z = true;
        if (map.containsKey(numValueOf)) {
            zzdb zzdbVarZzq = zzq(i);
            int i2 = bundle.getInt(com.google.android.play.core.assetpacks.model.zzb.zza(NotificationCompat.CATEGORY_STATUS, zzdbVarZzq.zzc.zza));
            zzda zzdaVar = zzdbVarZzq.zzc;
            int i3 = zzdaVar.zzd;
            if (zzbg.zzc(i3, i2)) {
                zza.zza("Found stale update for session %s with status %d.", numValueOf, Integer.valueOf(i3));
                zzda zzdaVar2 = zzdbVarZzq.zzc;
                String str = zzdaVar2.zza;
                int i4 = zzdaVar2.zzd;
                if (i4 == 4) {
                    ((zzy) this.zzc.zza()).zzh(i, str);
                } else if (i4 == 5) {
                    ((zzy) this.zzc.zza()).zzi(i);
                } else if (i4 == 6) {
                    ((zzy) this.zzc.zza()).zze(Arrays.asList(str));
                }
            } else {
                zzdaVar.zzd = i2;
                if (zzbg.zzd(i2)) {
                    zzn(i);
                    this.zzd.zzc(zzdbVarZzq.zzc.zza);
                } else {
                    for (zzdc zzdcVar : zzdaVar.zzf) {
                        ArrayList parcelableArrayList = bundle.getParcelableArrayList(com.google.android.play.core.assetpacks.model.zzb.zzb("chunk_intents", zzdbVarZzq.zzc.zza, zzdcVar.zza));
                        if (parcelableArrayList != null) {
                            for (int i5 = 0; i5 < parcelableArrayList.size(); i5++) {
                                if (parcelableArrayList.get(i5) != null && ((Intent) parcelableArrayList.get(i5)).getData() != null) {
                                    ((zzcz) zzdcVar.zzd.get(i5)).zza = true;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            String strZzs = zzs(bundle);
            long j = bundle.getLong(com.google.android.play.core.assetpacks.model.zzb.zza("pack_version", strZzs));
            String string = bundle.getString(com.google.android.play.core.assetpacks.model.zzb.zza("pack_version_tag", strZzs), "");
            int i6 = bundle.getInt(com.google.android.play.core.assetpacks.model.zzb.zza(NotificationCompat.CATEGORY_STATUS, strZzs));
            long j2 = bundle.getLong(com.google.android.play.core.assetpacks.model.zzb.zza("total_bytes_to_download", strZzs));
            ArrayList<String> stringArrayList = bundle.getStringArrayList(com.google.android.play.core.assetpacks.model.zzb.zza("slice_ids", strZzs));
            ArrayList arrayList = new ArrayList();
            for (String str2 : zzt(stringArrayList)) {
                ArrayList parcelableArrayList2 = bundle.getParcelableArrayList(com.google.android.play.core.assetpacks.model.zzb.zzb("chunk_intents", strZzs, str2));
                ArrayList arrayList2 = new ArrayList();
                Iterator it = zzt(parcelableArrayList2).iterator();
                while (it.hasNext()) {
                    if (((Intent) it.next()) == null) {
                        z = false;
                    }
                    arrayList2.add(new zzcz(z));
                    z = true;
                }
                String string2 = bundle.getString(com.google.android.play.core.assetpacks.model.zzb.zzb("uncompressed_hash_sha256", strZzs, str2));
                long j3 = bundle.getLong(com.google.android.play.core.assetpacks.model.zzb.zzb("uncompressed_size", strZzs, str2));
                int i7 = bundle.getInt(com.google.android.play.core.assetpacks.model.zzb.zzb("patch_format", strZzs, str2), 0);
                arrayList.add(i7 != 0 ? new zzdc(str2, string2, j3, arrayList2, 0, i7) : new zzdc(str2, string2, j3, arrayList2, bundle.getInt(com.google.android.play.core.assetpacks.model.zzb.zzb("compression_format", strZzs, str2), 0), 0));
                z = true;
            }
            this.zzf.put(Integer.valueOf(i), new zzdb(i, bundle.getInt("app_version_code"), new zzda(strZzs, j, i6, j2, arrayList, string)));
        }
        return Boolean.TRUE;
    }

    public final /* synthetic */ void zzc(String str, int i, long j) {
        zzdb zzdbVar = (zzdb) zzu(Arrays.asList(str)).get(str);
        if (zzdbVar == null || zzbg.zzd(zzdbVar.zzc.zzd)) {
            zza.zzb(String.format("Could not find pack %s while trying to complete it", str), new Object[0]);
        }
        this.zzb.zzE(str, i, j);
        zzdbVar.zzc.zzd = 4;
    }

    public final /* synthetic */ void zzd(int i) {
        zzq(i).zzc.zzd = 5;
    }

    public final /* synthetic */ void zze(int i) {
        zzdb zzdbVarZzq = zzq(i);
        zzda zzdaVar = zzdbVarZzq.zzc;
        if (!zzbg.zzd(zzdaVar.zzd)) {
            throw new zzck(String.format("Could not safely delete session %d because it is not in a terminal state.", Integer.valueOf(i)), i);
        }
        this.zzb.zzE(zzdaVar.zza, zzdbVarZzq.zzb, zzdaVar.zzb);
        zzda zzdaVar2 = zzdbVarZzq.zzc;
        int i2 = zzdaVar2.zzd;
        if (i2 == 5 || i2 == 6) {
            this.zzb.zzF(zzdaVar2.zza, zzdbVarZzq.zzb, zzdaVar2.zzb);
        }
    }

    public final Map zzf(final List list) {
        return (Map) zzr(new zzdd() { // from class: com.google.android.play.core.assetpacks.zzcw
            @Override // com.google.android.play.core.assetpacks.zzdd
            public final Object zza() {
                return this.zza.zzh(list);
            }
        });
    }

    public final Map zzg() {
        return this.zzf;
    }

    public final /* synthetic */ Map zzh(List list) {
        Map mapZzu = zzu(list);
        HashMap map = new HashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            final zzdb zzdbVar = (zzdb) mapZzu.get(str);
            if (zzdbVar == null) {
                map.put(str, 8);
            } else {
                zzda zzdaVar = zzdbVar.zzc;
                if (zzbg.zza(zzdaVar.zzd)) {
                    try {
                        zzdaVar.zzd = 6;
                        ((Executor) this.zze.zza()).execute(new Runnable() { // from class: com.google.android.play.core.assetpacks.zzcy
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.zza.zzn(zzdbVar.zza);
                            }
                        });
                        this.zzd.zzc(str);
                    } catch (zzck unused) {
                        zza.zzd("Session %d with pack %s does not exist, no need to cancel.", Integer.valueOf(zzdbVar.zza), str);
                    }
                }
                map.put(str, Integer.valueOf(zzdbVar.zzc.zzd));
            }
        }
        return map;
    }

    public final /* synthetic */ Map zzi(List list) {
        HashMap map = new HashMap();
        for (zzdb zzdbVar : this.zzf.values()) {
            String str = zzdbVar.zzc.zza;
            if (list.contains(str)) {
                zzdb zzdbVar2 = (zzdb) map.get(str);
                if ((zzdbVar2 == null ? -1 : zzdbVar2.zza) < zzdbVar.zza) {
                    map.put(str, zzdbVar);
                }
            }
        }
        return map;
    }

    public final void zzj() {
        this.zzg.lock();
    }

    public final void zzk(final String str, final int i, final long j) {
        zzr(new zzdd() { // from class: com.google.android.play.core.assetpacks.zzcv
            @Override // com.google.android.play.core.assetpacks.zzdd
            public final Object zza() {
                this.zza.zzc(str, i, j);
                return null;
            }
        });
    }

    public final void zzl() {
        this.zzg.unlock();
    }

    public final void zzm(final int i) {
        final int i2 = 5;
        zzr(new zzdd(i, i2) { // from class: com.google.android.play.core.assetpacks.zzcs
            public final /* synthetic */ int zzb;

            @Override // com.google.android.play.core.assetpacks.zzdd
            public final Object zza() {
                this.zza.zzd(this.zzb);
                return null;
            }
        });
    }

    public final void zzn(final int i) {
        zzr(new zzdd() { // from class: com.google.android.play.core.assetpacks.zzcr
            @Override // com.google.android.play.core.assetpacks.zzdd
            public final Object zza() {
                this.zza.zze(i);
                return null;
            }
        });
    }

    public final boolean zzo(final Bundle bundle) {
        return ((Boolean) zzr(new zzdd() { // from class: com.google.android.play.core.assetpacks.zzct
            @Override // com.google.android.play.core.assetpacks.zzdd
            public final Object zza() {
                return this.zza.zza(bundle);
            }
        })).booleanValue();
    }

    public final boolean zzp(final Bundle bundle) {
        return ((Boolean) zzr(new zzdd() { // from class: com.google.android.play.core.assetpacks.zzcu
            @Override // com.google.android.play.core.assetpacks.zzdd
            public final Object zza() {
                return this.zza.zzb(bundle);
            }
        })).booleanValue();
    }
}
