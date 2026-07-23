package com.google.android.play.core.assetpacks;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzcf {
    private static final com.google.android.play.core.internal.zzag zza = new com.google.android.play.core.internal.zzag("ExtractChunkTaskHandler");
    private final byte[] zzb = new byte[8192];
    private final zzbh zzc;
    private final com.google.android.play.core.internal.zzco zzd;
    private final com.google.android.play.core.internal.zzco zze;
    private final zzco zzf;
    private final zzeb zzg;

    public zzcf(zzbh zzbhVar, com.google.android.play.core.internal.zzco zzcoVar, com.google.android.play.core.internal.zzco zzcoVar2, zzco zzcoVar3, zzeb zzebVar) {
        this.zzc = zzbhVar;
        this.zzd = zzcoVar;
        this.zze = zzcoVar2;
        this.zzf = zzcoVar3;
        this.zzg = zzebVar;
    }

    private final File zzb(zzce zzceVar) {
        File fileZzp = this.zzc.zzp(zzceVar.zzl, zzceVar.zza, zzceVar.zzb, zzceVar.zzd);
        if (!fileZzp.exists()) {
            fileZzp.mkdirs();
        }
        return fileZzp;
    }

    /* JADX WARN: Code duplicated, block: B:52:0x018c  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.google.android.play.core.assetpacks.zzbh] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void zza(zzce zzceVar) throws Throwable {
        InputStream sequenceInputStream;
        zzet zzetVarZzb;
        File fileZzc;
        long length;
        int iMin;
        int iMax;
        long j;
        ?? r10 = this.zzc;
        String str = zzceVar.zzl;
        int i = zzceVar.zza;
        long j2 = zzceVar.zzb;
        String str2 = zzceVar.zzd;
        zzen zzenVar = new zzen(r10, str, i, j2, str2);
        File fileZzo = r10.zzo(str, i, j2, str2);
        if (!fileZzo.exists()) {
            fileZzo.mkdirs();
        }
        try {
            InputStream inputStream = zzceVar.zzj;
            InputStream gZIPInputStream = zzceVar.zze != 1 ? inputStream : new GZIPInputStream(inputStream, 8192);
            try {
                try {
                    if (zzceVar.zzf > 0) {
                        zzem zzemVarZzb = zzenVar.zzb();
                        int iZzb = zzemVarZzb.zzb();
                        int i2 = zzceVar.zzf;
                        if (iZzb != i2 - 1) {
                            throw new zzck(String.format("Trying to resume with chunk number %s when previously processed chunk was number %s.", Integer.valueOf(i2), Integer.valueOf(zzemVarZzb.zzb())), zzceVar.zzk);
                        }
                        int iZza = zzemVarZzb.zza();
                        if (iZza == 1) {
                            zza.zza("Resuming zip entry from last chunk during file %s.", zzemVarZzb.zze());
                            File file = new File(zzemVarZzb.zze());
                            if (!file.exists()) {
                                throw new zzck("Partial file specified in checkpoint does not exist. Corrupt directory.", zzceVar.zzk);
                            }
                            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                            randomAccessFile.seek(zzemVarZzb.zzc());
                            long jZzd = zzemVarZzb.zzd();
                            while (true) {
                                iMin = (int) Math.min(jZzd, PlaybackStateCompat.ACTION_PLAY_FROM_URI);
                                iMax = Math.max(gZIPInputStream.read(this.zzb, 0, iMin), 0);
                                if (iMax > 0) {
                                    randomAccessFile.write(this.zzb, 0, iMax);
                                }
                                j = jZzd - ((long) iMax);
                                if (j <= 0 || iMax <= 0) {
                                    break;
                                    break;
                                }
                                jZzd = j;
                            }
                            long length2 = randomAccessFile.length();
                            randomAccessFile.close();
                            if (iMax != iMin) {
                                zza.zza("Chunk has ended while resuming the previous chunks file content.", new Object[0]);
                                zzenVar.zzg(file.getCanonicalPath(), length2, j, zzceVar.zzf);
                            } else {
                                gZIPInputStream = gZIPInputStream;
                                sequenceInputStream = gZIPInputStream;
                            }
                        } else if (iZza == 2) {
                            zza.zza("Resuming zip entry from last chunk during local file header.", new Object[0]);
                            zzbh zzbhVar = this.zzc;
                            String str3 = zzceVar.zzl;
                            int i3 = zzceVar.zza;
                            long j3 = zzceVar.zzb;
                            String str4 = zzceVar.zzd;
                            Objects.requireNonNull(zzbhVar);
                            File file2 = new File(zzbhVar.zzo(str3, i3, j3, str4), "checkpoint_ext.dat");
                            if (!file2.exists()) {
                                throw new zzck("Checkpoint extension file not found.", zzceVar.zzk);
                            }
                            gZIPInputStream = gZIPInputStream;
                            sequenceInputStream = new SequenceInputStream(new FileInputStream(file2), gZIPInputStream);
                        } else {
                            if (iZza != 3) {
                                throw new zzck(String.format("Slice checkpoint file corrupt. Unexpected FileExtractionStatus %s.", Integer.valueOf(zzemVarZzb.zza())), zzceVar.zzk);
                            }
                            zza.zza("Resuming central directory from last chunk.", new Object[0]);
                            zzenVar.zzd(gZIPInputStream, zzemVarZzb.zzc());
                            if (!zzceVar.zza()) {
                                throw new zzck("Chunk has ended twice during central directory. This should not be possible with chunk sizes of 50MB.", zzceVar.zzk);
                            }
                        }
                        sequenceInputStream = null;
                    } else {
                        gZIPInputStream = gZIPInputStream;
                        sequenceInputStream = gZIPInputStream;
                    }
                    if (sequenceInputStream != null) {
                        zzbw zzbwVar = new zzbw(sequenceInputStream);
                        File fileZzb = zzb(zzceVar);
                        do {
                            zzetVarZzb = zzbwVar.zzb();
                            if (!zzetVarZzb.zze() && !zzbwVar.zzc()) {
                                if (!zzetVarZzb.zzh() || zzetVarZzb.zzg()) {
                                    zzenVar.zzk(zzetVarZzb.zzf(), zzbwVar);
                                } else {
                                    zzenVar.zzj(zzetVarZzb.zzf());
                                    File file3 = new File(fileZzb, zzetVarZzb.zzc());
                                    file3.getParentFile().mkdirs();
                                    FileOutputStream fileOutputStream = new FileOutputStream(file3);
                                    int i4 = zzbwVar.read(this.zzb, 0, 8192);
                                    while (i4 > 0) {
                                        fileOutputStream.write(this.zzb, 0, i4);
                                        i4 = zzbwVar.read(this.zzb, 0, 8192);
                                    }
                                    fileOutputStream.close();
                                }
                            }
                            if (zzbwVar.zzd()) {
                                break;
                            }
                        } while (!zzbwVar.zzc());
                        if (zzbwVar.zzc()) {
                            zza.zza("Writing central directory metadata.", new Object[0]);
                            zzenVar.zzk(zzetVarZzb.zzf(), sequenceInputStream);
                        }
                        if (!zzceVar.zza()) {
                            if (zzetVarZzb.zze()) {
                                zza.zza("Writing slice checkpoint for partial local file header.", new Object[0]);
                                zzenVar.zzh(zzetVarZzb.zzf(), zzceVar.zzf);
                            } else if (zzbwVar.zzc()) {
                                zza.zza("Writing slice checkpoint for central directory.", new Object[0]);
                                zzenVar.zzf(zzceVar.zzf);
                            } else {
                                if (zzetVarZzb.zza() == 0) {
                                    zza.zza("Writing slice checkpoint for partial file.", new Object[0]);
                                    fileZzc = new File(zzb(zzceVar), zzetVarZzb.zzc());
                                    length = zzetVarZzb.zzb() - zzbwVar.zza();
                                    if (fileZzc.length() != length) {
                                        throw new zzck("Partial file is of unexpected size.");
                                    }
                                } else {
                                    zza.zza("Writing slice checkpoint for partial unextractable file.", new Object[0]);
                                    fileZzc = zzenVar.zzc();
                                    length = fileZzc.length();
                                }
                                zzenVar.zzg(fileZzc.getCanonicalPath(), length, zzbwVar.zza(), zzceVar.zzf);
                            }
                        }
                    }
                    gZIPInputStream.close();
                    if (zzceVar.zza()) {
                        try {
                            zzenVar.zzi(zzceVar.zzf);
                        } catch (IOException e) {
                            zza.zzb("Writing extraction finished checkpoint failed with %s.", e.getMessage());
                            throw new zzck("Writing extraction finished checkpoint failed.", e, zzceVar.zzk);
                        }
                    }
                    zza.zzd("Extraction finished for chunk %s of slice %s of pack %s of session %s.", Integer.valueOf(zzceVar.zzf), zzceVar.zzd, zzceVar.zzl, Integer.valueOf(zzceVar.zzk));
                    ((zzy) this.zzd.zza()).zzg(zzceVar.zzk, zzceVar.zzl, zzceVar.zzd, zzceVar.zzf);
                    try {
                        zzceVar.zzj.close();
                    } catch (IOException unused) {
                        zza.zze("Could not close file for chunk %s of slice %s of pack %s.", Integer.valueOf(zzceVar.zzf), zzceVar.zzd, zzceVar.zzl);
                    }
                    if (zzceVar.zzi == 3) {
                        zzbb zzbbVar = (zzbb) this.zze.zza();
                        String str5 = zzceVar.zzl;
                        long j4 = zzceVar.zzh;
                        zzbbVar.zzd(AssetPackState.zzb(str5, 3, 0, j4, j4, this.zzf.zzb(str5, zzceVar), 1, zzceVar.zzc, this.zzg.zza(zzceVar.zzl)));
                    }
                } catch (Throwable th) {
                    th = th;
                    try {
                        r10.close();
                    } catch (Throwable unused2) {
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                r10 = gZIPInputStream;
            }
        } catch (IOException e2) {
            zza.zzb("IOException during extraction %s.", e2.getMessage());
            throw new zzck(String.format("Error extracting chunk %s of slice %s of pack %s of session %s.", Integer.valueOf(zzceVar.zzf), zzceVar.zzd, zzceVar.zzl, Integer.valueOf(zzceVar.zzk)), e2, zzceVar.zzk);
        }
    }
}
