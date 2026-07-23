package com.google.android.play.core.assetpacks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbk extends com.google.android.play.core.internal.zzcm {
    private final File zza;
    private final File zzb;
    private final NavigableMap zzc = new TreeMap();

    public zzbk(File file, File file2) throws IOException {
        this.zza = file;
        this.zzb = file2;
        ArrayList<File> arrayList = (ArrayList) zzep.zza(file, file2);
        if (arrayList.isEmpty()) {
            throw new zzck(String.format("Virtualized slice archive empty for %s, %s", file, file2));
        }
        long length = 0;
        for (File file3 : arrayList) {
            this.zzc.put(Long.valueOf(length), file3);
            length += file3.length();
        }
    }

    private final InputStream zzd(long j, Long l) throws IOException {
        FileInputStream fileInputStream = new FileInputStream((File) this.zzc.get(l));
        if (fileInputStream.skip(j - l.longValue()) == j - l.longValue()) {
            return fileInputStream;
        }
        throw new zzck(String.format("Virtualized slice archive corrupt, could not skip in file with key %s", l));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
    }

    @Override // com.google.android.play.core.internal.zzcm
    public final long zza() {
        Map.Entry entryLastEntry = this.zzc.lastEntry();
        return ((File) entryLastEntry.getValue()).length() + ((Long) entryLastEntry.getKey()).longValue();
    }

    @Override // com.google.android.play.core.internal.zzcm
    public final InputStream zzb(long j, long j2) throws IOException {
        if (j < 0 || j2 < 0) {
            throw new zzck(String.format("Invalid input parameters %s, %s", Long.valueOf(j), Long.valueOf(j2)));
        }
        long j3 = j + j2;
        if (j3 > zza()) {
            throw new zzck(String.format("Trying to access archive out of bounds. Archive ends at: %s. Tried accessing: %s", Long.valueOf(zza()), Long.valueOf(j3)));
        }
        Long l = (Long) this.zzc.floorKey(Long.valueOf(j));
        Long l2 = (Long) this.zzc.floorKey(Long.valueOf(j3));
        if (l.equals(l2)) {
            return new zzbj(zzd(j, l), j2);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(zzd(j, l));
        Collection collectionValues = this.zzc.subMap(l, false, l2, false).values();
        if (!collectionValues.isEmpty()) {
            arrayList.add(new zzdr(Collections.enumeration(collectionValues)));
        }
        arrayList.add(new zzbj(new FileInputStream((File) this.zzc.get(l2)), j2 - (l2.longValue() - j)));
        return new SequenceInputStream(Collections.enumeration(arrayList));
    }
}
