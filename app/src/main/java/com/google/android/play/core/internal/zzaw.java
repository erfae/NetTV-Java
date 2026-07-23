package com.google.android.play.core.internal;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.play.core.splitcompat.SplitCompat;
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzaw implements com.google.android.play.core.splitinstall.zzh {
    private final Context zza;
    private final com.google.android.play.core.splitcompat.zze zzb;
    private final zzay zzc;
    private final Executor zzd;
    private final com.google.android.play.core.splitcompat.zzr zze;

    public zzaw(Context context, Executor executor, zzay zzayVar, com.google.android.play.core.splitcompat.zze zzeVar, com.google.android.play.core.splitcompat.zzr zzrVar, byte[] bArr) {
        this.zza = context;
        this.zzb = zzeVar;
        this.zzc = zzayVar;
        this.zzd = executor;
        this.zze = zzrVar;
    }

    public static /* bridge */ /* synthetic */ void zzb(zzaw zzawVar, List list, com.google.android.play.core.splitinstall.zzf zzfVar) {
        Integer numZze = zzawVar.zze(list);
        if (numZze == null) {
            return;
        }
        if (numZze.intValue() == 0) {
            zzfVar.zzc();
        } else {
            zzfVar.zzb(numZze.intValue());
        }
    }

    public static /* bridge */ /* synthetic */ void zzc(zzaw zzawVar, com.google.android.play.core.splitinstall.zzf zzfVar) {
        try {
            if (SplitCompat.zzd(zzce.zza(zzawVar.zza))) {
                Log.i("SplitCompat", "Splits installed.");
                zzfVar.zza();
            } else {
                Log.e("SplitCompat", "Emulating splits failed.");
                zzfVar.zzb(-12);
            }
        } catch (Exception e) {
            Log.e("SplitCompat", "Error emulating splits.", e);
            zzfVar.zzb(-12);
        }
    }

    @Nullable
    @SplitInstallErrorCode
    private final Integer zze(List list) {
        FileLock fileLockTryLock;
        try {
            FileChannel channel = new RandomAccessFile(this.zzb.zzd(), "rw").getChannel();
            Integer numValueOf = null;
            try {
                try {
                    fileLockTryLock = channel.tryLock();
                } catch (Throwable th) {
                    if (channel != null) {
                        try {
                            channel.close();
                        } catch (Throwable unused) {
                        }
                    }
                    throw th;
                }
            } catch (OverlappingFileLockException unused2) {
                fileLockTryLock = null;
            }
            if (fileLockTryLock != null) {
                int i = 0;
                try {
                    Log.i("SplitCompat", "Copying splits.");
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        Intent intent = (Intent) it.next();
                        String stringExtra = intent.getStringExtra("split_id");
                        AssetFileDescriptor assetFileDescriptorOpenAssetFileDescriptor = this.zza.getContentResolver().openAssetFileDescriptor(intent.getData(), "r");
                        File fileZze = this.zzb.zze(stringExtra);
                        if ((fileZze.exists() && fileZze.length() != assetFileDescriptorOpenAssetFileDescriptor.getLength()) || !fileZze.exists()) {
                            if (this.zzb.zzg(stringExtra).exists()) {
                                continue;
                            } else {
                                BufferedInputStream bufferedInputStream = new BufferedInputStream(assetFileDescriptorOpenAssetFileDescriptor.createInputStream());
                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(fileZze);
                                    try {
                                        byte[] bArr = new byte[4096];
                                        while (true) {
                                            int i2 = bufferedInputStream.read(bArr);
                                            if (i2 <= 0) {
                                                break;
                                            }
                                            fileOutputStream.write(bArr, 0, i2);
                                            try {
                                                bufferedInputStream.close();
                                            } catch (Throwable unused3) {
                                            }
                                            throw th;
                                        }
                                        fileOutputStream.close();
                                        bufferedInputStream.close();
                                    } catch (Throwable th2) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (Throwable unused4) {
                                        }
                                        throw th2;
                                    }
                                } catch (Throwable th3) {
                                    bufferedInputStream.close();
                                    throw th3;
                                }
                            }
                        }
                    }
                    Log.i("SplitCompat", "Splits copied.");
                    try {
                        File[] fileArrListFiles = this.zzb.zzb().listFiles();
                        try {
                            if (this.zzc.zzc(fileArrListFiles) && this.zzc.zza(fileArrListFiles)) {
                                try {
                                    File[] fileArrListFiles2 = this.zzb.zzb().listFiles();
                                    Arrays.sort(fileArrListFiles2);
                                    int length = fileArrListFiles2.length;
                                    while (true) {
                                        length--;
                                        if (length < 0) {
                                            break;
                                        }
                                        com.google.android.play.core.splitcompat.zze.zzm(fileArrListFiles2[length]);
                                        File file = fileArrListFiles2[length];
                                        file.renameTo(this.zzb.zzf(file));
                                    }
                                    Log.i("SplitCompat", "Splits verified.");
                                } catch (IOException e) {
                                    Log.e("SplitCompat", "Cannot write verified split.", e);
                                    i = -13;
                                }
                            } else {
                                Log.e("SplitCompat", "Split verification failed.");
                                i = -11;
                            }
                        } catch (Exception e2) {
                            Log.e("SplitCompat", "Error verifying splits.", e2);
                        }
                    } catch (IOException e3) {
                        Log.e("SplitCompat", "Cannot access directory for unverified splits.", e3);
                    }
                } catch (Exception e4) {
                    Log.e("SplitCompat", "Error copying splits.", e4);
                }
                numValueOf = Integer.valueOf(i);
                fileLockTryLock.release();
            }
            if (channel != null) {
                channel.close();
            }
            return numValueOf;
        } catch (Exception e5) {
            Log.e("SplitCompat", "Error locking files.", e5);
            return -13;
        }
    }

    @Override // com.google.android.play.core.splitinstall.zzh
    public final void zzd(List list, com.google.android.play.core.splitinstall.zzf zzfVar) {
        if (!SplitCompat.zze()) {
            throw new IllegalStateException("Ingestion should only be called in SplitCompat mode.");
        }
        this.zzd.execute(new zzav(this, list, zzfVar));
    }
}
