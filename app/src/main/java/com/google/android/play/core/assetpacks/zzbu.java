package com.google.android.play.core.assetpacks;

import android.os.ParcelFileDescriptor;
import com.google.android.play.core.tasks.Tasks;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/* JADX INFO: compiled from: com.google.android.play:core@@1.10.3 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbu {
    private final com.google.android.play.core.internal.zzco zza;

    public zzbu(com.google.android.play.core.internal.zzco zzcoVar) {
        this.zza = zzcoVar;
    }

    public final InputStream zza(int i, String str, String str2, int i2) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) Tasks.await(((zzy) this.zza.zza()).zza(i, str, str2, i2));
            if (parcelFileDescriptor == null || parcelFileDescriptor.getFileDescriptor() == null) {
                throw new zzck(String.format("Corrupted ParcelFileDescriptor, session %s packName %s sliceId %s, chunkNumber %s", Integer.valueOf(i), str, str2, Integer.valueOf(i2)), i);
            }
            return new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
        } catch (InterruptedException e) {
            throw new zzck("Extractor was interrupted while waiting for chunk file.", e, i);
        } catch (ExecutionException e2) {
            throw new zzck(String.format("Error opening chunk file, session %s packName %s sliceId %s, chunkNumber %s", Integer.valueOf(i), str, str2, Integer.valueOf(i2)), e2, i);
        }
    }
}
