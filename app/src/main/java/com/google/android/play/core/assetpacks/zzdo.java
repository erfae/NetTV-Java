package com.google.android.play.core.assetpacks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.play.core.assetpacks.model.AssetPackStatus;
import com.google.android.play.core.common.LocalTestingException;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzdo implements zzy {
    private static final com.google.android.play.core.internal.zzag zza = new com.google.android.play.core.internal.zzag("FakeAssetPackService");
    private static final AtomicInteger zzb = new AtomicInteger(1);
    private final String zzc;
    private final zzbb zzd;
    private final zzco zze;
    private final Context zzf;
    private final zzed zzg;
    private final com.google.android.play.core.internal.zzco zzh;
    private final zzeb zzi;
    private final Handler zzj = new Handler(Looper.getMainLooper());

    @VisibleForTesting
    public zzdo(File file, zzbb zzbbVar, zzco zzcoVar, Context context, zzed zzedVar, com.google.android.play.core.internal.zzco zzcoVar2, zzeb zzebVar) {
        this.zzc = file.getAbsolutePath();
        this.zzd = zzbbVar;
        this.zze = zzcoVar;
        this.zzf = context;
        this.zzg = zzedVar;
        this.zzh = zzcoVar2;
        this.zzi = zzebVar;
    }

    @VisibleForTesting
    public static long zzk(@AssetPackStatus int i, long j) {
        if (i == 2) {
            return j / 2;
        }
        if (i == 3 || i == 4) {
            return j;
        }
        return 0L;
    }

    private final Bundle zzp(int i, String str, @AssetPackStatus int i2) throws LocalTestingException {
        Bundle bundle = new Bundle();
        bundle.putInt("app_version_code", this.zzg.zza());
        bundle.putInt("session_id", i);
        File[] fileArrZzs = zzs(str);
        ArrayList<String> arrayList = new ArrayList<>();
        long length = 0;
        for (File file : fileArrZzs) {
            length += file.length();
            ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>();
            arrayList2.add(i2 == 3 ? new Intent().setData(Uri.EMPTY) : null);
            String strZza = com.google.android.play.core.internal.zzcj.zza(file);
            bundle.putParcelableArrayList(com.google.android.play.core.assetpacks.model.zzb.zzb("chunk_intents", str, strZza), arrayList2);
            bundle.putString(com.google.android.play.core.assetpacks.model.zzb.zzb("uncompressed_hash_sha256", str, strZza), zzr(file));
            bundle.putLong(com.google.android.play.core.assetpacks.model.zzb.zzb("uncompressed_size", str, strZza), file.length());
            arrayList.add(strZza);
        }
        bundle.putStringArrayList(com.google.android.play.core.assetpacks.model.zzb.zza("slice_ids", str), arrayList);
        bundle.putLong(com.google.android.play.core.assetpacks.model.zzb.zza("pack_version", str), this.zzg.zza());
        bundle.putInt(com.google.android.play.core.assetpacks.model.zzb.zza(NotificationCompat.CATEGORY_STATUS, str), i2);
        bundle.putInt(com.google.android.play.core.assetpacks.model.zzb.zza("error_code", str), 0);
        bundle.putLong(com.google.android.play.core.assetpacks.model.zzb.zza("bytes_downloaded", str), zzk(i2, length));
        bundle.putLong(com.google.android.play.core.assetpacks.model.zzb.zza("total_bytes_to_download", str), length);
        bundle.putStringArrayList("pack_names", new ArrayList<>(Arrays.asList(str)));
        bundle.putLong("bytes_downloaded", zzk(i2, length));
        bundle.putLong("total_bytes_to_download", length);
        final Intent intentPutExtra = new Intent("com.google.android.play.core.assetpacks.receiver.ACTION_SESSION_UPDATE").putExtra("com.google.android.play.core.assetpacks.receiver.EXTRA_SESSION_STATE", bundle);
        this.zzj.post(new Runnable() { // from class: com.google.android.play.core.assetpacks.zzdl
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzl(intentPutExtra);
            }
        });
        return bundle;
    }

    private final AssetPackState zzq(String str, @AssetPackStatus int i) throws LocalTestingException {
        long length = 0;
        for (File file : zzs(str)) {
            length += file.length();
        }
        return AssetPackState.zzb(str, i, 0, zzk(i, length), length, this.zze.zza(str), 1, String.valueOf(this.zzg.zza()), this.zzi.zza(str));
    }

    private static String zzr(File file) throws LocalTestingException {
        try {
            return zzdq.zza(Arrays.asList(file));
        } catch (IOException e) {
            throw new LocalTestingException(String.format("Could not digest file: %s.", file), e);
        } catch (NoSuchAlgorithmException e2) {
            throw new LocalTestingException("SHA256 algorithm not supported.", e2);
        }
    }

    private final File[] zzs(final String str) throws LocalTestingException {
        File file = new File(this.zzc);
        if (!file.isDirectory()) {
            throw new LocalTestingException(String.format("Local testing directory '%s' not found.", file));
        }
        File[] fileArrListFiles = file.listFiles(new FilenameFilter() { // from class: com.google.android.play.core.assetpacks.zzdj
            @Override // java.io.FilenameFilter
            public final boolean accept(File file2, String str2) {
                return str2.startsWith(String.valueOf(str).concat("-")) && str2.endsWith(".apk");
            }
        });
        if (fileArrListFiles == null) {
            throw new LocalTestingException(String.format("Failed fetching APKs for pack '%s'.", str));
        }
        if (fileArrListFiles.length == 0) {
            throw new LocalTestingException(String.format("No APKs available for pack '%s'.", str));
        }
        for (File file2 : fileArrListFiles) {
            if (com.google.android.play.core.internal.zzcj.zza(file2).equals(str)) {
                return fileArrListFiles;
            }
        }
        throw new LocalTestingException(String.format("No main slice available for pack '%s'.", str));
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final Task zza(int i, String str, String str2, int i2) {
        zza.zzd("getChunkFileDescriptor(session=%d, %s, %s, %d)", Integer.valueOf(i), str, str2, Integer.valueOf(i2));
        com.google.android.play.core.tasks.zzi zziVar = new com.google.android.play.core.tasks.zzi();
        try {
            for (File file : zzs(str)) {
                if (com.google.android.play.core.internal.zzcj.zza(file).equals(str2)) {
                    zziVar.zzc(ParcelFileDescriptor.open(file, 268435456));
                    return zziVar.zza();
                }
            }
            throw new LocalTestingException(String.format("Local testing slice for '%s' not found.", str2));
        } catch (LocalTestingException e) {
            zza.zze("getChunkFileDescriptor failed", e);
            zziVar.zzb(e);
        } catch (FileNotFoundException e2) {
            zza.zze("getChunkFileDescriptor failed", e2);
            zziVar.zzb(new LocalTestingException("Asset Slice file not found.", e2));
        }
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final Task zzb(final List list, final zzbe zzbeVar, Map map) {
        final com.google.android.play.core.tasks.zzi zziVarM = Insets$$ExternalSyntheticOutline0.m(zza, "getPackStates(%s)", new Object[]{list});
        ((Executor) this.zzh.zza()).execute(new Runnable() { // from class: com.google.android.play.core.assetpacks.zzdm
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzm(list, zzbeVar, zziVarM);
            }
        });
        return zziVarM.zza();
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final Task zzc(final List list, final List list2, Map map) {
        final com.google.android.play.core.tasks.zzi zziVarM = Insets$$ExternalSyntheticOutline0.m(zza, "startDownload(%s)", new Object[]{list2});
        ((Executor) this.zzh.zza()).execute(new Runnable() { // from class: com.google.android.play.core.assetpacks.zzdn
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzo(list2, zziVarM, list);
            }
        });
        return zziVarM.zza();
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final Task zzd(Map map) {
        zza.zzd("syncPacks()", new Object[0]);
        return Tasks.zzb(new ArrayList());
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final void zze(List list) {
        zza.zzd("cancelDownload(%s)", list);
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final void zzf() {
        zza.zzd("keepAlive", new Object[0]);
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final void zzg(int i, String str, String str2, int i2) {
        zza.zzd("notifyChunkTransferred", new Object[0]);
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final void zzh(final int i, final String str) {
        zza.zzd("notifyModuleCompleted", new Object[0]);
        ((Executor) this.zzh.zza()).execute(new Runnable() { // from class: com.google.android.play.core.assetpacks.zzdk
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzn(i, str);
            }
        });
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final void zzi(int i) {
        zza.zzd("notifySessionFailed", new Object[0]);
    }

    @Override // com.google.android.play.core.assetpacks.zzy
    public final void zzj(String str) {
        zza.zzd("removePack(%s)", str);
    }

    public final /* synthetic */ void zzl(Intent intent) {
        this.zzd.zza(this.zzf, intent);
    }

    public final /* synthetic */ void zzm(List list, zzbe zzbeVar, com.google.android.play.core.tasks.zzi zziVar) {
        HashMap map = new HashMap();
        Iterator it = list.iterator();
        long j = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            try {
                AssetPackState assetPackStateZzq = zzq(str, ((zze) zzbeVar).zza.zza(8, str));
                j += assetPackStateZzq.totalBytesToDownload();
                map.put(str, assetPackStateZzq);
            } catch (LocalTestingException e) {
                zziVar.zzb(e);
                return;
            }
        }
        zziVar.zzc(new zzbo(j, map));
    }

    public final /* synthetic */ void zzn(int i, String str) {
        try {
            zzp(i, str, 4);
        } catch (LocalTestingException e) {
            zza.zze("notifyModuleCompleted failed", e);
        }
    }

    public final /* synthetic */ void zzo(List list, com.google.android.play.core.tasks.zzi zziVar, List list2) {
        HashMap map = new HashMap();
        Iterator it = list.iterator();
        long j = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            try {
                AssetPackState assetPackStateZzq = zzq(str, 1);
                j += assetPackStateZzq.totalBytesToDownload();
                map.put(str, assetPackStateZzq);
            } catch (LocalTestingException e) {
                zziVar.zzb(e);
                return;
            }
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            String str2 = (String) it2.next();
            try {
                int andIncrement = zzb.getAndIncrement();
                zzp(andIncrement, str2, 1);
                zzp(andIncrement, str2, 2);
                zzp(andIncrement, str2, 3);
            } catch (LocalTestingException e2) {
                zziVar.zzb(e2);
                return;
            }
        }
        Iterator it3 = list2.iterator();
        while (it3.hasNext()) {
            String str3 = (String) it3.next();
            map.put(str3, AssetPackState.zzb(str3, 4, 0, 0L, 0L, 0.0d, 1, String.valueOf(this.zzg.zza()), String.valueOf(this.zzg.zza())));
        }
        zziVar.zzc(new zzbo(j, map));
    }
}
